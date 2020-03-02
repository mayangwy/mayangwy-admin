package org.mayangwy.admin.modules.system.service;

import cn.hutool.core.util.RandomUtil;
import org.mayangwy.admin.modules.system.dao.UserRepository;
import org.mayangwy.admin.modules.system.entity.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
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
        //List<UserPO> all = userRepository.findAll();
        String s = userRepository.testNative(2L);
        System.out.println(s);
        List<UserPO> all = userRepository.findAllById(Arrays.asList(2L, 3L, 4L));
        for (int i = 0; i < all.size(); i++) {
            if(i%2 == 0){
                all.get(i).setBbb(new BigDecimal("222"));
                all.get(i).setCcc(new Long("333"));
                all.get(i).setDdd(444L);
                all.get(i).setEee(111.22);
                all.get(i).setFff(222.33);
            } else {
                all.get(i).setBbb(new BigDecimal("11111222223333344444.00000000023"));
                all.get(i).setCcc(new Long("333334444455555666"));
                all.get(i).setDdd(444441111122222777L);
                all.get(i).setEee(1113334454354335535111.000000000222);
                all.get(i).setFff(2223323342234234242433.000000000333);
            }
        }
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
        /*UserPO userPO = new UserPO();
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
        userRepository.save(userPO);*/

        UserPO userPO2 = new UserPO();
        userPO2.setUserName("测试超长3");
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