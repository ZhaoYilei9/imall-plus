package com.imall.controller;

import java.util.List;

import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imall.common.pojo.PageResult;
import com.imall.common.vo.BrandVO;
import com.imall.pojo.Brand;
import com.imall.pojo.Category;
import com.imall.response.ImallResult;
import com.imall.service.BrandService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author zyl brand/page?key=&page=1&rows=5&sortBy=id&desc=false
 */
@RestController
@RequestMapping("brand")
@Slf4j
public class BrandController {

	@Autowired
	private BrandService brandService;

	/**
	 * 
	 * @param key    过滤查询
	 * @param page   页数
	 * @param rows   每页现实的记录数
	 * @param sortBy 要排序的字段
	 * @param desc   增序还是降序
	 * @return
	 */
	@GetMapping("page")
	public PageResult<Brand> brandList(@RequestParam(name = "key", required = false) String key,
			@RequestParam(name = "page", defaultValue = "1") Integer page,
			@RequestParam(name = "rows", defaultValue = "5") Integer rows, @RequestParam(name = "sortBy") String sortBy,
			@RequestParam(name = "desc") Boolean desc) {
		PageResult<Brand> brands = brandService.brandList(key, page, rows, sortBy, desc);

		return brands;
	}

	@PostMapping
	public ImallResult saveBrand(BrandVO brand) {
		log.info("***brand:{}***", brand);
		Integer saveBrandCounts = brandService.saveBrand(brand);
		if (saveBrandCounts > 0) {
			return ImallResult.success();
		}
		return ImallResult.errorMsg("保存失败");
	}

	/**
	 * bid/1912
	 */
	@GetMapping("bid/{id}")
	public ImallResult queryBrandById(@PathVariable("id") Long id) {
		log.info("***bid:{}", id);
		List<Category> categories = brandService.queryCategoriesByBid(id);
		if (CollectionUtils.isEmpty(categories)) {
			return ImallResult.errorMsg("查询失败");
		}
		return ImallResult.success(categories);
	}

	@PutMapping
	public ImallResult updateBrand(BrandVO brand) {
		Integer updateBrandCount = brandService.updateBrand(brand);
		log.info("***修改brand的cids:{}***", brand.getCids());
		if (updateBrandCount > 0) {
			return ImallResult.success();
		}
		return ImallResult.errorMsg("保存失败");
	}

	@DeleteMapping("bid/{bid}")
	public ImallResult deleteBrandById(@PathVariable("bid") Long bid) {
		log.info("***要删除的品牌的id:{}", bid);
		Integer deleteCounts = brandService.deleteBrandById(bid);
		if (deleteCounts > 0) {
			return ImallResult.success();
		}
		return ImallResult.errorMsg("删除失败");
	}

	/**
	 * http://api.imall.com/api/item/brand/cid/76
	 */
	@GetMapping("cid/{cid}")
	public ImallResult getBrandsByCid(@PathVariable("cid") Long cid) {
		log.info("***新增商品信息-品牌查询-cid:{}",cid);
		List<Brand> brands = brandService.getBrandsByCid(cid);
		if (CollectionUtils.isEmpty(brands)) {
			return ImallResult.errorMsg("查询错误");
		}
		
		return ImallResult.success(brands);
	}

}
