package com.imall.common.vo;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandVO {
	
	private Long id;

	/**
	 * 品牌名称
	 */
	private String name;

	/**
	 * 品牌图片地址
	 */
	private String image;

	/**
	 * 品牌的首字母
	 */
	private String letter;
	
	/**
	 * 品牌的商品分类
	 */
	private List<Long> cids;

	@Override
	public String toString() {
		return "BrandVO [id=" + id + ", name=" + name + ", image=" + image + ", letter=" + letter + ", cids=" + cids
				+ "]";
	}
	
}
