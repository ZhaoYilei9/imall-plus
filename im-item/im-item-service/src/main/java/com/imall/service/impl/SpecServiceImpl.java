package com.imall.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imall.mapper.SpecGroupMapper;
import com.imall.mapper.SpecParamMapper;
import com.imall.pojo.SpecGroup;
import com.imall.pojo.SpecParam;
import com.imall.service.SpecService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class SpecServiceImpl implements SpecService {
	
	@Autowired
	private SpecParamMapper specParamMapper;
	
	@Autowired
	private SpecGroupMapper specGroupMapper;

	@Override
	public List<SpecParam> getSpecParamsByCid(Long cid) {
		Example example = new Example(SpecParam.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("cid", cid);
		List<SpecParam> specParams = specParamMapper.selectByExample(example);
		
		return specParams;
	}

	@Override
	public List<SpecGroup> getSpecGroupsByCid(Long cid) {
		Example example = new Example(SpecGroup.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("cid", cid);
		List<SpecGroup> specGroups = specGroupMapper.selectByExample(example);

		return specGroups;
	}

	@Override
	public List<SpecGroup> getSpecsByCid(Long cid) {
		List<SpecGroup> specGroups = this.getSpecGroupsByCid(cid);
		List<SpecParam> specParams = this.getSpecParamsByCid(cid);
		Map<Long,List<SpecParam>> map = new HashMap<>();
		for (SpecParam param : specParams) {
			if (!map.containsKey(param.getGroupId())){
				map.put(param.getGroupId(),new ArrayList<>());
			}
			map.get(param.getGroupId()).add(param);
		}
		for (SpecGroup group : specGroups) {
			group.setParams(map.get(group.getId()));
		}
		return specGroups;
	}


}
