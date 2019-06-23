package com.imall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imall.api.SpecificationApi;
import com.imall.pojo.SpecGroup;
import com.imall.pojo.SpecParam;
import com.imall.response.ImallResult;
import com.imall.service.SpecService;

import lombok.extern.slf4j.Slf4j;

/**
 * http://api.imall.com/api/item/spec/params?cid=76
 * @author zyl
 *
 */
@RestController
@RequestMapping("spec")
@Slf4j
public class SpecController implements SpecificationApi{
	
	@Autowired
	private SpecService specService;

	@RequestMapping("params")
	public ImallResult getSpecParamsByCid(@RequestParam("cid") Long cid) {
		log.info("***商品规格查询-cid：{}", cid);
		List<SpecParam> specParams = specService.getSpecParamsByCid(cid);
		if (CollectionUtils.isEmpty(specParams)) {
			return ImallResult.errorMsg("查询规格参数失败");
		}
		return ImallResult.success(specParams);
	}
	/**
	 * http://api.imall.com/api/item/spec/groups/76
	 */
	@RequestMapping("groups/{cid}")
	public ImallResult getSpecGroupsByCid(@PathVariable("cid") Long cid) {
		List<SpecGroup> specGroups = specService.getSpecGroupsByCid(cid);
		if (CollectionUtils.isEmpty(specGroups)) {
			return ImallResult.errorMsg("查询规格参数失败");
		}
		return ImallResult.success(specGroups);
	}
}
