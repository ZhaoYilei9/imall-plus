package com.imall.common.vo;

import java.util.List;

import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class GoodsVO {

	private String title;

    /**
     * 子标题
     */
    private String subTitle;

    /**
     * 1级类目id
     */
    private Long cid1;

    /**
     * 2级类目id
     */
    private Long cid2;

    /**
     * 3级类目id
     */
    private Long cid3;

    /**
     * 商品所属品牌id
     */
    private Long brandId;
    
    private List<SkuVO> skus;
    
    private SpuDetailVO spuDetail;

	@Override
	public String toString() {
		return "GoodsVO [title=" + title + ", subTitle=" + subTitle + ", cid1=" + cid1 + ", cid2=" + cid2 + ", cid3="
				+ cid3 + ", brandId=" + brandId + ", skus=" + skus + ", spuDetail=" + spuDetail + "]";
	}
    
}
