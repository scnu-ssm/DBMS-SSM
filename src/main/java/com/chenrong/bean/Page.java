package com.chenrong.bean;

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
	 
	 private Integer span = Const.SPAN;  // 一页存放的数据是 500 条记录

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
		if(allRows % span == 0) {
			this.end = allRows / span;
		}else {
		    this.end = allRows / span + 1;
		}
	}

	public Integer getCurrent() {
		return current;
	}

	public void setCurrent(Integer current) {
		this.current = current;
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
		this.nextPage = nextPage;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}
	 
}
