package org.mayangwy.admin.ext;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@Slf4j
public abstract class BaseJob implements Job {

    public void execute(JobExecutionContext context) throws JobExecutionException {
        ThreadContext.put("logId", IdUtil.simpleUUID());
        log.info("============= 定时任务准备执行 =============");

        doTask(context);

        log.info("============= 定时任务结束执行 =============");
    }

    public abstract void doTask(JobExecutionContext context);

}