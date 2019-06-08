package com.imall.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imall.common.pojo.PageResult;
import com.imall.mapper.SpuMapper;
import com.imall.pojo.Brand;
import com.imall.pojo.Spu;
import com.imall.service.GoodsService;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
@Slf4j
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private SpuMapper spuMapper;

	@Override
	public PageResult<Spu> spuList(String key, Boolean saleable, Integer page, Integer rows) {
		// 1.开启分页助手
		PageHelper.startPage(page, rows);
		Example example = new Example(Brand.class);
		Criteria criteria = example.createCriteria();
//		// 3.根据key进行查询
//		if (StringUtils.isNotBlank(key)) {
//			criteria.andLike("title", key + "%");
//		}
		List<Spu> spus = spuMapper.selectByExample(example);
		PageInfo<Spu> pageInfo = new PageInfo<>(spus);
		PageResult<Spu> result = new PageResult<>();
		result.setItems(pageInfo.getList());
		result.setPage(pageInfo.getPageNum());
		result.setTotal(pageInfo.getTotal());
		result.setPages(pageInfo.getPages());

		log.info("***商品查询服务层pageResult:{}", result);
		return result;
	}

}
