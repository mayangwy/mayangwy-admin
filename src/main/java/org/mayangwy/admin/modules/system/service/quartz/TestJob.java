package org.mayangwy.admin.modules.system.service.quartz;

import lombok.extern.slf4j.Slf4j;
import org.mayangwy.admin.core.base.entity.BaseJob;
import org.mayangwy.admin.modules.system.service.UserCommonService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@DisallowConcurrentExecution
@Slf4j
public class TestJob extends BaseJob {

    @Autowired
    private UserCommonService userCommonService;

    @Override
    public void doTask(JobExecutionContext context) {
        log.info(this.toString());
        log.info(userCommonService.toString());
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("============================== test job is running {} !!! ==============================");
    }

    @Override
    public String taskName() {
        return "Task0001";
    }

    @Override
    public String[] taskCrons() {
        return new String[]{"0 0/5 * * * ?", "30 0/6 * * * ?"};
    }

}