package com.imall.service.impl;

import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imall.common.pojo.PageResult;
import com.imall.common.vo.BrandVO;
import com.imall.mapper.BrandMapper;
import com.imall.mapper.CategoryBrandMapper;
import com.imall.pojo.Brand;
import com.imall.pojo.Category;
import com.imall.pojo.CategoryBrand;
import com.imall.service.BrandService;
import com.imall.service.CategoryService;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
@Slf4j
public class BrandServiceImpl implements BrandService {

	@Autowired
	private BrandMapper brandMapper;

	@Autowired
	private CategoryBrandMapper categoryBrandMapper;

	@Autowired
	private CategoryService categoryService;

	@Override
	public PageResult<Brand> brandList(String key, Integer page, Integer rows, String sortBy, Boolean desc) {
		// 1.开启分页助手
		PageHelper.startPage(page, rows);
		// 2.排序
		Example example = new Example(Brand.class);
		example.setOrderByClause(sortBy + (desc ? " desc" : " asc"));
		Criteria criteria = example.createCriteria();
		// 3.根据key进行查询
		if (StringUtils.isNotBlank(key)) {
			criteria.andLike("name", key + "%").orEqualTo("letter", key.toUpperCase());
		}
		List<Brand> brands = brandMapper.selectByExample(example);
		PageInfo<Brand> pageInfo = new PageInfo<>(brands);
		PageResult<Brand> result = new PageResult<>();
		result.setItems(pageInfo.getList());
		result.setPage(pageInfo.getPageNum());
		result.setTotal(pageInfo.getTotal());
		result.setPages(pageInfo.getPages());

		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer saveBrand(BrandVO brand) {
		Brand brandDb = new Brand();
		BeanUtils.copyProperties(brand, brandDb);
		// 1.保存品牌信息，即新增商品品牌表
		int insertBrandCounts = brandMapper.insert(brandDb);
		log.info("***新增品牌id:{}", brandDb.getId());
		// 2.保存品牌分类信息，即新增品牌商品分类表
		int insertCBCounts = 0;
		List<Long> cids = brand.getCids();
		for (Long cid : cids) {
			CategoryBrand cb = new CategoryBrand();
			cb.setBrandId(brandDb.getId());
			cb.setCategoryId(cid);
			insertCBCounts = categoryBrandMapper.insert(cb);
		}
		if (insertBrandCounts == 0 || insertCBCounts == 0) {
			return 0;
		}
		return 1;
	}


	@Override
	public List<Category> queryCategoriesByBid(Long id) {
		Example example = new Example(CategoryBrand.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("brandId", id);
		List<CategoryBrand> categoryBrands = categoryBrandMapper.selectByExample(example);
		List<Long> cids = categoryBrands.stream().map(CategoryBrand::getCategoryId).collect(Collectors.toList());
		log.info("***cids:{}", cids);
		List<Category> categories = new ArrayList<Category>();
		for (Long cid : cids) {
			Category category = categoryService.queryCategoryByCid(cid);
			categories.add(category);
		}
		return categories;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer updateBrand(BrandVO brand) {
		Brand brandDb = new Brand();
		BeanUtils.copyProperties(brand, brandDb);
		// 1.保存品牌信息，即新增商品品牌表
		int updateBrandCounts = brandMapper.updateByPrimaryKey(brandDb);
		log.info("***修改品牌id:{}", brandDb.getId());
		// 2.保存品牌分类信息，即新增品牌商品分类表
		int updateCBCounts = 0;
		List<Long> cids = brand.getCids();
		// 3.先删除该品牌对应的-品牌商品分类表的旧数据
		Example example = new Example(CategoryBrand.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("brandId", brand.getId());
		int deleteCounts = categoryBrandMapper.deleteByExample(example);
		for (Long cid : cids) {

			// 4.再新增该品牌对应的-品牌商品分类表
			CategoryBrand cb = new CategoryBrand();
			cb.setBrandId(brandDb.getId());
			cb.setCategoryId(cid);
			updateCBCounts = categoryBrandMapper.insert(cb);
		}
		if (updateBrandCounts == 0 || updateCBCounts == 0) {
			return 0;
		}
		return 1;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public Integer deleteBrandById(Long bid) {
		//1.删除品牌的相关信息
		
		int deleteCount = brandMapper.deleteByPrimaryKey(bid);
		//2.删除品牌分类关联表的数据
		Example example = new Example(CategoryBrand.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("brandId", bid);
		int deleteCounts = categoryBrandMapper.deleteByExample(example);
		if (deleteCount == 0 || deleteCounts == 0) {
			return 0;
		}
		return 1;
	}

	@Override
	public List<Brand> getBrandsByCid(Long cid) {
		Example example = new Example(CategoryBrand.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("categoryId", cid);
		List<CategoryBrand> cbs = categoryBrandMapper.selectByExample(example);
		List<Long> bids = cbs.stream().map(CategoryBrand :: getBrandId).collect(Collectors.toList());
		log.info("***商品新增-查询品牌ids:{}", bids.size());
		List<Brand> brands = brandMapper.selectByIdList(bids);
		return brands;
	}

	@Override
	public List<Brand> getBrandsByBids(List<Long> ids) {
		List<Brand> brands = brandMapper.selectByIdList(ids);
		if (CollectionUtils.isEmpty(brands)) {
			return null;
		}
		return brands;
	}
	
	
}
