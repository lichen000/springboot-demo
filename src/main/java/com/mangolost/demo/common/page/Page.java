package com.mangolost.demo.common.page;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页对象
 * Created by mangolost on 2017-11-23
 */
public class Page<T> implements Serializable {

	public static final long serialVersionUID = 1L;

	private int pageNumber = 1; //分页页码，默认为1（第一页为1）
	private int pageSize = 10; //分页大小，默认为10
	private int totalCount = 0; //记录总数
	private int totalPages = 1; //记录总页数
	private List<T> result = new ArrayList<>(); //分页结果集

	public Page() {

	}

	public Page(int pageNumber, int pageSize) {
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}

	public Page(int pageNumber, int pageSize, int totalCount, int totalPages, List<T> result) {
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
		this.totalPages = totalPages;
		this.result = result;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
