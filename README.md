#使用方法
分布式使用的是redis,用下自己的redisService
```
String key="somework";
LongTimeWorkThread ltw=new LongTimeWorkThread() {
	
	@Override
	public void doWork() {
		// TODO Auto-generated method stub
		int sucNum=evaluationIOService.importAnswers(importAnswerVo);
	}
	@Override
	public Object finish() {
		distributeWorkService.clearKey(key);
		return null;
	}
};
StartInfo startInfo=distributeWorkService.checkStart(key, ltw);
if(startInfo.isThisStartFlag()) {
	rstInfo="启动成功,预计需要:"+data.size()/10+"秒";
}else {
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	rstInfo="启动失败,现在执行的是"+sdf.format(startInfo.getStartTime())+"开始的任务";
}
```
