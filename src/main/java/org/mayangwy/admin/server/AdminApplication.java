package org.mayangwy.admin.server;

import cn.hutool.core.util.IdUtil;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAspectJAutoProxy
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"org.mayangwy.admin.dao"})
@EntityScan(basePackages = {"org.mayangwy.admin.entity"})
@SpringBootApplication(scanBasePackages = {"org.mayangwy"})
public class AdminApplication {

	public static void main(String[] args) {
		//设置启动时主线程的线程ID，用于日志记录
		ThreadContext.put("logId", IdUtil.simpleUUID());
		SpringApplication.run(AdminApplication.class, args);
	}

}