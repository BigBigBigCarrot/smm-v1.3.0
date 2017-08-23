package com.david.task;

import java.util.Calendar;

import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 
 * @author left784533
 *
 */
@Component("myTask")
@Lazy(value = false)
public class MyTask {
	
	@Scheduled(fixedRate = 1000 * 10)//每10秒执行一次
	public void job(){
		Calendar calendar=Calendar.getInstance();
		System.out.println("执行定时任务,当前系统时间:"+calendar.getTime());
	}

}
