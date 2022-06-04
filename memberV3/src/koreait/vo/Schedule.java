package koreait.vo;

import java.util.Date;

public class Schedule {
	private int idx;
	private int mno;
	private String title;
	private Date sdate;
	
	
	public Schedule() {
		// TODO Auto-generated constructor stub
	}
	
	public Schedule(int idx, int mno, String title, Date sdate) {
		super();
		this.idx = idx;
		this.mno = mno;
		this.title = title;
		this.sdate = sdate;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public int getMno() {
		return mno;
	}

	public void setMno(int mno) {
		this.mno = mno;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getSdate() {
		return sdate;
	}

	public void setSdate(Date sdate) {
		this.sdate = sdate;
	}

	@Override
	public String toString() {
		return "Schedule [idx=" + idx + ", mno=" + mno + ", title=" + title + ", sdate=" + sdate + "]";
	}
	

}
