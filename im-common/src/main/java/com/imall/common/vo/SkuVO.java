package com.imall.common.vo;

import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class SkuVO {

	/**
     * spu id
     */
    private Long spuId;

    /**
     * 商品标题
     */
    private String title;

    /**
     * 商品的图片，多个图片以‘,’分割
     */
    private String images;

    /**
     * 销售价格，单位为分
     */
    private Long price;

    /**
     * 特有规格属性在spu属性模板中的对应下标组合
     */
    private String indexes;

    /**
     * sku的特有规格参数键值对，json格式，反序列化时请使用linkedHashMap，保证有序
     */
    private String ownSpec;

    /**
     * 是否有效，0无效，1有效
     */
    private Boolean enable;
    
    /**
     * 库存
     */
    private Long stock;

	@Override
	public String toString() {
		return "SkuVO [spuId=" + spuId + ", title=" + title + ", images=" + images + ", price=" + price + ", indexes="
				+ indexes + ", ownSpec=" + ownSpec + ", enable=" + enable + ", stock=" + stock + "]";
	}
    
}
