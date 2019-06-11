package com.yimilan.teacher.api.commons.work;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class LongTimeWorkThread {
	private static ConcurrentHashMap<String, StartInfo> threadsFlag = new ConcurrentHashMap<String, StartInfo>();
	private static ExecutorService pool = Executors.newCachedThreadPool();

	public StartInfo checkStart(String key) {
		StartInfo flag = threadsFlag.get(key);
		if (flag == null || !flag.isDoingFlag()) {
			if (flag == null) {
				// 第一次启动
				flag = new StartInfo(true, Calendar.getInstance().getTime(), "启动成功");
			} else {
				// 非第一次启动，也不是在执行中
				flag.setDoingFlag(true);
				flag.setStartTime(Calendar.getInstance().getTime());
			}
			threadsFlag.put(key, flag);
			pool.execute(new WorkThread(this, key) {
				public void run() {
					try {
						workObj.doWork();
					} finally {
						workObj.removeDoingFlag(key);
						finish();
					}
				}
			});
			flag.setThisStartFlag(true);
			return flag;
		} else {
			flag.setThisStartFlag(false);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			flag.setInfo("启动失败,正在执行的作业的启动时间:" + sdf.format(flag.getStartTime())+"");
			return flag;
		}
	}

	public void removeDoingFlag(String key) {
		StartInfo flag = threadsFlag.get(key);
		if (flag != null) {
			flag.setDoingFlag(false);
		}
	}

	public void shutdown() {
		pool.shutdown();
	}

	

	/**
	 * 调用者需实现该方法，执行具体的任务
	 */
	public abstract void doWork();
	
	/**
	 * 任务完毕后,清除分布式锁等操作
	 * @return
	 */
	public Object finish(){ return ""; }
	
}

class WorkThread extends Thread {
	public LongTimeWorkThread workObj;
	public String key;

	public WorkThread(LongTimeWorkThread workObj, String key) {
		this.workObj = workObj;
		this.key = key;
	}
}