package com.imall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

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
public class SpecController {
	
	@Autowired
	private SpecService specService;

	@RequestMapping("params")
	public ResponseEntity getSpecParamsByCid(@RequestParam("cid") Long cid) {
		log.info("***商品规格查询-cid：{}", cid);
		List<SpecParam> specParams = specService.getSpecParamsByCid(cid);
		if (CollectionUtils.isEmpty(specParams)) {
			return ResponseEntity.status(500).build();
		}
		return ResponseEntity.ok(specParams);
	}
	/**
	 * http://api.imall.com/api/item/spec/groups/76
	 */
	@RequestMapping("groups/{cid}")
	public ResponseEntity getSpecGroupsByCid(@PathVariable("cid") Long cid) {
		List<SpecGroup> specGroups = specService.getSpecGroupsByCid(cid);
		if (CollectionUtils.isEmpty(specGroups)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok(specGroups);
	}
	@RequestMapping("{cid}")
	public ResponseEntity getSpecsByCid(@RequestParam("cid") Long cid){
		List<SpecGroup> specs = this.specService.getSpecsByCid(cid);
		if (CollectionUtils.isEmpty(specs)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok(specs);
	}
}
