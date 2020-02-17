package com.chenrong.bean;

import java.util.List;

/**
 *  页的信息
 * @author chenrong
 *
 */
public class Page {
	
	 private Integer start = 1;  // 首页页码
	 
	 private Integer end;  // 尾页页码
	 
	 private Integer current;  // 当前页码
	 
	 private Integer prePage;  // 前一页
	 
	 private Integer nextPage; // 下一页
	 
	 private Integer rows; // 当前页有多少条记录
	 
	 private Integer allRows;  // 共有多少条记录
	 
	 private List<Integer> pageArr;   // 页码列表

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getEnd() {
		return end;
	}

	// 传入的参数为 All数据记录数量
	public void setEnd(Integer allRows) {
		if(allRows % Const.SPAN == 0) {
			this.end = allRows / Const.SPAN;
		}else {
		    this.end = allRows / Const.SPAN + 1;
		}
		// end 不允许是 0
		if(allRows == 0) {
			this.end = 1;
		}
	}

	public Integer getCurrent() {
		return current;
	}

	public void setCurrent(Integer current) {
		this.current = current < 1 ? 1 : current;
	}

	public Integer getPrePage() {
		return prePage;
	}

	public void setPrePage(Integer prePage) {
		this.prePage = prePage < 1 ? 1 : prePage;
	}

	public Integer getNextPage() {
		return nextPage;
	}

	public void setNextPage(Integer nextPage) {
		this.nextPage = nextPage > end ? end : nextPage;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getAllRows() {
		return allRows;
	}

	public void setAllRows(Integer allRows) {
		this.allRows = allRows;
	}

	public List<Integer> getPageArr() {
		return pageArr;
	}

	public void setPageArr(List<Integer> pageArr) {
		this.pageArr = pageArr;
	}

	@Override
	public String toString() {
		return "Page [start=" + start + ", end=" + end + ", current=" + current + ", prePage=" + prePage + ", nextPage="
				+ nextPage + ", rows=" + rows + ", allRows=" + allRows + ", pageArr=" + pageArr
				+ "]";
	}
	 
}
