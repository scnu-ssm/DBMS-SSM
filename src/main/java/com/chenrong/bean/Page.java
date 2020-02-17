package com.chenrong.bean;

import java.util.List;

/**
 *  ҳ����Ϣ
 * @author chenrong
 *
 */
public class Page {
	
	 private Integer start = 1;  // ��ҳҳ��
	 
	 private Integer end;  // βҳҳ��
	 
	 private Integer current;  // ��ǰҳ��
	 
	 private Integer prePage;  // ǰһҳ
	 
	 private Integer nextPage; // ��һҳ
	 
	 private Integer rows; // ��ǰҳ�ж�������¼
	 
	 private Integer allRows;  // ���ж�������¼
	 
	 private List<Integer> pageArr;   // ҳ���б�

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getEnd() {
		return end;
	}

	// ����Ĳ���Ϊ All���ݼ�¼����
	public void setEnd(Integer allRows) {
		if(allRows % Const.SPAN == 0) {
			this.end = allRows / Const.SPAN;
		}else {
		    this.end = allRows / Const.SPAN + 1;
		}
		// end �������� 0
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
