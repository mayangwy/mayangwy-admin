package org.mayangwy.admin.core.ext.jpa;

import cn.hutool.core.util.ClassUtil;
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
import java.util.HashMap;
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
        this.doMainClass = getDomainClass();
        System.out.println(this.doMainClass.getSimpleName());
        Field[] declaredFields = ClassUtil.getDeclaredFields(this.doMainClass);
        if(declaredFields != null){
            for(Field field : declaredFields){
                Id idAnnotation = field.getAnnotation(Id.class);
                if(this.idName == null && idAnnotation != null){
                    this.idName = field.getName();
                }

                IsDel isDelAnnotation = field.getAnnotation(IsDel.class);
                if(this.isDelName == null && isDelAnnotation != null){
                    this.isDelName = field.getName();
                }

                CreateTime createTimeAnnotation = field.getAnnotation(CreateTime.class);
                if(this.createTimeName == null && createTimeAnnotation != null){
                    this.createTimeName = field.getName();
                }

                UpdateTime updateTimeAnnotation = field.getAnnotation(UpdateTime.class);
                if(this.updateTimeName == null && updateTimeAnnotation != null){
                    this.updateTimeName = field.getName();
                }

            }
        }

        if(this.idName == null){
            Method[] declaredMethods = ClassUtil.getDeclaredMethods(this.doMainClass);
            if(declaredMethods != null){
                for(Method method : declaredMethods){
                    Id idAnnotation = method.getAnnotation(Id.class);
                    if(idAnnotation != null){
                        this.idName = method.getName().substring(3);
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

    public Object findById(Serializable id){
        String hql = "from " + doMainClass.getSimpleName() + " where " + idName + " = ? ";
        Map<String, Object> params = new HashMap<>();
        params.put(idName, id);
        if(this.isDelName != null){
            hql = hql + this.isDelName + " = ?";
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