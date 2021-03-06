package com.imall.service;

import java.util.List;

import com.imall.pojo.SpecGroup;
import com.imall.pojo.SpecParam;

public interface SpecService {

	List<SpecParam> getSpecParamsByCid(Long cid);

	List<SpecGroup> getSpecGroupsByCid(Long cid);

	List<SpecGroup> getSpecsByCid(Long cid);
}
