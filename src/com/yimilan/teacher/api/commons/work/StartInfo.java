package com.yimilan.teacher.api.commons.work;

import java.util.Date;

public class StartInfo {
	// false 初始化状态或非执行状态。true在执行状态
	private boolean doingFlag;
	//本次启动结果 true本次启动成功 false本次启动失败
	private boolean thisStartFlag;
	private String info;
	private Date startTime;
	
	public StartInfo() {
		super();
	}

	public boolean isDoingFlag() {
		return doingFlag;
	}

	public void setDoingFlag(boolean doingFlag) {
		this.doingFlag = doingFlag;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	

	public boolean isThisStartFlag() {
		return thisStartFlag;
	}

	public void setThisStartFlag(boolean thisStartFlag) {
		this.thisStartFlag = thisStartFlag;
	}

	public StartInfo(boolean doingFlag, Date startTime, String info) {
		super();
		this.doingFlag = doingFlag;
		this.info = info;
		this.startTime = startTime;
	}

	@Override
	public String toString() {
		return "StartInfo [doingFlag=" + doingFlag + ", thisStartFlag=" + thisStartFlag + ", info=" + info
				+ ", startTime=" + startTime + "]";
	}

	

}
