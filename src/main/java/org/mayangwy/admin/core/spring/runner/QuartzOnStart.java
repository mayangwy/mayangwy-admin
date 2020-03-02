package org.mayangwy.admin.core.spring.runner;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class QuartzOnStart implements ApplicationRunner {

    @Autowired
    private Scheduler scheduler;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        /*if(! scheduler.isStarted()){
            log.info("task is start !!!");
            scheduler.start();
        } else {
            log.info("task is has start !!!");
        }*/
    }

}