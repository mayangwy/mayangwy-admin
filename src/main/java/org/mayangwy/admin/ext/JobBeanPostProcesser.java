package org.mayangwy.admin.ext;

import lombok.SneakyThrows;
import org.quartz.*;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class JobBeanPostProcesser implements BeanPostProcessor {

    @Autowired
    private Scheduler scheduler;

    @SneakyThrows
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof BaseJob){
            BaseJob baseJob = (BaseJob) bean;
            String taskName = baseJob.taskName();
            boolean checkJobExists = scheduler.checkExists(JobKey.jobKey(taskName));
            if(!checkJobExists){
                JobDetail jobDetail = JobBuilder.newJob(baseJob.getClass()).withIdentity(taskName).withDescription(taskName)
                        .storeDurably(true).requestRecovery(true).build();
                scheduler.addJob(jobDetail, false);
            }
            String[] taskCrons = baseJob.taskCrons();
            if(taskCrons != null && taskCrons.length > 0){
                for (int i = 0; i < taskCrons.length; i++) {
                    String triggerNo = taskName + "Trigger" + (i + 1);
                    boolean checkTriggerExists = scheduler.checkExists(TriggerKey.triggerKey(triggerNo));
                    if(!checkTriggerExists){
                        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(triggerNo).forJob(taskName).withDescription(triggerNo)
                                .startNow().withSchedule(CronScheduleBuilder.cronSchedule(taskCrons[i])).build();
                        scheduler.scheduleJob(cronTrigger);
                    }
                }
            }
        }
        return bean;
    }

}