package com.imall.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.imall.response.ImallResult;

@RequestMapping("spec")
public interface SpecificationApi {


	@RequestMapping("params")
	public ImallResult getSpecParamsByCid(@RequestParam("cid") Long cid);
}
