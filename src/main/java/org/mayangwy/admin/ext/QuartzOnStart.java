package org.mayangwy.admin.ext;

import lombok.extern.slf4j.Slf4j;
import org.mayangwy.admin.service.quartz.TestJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class QuartzOnStart implements ApplicationRunner {

    @Autowired
    private Scheduler scheduler;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        /*JobDetail jobDetail = JobBuilder.newJob(TestJob.class).withDescription("a job for test").withIdentity("testJob").storeDurably(true)
                .requestRecovery(true).build();
        scheduler.addJob(jobDetail, true);
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("testJobTrigger", "DEFAULT")
                .withDescription("a Trigger for testJob").startNow().withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * * * ?"))
                .forJob(jobDetail).build();
        boolean b = scheduler.checkExists(trigger.getKey());
        Date date;
        if(b){
            date = scheduler.rescheduleJob(trigger.getKey(), trigger);
        } else {
            date = scheduler.scheduleJob(trigger);
        }
        System.out.println(date);*/
        if(! scheduler.isStarted()){
            log.info("task is start !!!");
            scheduler.start();
        } else {
            log.info("task is has start !!!");
        }
    }

}