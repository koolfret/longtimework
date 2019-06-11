package net.highersoft.commons.work;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.yimilan.teacher.api.service.redis.RedisService;

/**
 * 使用分布式的锁的长时任务
 * @author chengzhong
 *
 */
@Service
public class DistributeWorkService {
	@Autowired
	private RedisService redisService;
	public StartInfo checkStart(String key,LongTimeWorkThread ltw) {
		String val=redisService.getAsString(key);
		if(val!=null) {
			StartInfo redisExist=JSONObject.parseObject(val,StartInfo.class);
			redisExist.setThisStartFlag(false);
			return redisExist;
		}else {
			StartInfo startInfo=ltw.checkStart(key);
			redisService.set(key, JSONObject.toJSONString(startInfo), 30, TimeUnit.MINUTES);
			return startInfo;
		}
		
	}
	
	public void clearKey(String key) {
		redisService.remove(key);
	}
}
