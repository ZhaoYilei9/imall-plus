package com.imall.api;

import java.util.List;

import com.imall.pojo.SpecGroup;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.imall.pojo.SpecParam;
import com.imall.response.ImallResult;

@RequestMapping("spec")
public interface SpecificationApi {


	@RequestMapping("params")
	public List<SpecParam> getSpecParamsByCid(@RequestParam("cid") Long cid);
	@RequestMapping("{cid}")
	public List<SpecGroup> getSpecsByCid(@RequestParam("cid") Long cid);
}
