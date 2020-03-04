package org.mayangwy.admin.core.ext.jpa;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ClassUtil;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.mayangwy.admin.core.base.annotation.CreateTime;
import org.mayangwy.admin.core.base.annotation.IsDel;
import org.mayangwy.admin.core.base.annotation.UpdateTime;
import org.mayangwy.admin.core.base.enums.IsDelEnum;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.Query;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseJpaRepository<T> {

    @Autowired
    protected EntityManager entityManager;

    private Class<T> doMainClass;

    private String idName;

    private String isDelName;

    private String createTimeName;

    private String updateTimeName;

    public BaseJpaRepository(){
        doMainClass = getDomainClass();
        System.out.println(doMainClass.getSimpleName());
        Field[] declaredFields = ClassUtil.getDeclaredFields(doMainClass);
        if(declaredFields != null){
            for(Field field : declaredFields){
                Id idAnnotation = field.getAnnotation(Id.class);
                if(idName == null && idAnnotation != null){
                    idName = field.getName();
                }

                IsDel isDelAnnotation = field.getAnnotation(IsDel.class);
                if(isDelName == null && isDelAnnotation != null){
                    isDelName = field.getName();
                }

                CreateTime createTimeAnnotation = field.getAnnotation(CreateTime.class);
                if(createTimeName == null && createTimeAnnotation != null){
                    createTimeName = field.getName();
                }

                UpdateTime updateTimeAnnotation = field.getAnnotation(UpdateTime.class);
                if(updateTimeName == null && updateTimeAnnotation != null){
                    updateTimeName = field.getName();
                }

            }
        }

        if(idName == null){
            Method[] declaredMethods = ClassUtil.getDeclaredMethods(this.doMainClass);
            if(declaredMethods != null){
                for(Method method : declaredMethods){
                    Id idAnnotation = method.getAnnotation(Id.class);
                    if(idAnnotation != null){
                        idName = method.getName().substring(3);
                        break;
                    }
                }
            }
        }
    }

    public void add(T entity){
        entityManager.persist(entity);
    }

    public T update(T entity){
        return entityManager.merge(entity);
    }

    public T save(T entity){
        Field declaredField = ClassUtil.getDeclaredField(doMainClass, idName);
        Object id;
        try {
            id = declaredField.get(entity);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        if(id == null){
            add(entity);
            return entity;
        } else {
            return update(entity);
        }
    }

    private void setCommonValue(List<T> entityList){
        if(CollUtil.isEmpty(entityList)){
            return;
        }
        Field createTimeField = ClassUtil.getDeclaredField(doMainClass, createTimeName);
        Field updateTimeField = ClassUtil.getDeclaredField(doMainClass, updateTimeName);
        Field isDelField = ClassUtil.getDeclaredField(doMainClass, isDelName);

        Date nowDate = new Date();

    }

    public Object findById(Serializable id){
        String hql = "from " + doMainClass.getSimpleName() + " where " + idName + " = ? ";
        Map<String, Object> params = new HashMap<>();
        params.put(idName, id);
        if(isDelName != null){
            hql = hql + isDelName + " = ?";
            params.put(isDelName, IsDelEnum.YES);
        }
        Query query = entityManager.createQuery(hql);
        params.forEach(query::setParameter);
        return query.getResultList().get(0);
    }

    private Class<T> getDomainClass(){
        Type superClass = this.getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) superClass;
        Type actualTypeArgument = parameterizedType.getActualTypeArguments()[0];
        return (Class<T>)actualTypeArgument;

    }

}