package com.imall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imall.mapper.SpecParamMapper;
import com.imall.pojo.SpecParam;
import com.imall.service.SpecService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class SpecServiceImpl implements SpecService {
	
	@Autowired
	private SpecParamMapper specParamMapper;

	@Override
	public List<SpecParam> getSpecParamsByCid(Long cid) {
		Example example = new Example(SpecParam.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("cid", cid);
		List<SpecParam> specParams = specParamMapper.selectByExample(example);
		
		return specParams;
	}

}
