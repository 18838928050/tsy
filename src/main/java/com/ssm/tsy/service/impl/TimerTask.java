package com.ssm.tsy.service.impl;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ssm.tsy.service.TsyInvestigationQuestionService;
import com.ssm.tsy.service.UserService;

@Component
public class TimerTask {
	// 0 0 10,14,16 * * ? 每天上午10点，下午2点，4点
	// 0 0/30 9-17 * * ? 朝九晚五工作时间内每半小时
	// 0 0 12 ? * WED 表示每个星期三中午12点
	// "0 0 12 * * ?" 每天中午12点触发
	// "0 15 10 ? * *" 每天上午10:15触发
	// "0 15 10 * * ?" 每天上午10:15触发
	// "0 15 10 * * ? *" 每天上午10:15触发
	// "0 15 10 * * ? 2005" 2005年的每天上午10:15触发
	// "0 * 14 * * ?" 在每天下午2点到下午2:59期间的每1分钟触发
	// "0 0/5 14 * * ?" 在每天下午2点到下午2:55期间的每5分钟触发
	// "0 0/5 14,18 * * ?" 在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发
	// "0 0-5 14 * * ?" 在每天下午2点到下午2:05期间的每1分钟触发
	// "0 10,44 14 ? 3 WED" 每年三月的星期三的下午2:10和2:44触发
	// "0 15 10 ? * MON-FRI" 周一至周五的上午10:15触发
	// "0 15 10 15 * ?" 每月15日上午10:15触发
	// "0 15 10 L * ?" 每月最后一日的上午10:15触发
	// "0 15 10 ? * 6L" 每月的最后一个星期五上午10:15触发
	// "0 15 10 ? * 6L 2002-2005" 2002年至2005年的每月的最后一个星期五上午10:15触发
	// "0 15 10 ? * 6#3" 每月的第三个星期五上午10:15触发
	/**
	 * 每天22点30启动任务
	 */
	// @Scheduled(cron = "0 30 22 ? * *")
	// public void test1() throws Exception {
	// System.out.println("job1 开始执行..." + DateUtil.getTimeAndToString());
	// }
	//
	// // 每隔5秒隔行一次
	// @Scheduled(cron = "0/5 * * * * ?")
	// public void test2() throws Exception {
	// System.out.println("job2 开始执行");
	// }
	@Resource
	private UserService userService;

	
	@Resource
	private TsyInvestigationQuestionService  tsyInvestigationQuestionService;
	
	/**
	 * 每日凌晨触发
	 * @throws Exception
	 */
	@Scheduled(cron = "0 0 0 * * ?")
	public void EveryDayZero() throws Exception {
		tsyInvestigationQuestionService.updateEndDateSmallNow();
	}
	//
	// @Scheduled(cron = "0/5 * * * * ?")
	// public void test2() throws Exception {
	// System.out.println("job2 开始执行");
	// userService.CopyTable();
	// }
	/**
	 * 每月15日上午10:15触发
	 * 
	 * @throws Exception
	 */
	@Scheduled(cron = "0 15 10 15 * ?")
	public void EveryMonthLastDay() throws Exception {
		userService.CopyTable();
	}

}