package com.imall.common.pojo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {
	// 当前页
	private int page;
	// 记录
	private List<T> items;
	// 总页数
	private long pages;
	// 总记录数
	private long total;
	@Override
	public String toString() {
		return "PageResult [page=" + page + ", items=" + items + ", pages=" + pages + ", total=" + total + "]";
	}
	public PageResult(long total, long totalPage,List<T> items) {
		this.total = total;
		this.pages = totalPage;
		this.items = items;
	}
}
