package org.mayangwy.admin.service;

import cn.hutool.core.util.RandomUtil;
import org.mayangwy.admin.dao.UserRepository;
import org.mayangwy.admin.entity.PositionPO;
import org.mayangwy.admin.entity.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserCommonService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private List<Object> objectList;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    public List<UserPO> findAll(){
        List<UserPO> all = userRepository.findAll();
        //List<UserPO> all = userRepository.findAllById(Arrays.asList(3L, 4L));
        return all;
    }

    @Transactional(rollbackFor = Exception.class)
    public void testTransaction(){
        saveSimple();
        throw new RuntimeException("test exception !!!");
        //testTransaction2();
        /*DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(definition);
        try {
            testTransaction2();
            platformTransactionManager.commit(transactionStatus);
        } catch (Exception e) {
            throw e;
            //platformTransactionManager.rollback(transactionStatus);
        }*/
    }

    //@Transactional(rollbackFor = Exception.class)
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void testTransaction2(){
        UserPO userPO = new UserPO();
        userPO.setUserName("测试事务aaa");
        userPO.setCreateTime(new Date());
        userPO.setCreateUserId(0L);
        userPO.setIsDel(0);
        userPO.setUpdateTime(new Date());
        userPO.setUpdateUserId(0L);
        userPO.setPassword("123321");
        userRepository.save(userPO);
        throw new RuntimeException("test exception !!!");
    }

    //@Transactional(rollbackFor = Exception.class)
    public void saveSimple(){
        UserPO userPO = new UserPO();
        userPO.setUserName("测试事务"+ RandomUtil.randomNumber());
        userPO.setCreateTime(new Date());
        userPO.setCreateUserId(0L);
        userPO.setIsDel(0);
        userPO.setUpdateTime(new Date());
        userPO.setUpdateUserId(0L);
        userPO.setPassword("123321");
        userRepository.save(userPO);
    }

    public void save(){
        UserPO userPO = new UserPO();
        userPO.setUserName("测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长" +
                "测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长" +
                "测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长" +
                "测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长" +
                "测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长" +
                "测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长" +
                "测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长" +
                "测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长" +
                "测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长" +
                "测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长" +
                "测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长" +
                "测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长" +
                "测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长" +
                "测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长" +
                "测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长" +
                "测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长测试超长");
        userPO.setCreateTime(new Date());
        userPO.setCreateUserId(0L);
        userPO.setIsDel(0);
        userPO.setUpdateTime(new Date());
        userPO.setUpdateUserId(0L);
        userPO.setPassword("123321");
        userRepository.save(userPO);

        UserPO userPO2 = new UserPO();
        userPO2.setUserName("测试超长2");
        userPO2.setCreateTime(new Date());
        userPO2.setCreateUserId(0L);
        userPO2.setIsDel(0);
        userPO2.setUpdateTime(new Date());
        userPO2.setUpdateUserId(0L);
        userPO2.setPassword("123321");
        userRepository.save(userPO2);
    }

    public void findUserAndPosition(){

    }

}