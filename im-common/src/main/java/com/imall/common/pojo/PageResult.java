package com.imall.common.pojo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageResult<T> {
	// 当前页
	private int page;
	// 记录
	private List<T> items;
	// 总页数
	private long pages;
	// 总记录数
	private long total;
}
