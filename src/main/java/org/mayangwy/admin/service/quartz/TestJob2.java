package org.mayangwy.admin.service.quartz;

import lombok.extern.slf4j.Slf4j;
import org.mayangwy.admin.ext.BaseJob;
import org.mayangwy.admin.service.UserCommonService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@DisallowConcurrentExecution
@Slf4j
public class TestJob2 extends BaseJob {

    @Autowired
    private UserCommonService userCommonService;

    @Override
    public void doTask(JobExecutionContext context) {
        log.info("============================== 222222222222222222222 !!! ==============================");
    }

    @Override
    public String taskName() {
        return "Task0002";
    }

    @Override
    public String[] taskCrons() {
        return new String[]{"45 * * * * ?"};
    }

}
