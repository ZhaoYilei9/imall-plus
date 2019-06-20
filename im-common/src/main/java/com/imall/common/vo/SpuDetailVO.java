package com.imall.common.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class SpuDetailVO {
	/**
     * 通用规格参数数据
     */
    private String genericSpec;

    /**
     * 特有规格参数及可选值信息，json格式
     */
    private String specialSpec;

    /**
     * 包装清单
     */
    private String packingList;

    /**
     * 售后服务
     */
    private String afterService;

    /**
     * 商品描述信息
     */
    private String description;

	@Override
	public String toString() {
		return "SpuDetailVO [genericSpec=" + genericSpec + ", specialSpec=" + specialSpec + ", packingList="
				+ packingList + ", afterService=" + afterService + ", description=" + description + "]";
	}
    
    
}
