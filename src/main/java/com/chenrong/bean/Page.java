package com.chenrong.bean;

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
	 
	 private Integer span = Const.SPAN;  // һҳ��ŵ������� 500 ����¼

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
