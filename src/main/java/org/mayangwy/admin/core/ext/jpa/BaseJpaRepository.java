package org.mayangwy.admin.core.ext.jpa;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ClassUtil;
import org.mayangwy.admin.core.base.annotation.*;
import org.mayangwy.admin.core.base.entity.PageInput;
import org.mayangwy.admin.core.base.entity.PageOutput;
import org.mayangwy.admin.core.base.enums.IsDelEnum;
import org.mayangwy.admin.core.utils.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.Query;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public class BaseJpaRepository<T> {

    @Autowired
    protected EntityManager entityManager;

    private Class<T> doMainClass;

    private String idName;

    private String isDelName;

    private String createTimeName;

    private String updateTimeName;

    private String createUserIdName;

    private String updateUserIdName;

    public BaseJpaRepository(){
        doMainClass = getDomainClass();
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

                CreateUserId createUserIdAnnotation = field.getAnnotation(CreateUserId.class);
                if(createUserIdName == null && createUserIdAnnotation != null){
                    createUserIdName = field.getName();
                }

                UpdateUserId updateUserIdAnnotation = field.getAnnotation(UpdateUserId.class);
                if(updateUserIdName == null && updateUserIdAnnotation != null){
                    updateUserIdName = field.getName();
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

    public T save(T entity){
        Assert.notNull(entity, "entity is not null");
        return save(entity, new Date());
    }

    private T save(T entity, Date nowDate){
        Object id = getEntityFieldValue(idName, entity);

        if(id == null){
            preAdd(entity, nowDate);
            entityManager.persist(entity);
            return entity;
        } else {
            preUpdate(entity, nowDate);
            return entityManager.merge(entity);
        }
    }

    public List<T> saveAll(List<T> entityList){
        Assert.notEmpty(entityList, "entity is not empty");

        Date nowDate = new Date();
        List<T> result = new ArrayList<>();

        for(T entity : entityList){
            T saveResult = save(entity, nowDate);
            result.add(saveResult);
        }

        return result;
    }

    public int deleteLogicById(Serializable id){
        return deleteLogicByIds(Collections.singletonList(id));
    }

    public int deleteLogicByIds(List<Serializable> ids){
        Assert.notBlank(isDelName, "isDelName is not blank");
        Assert.notEmpty(ids, "ids is not empty");

        String hql = "update " + doMainClass.getSimpleName() + " set "
                + isDelName + " = :" + isDelName + " , "
                + updateTimeName + " = :" + updateTimeName + " "
                + " where ";

        if(ids.size() == 1){
            hql = hql + idName + " = :" + idName;
        } else {
            hql = hql + idName + " in (:" + idName + ") ";
        }

        Map<String, Object> params = new HashMap<>();
        params.put(isDelName, IsDelEnum.NO.getCode());
        params.put(updateTimeName, new Date());
        params.put(idName, ids);

        return executeUpdate(hql, params);
    }

    public int deleteById(Serializable id){
        return deleteByIds(Collections.singletonList(id));
    }

    public int deleteByIds(List<Serializable> ids){
        Assert.notBlank(idName, "idName is not blank");
        Assert.notEmpty(ids, "ids is not empty");

        String hql = "delete from " + doMainClass.getSimpleName() + " where " + idName + " = :" + idName;

        Map<String, Object> params = new HashMap<>();
        params.put(idName, ids);

        return executeUpdate(hql, params);
    }

    public <I, O> O findOne(String hql, I input){
        List<O> objects = find(hql, input, null);
        if(CollUtil.isEmpty(objects)){
            return null;
        }
        if(objects.size() > 1){
            throw new RuntimeException("resultSet not more than 1");
        }

        return objects.get(0);
    }

    public <I, O> PageOutput<O> findPage(String hql, String countHql, I input, PageInput pageInput){
        Assert.notNull(pageInput, "pageInput is not null");

        List<O> objects = find(hql, input, pageInput);

        PageOutput<O> pageOutput = new PageOutput<>();
        pageOutput.setList(objects);

        if(! pageInput.isCount()){
            return pageOutput;
        }

        Long count = findCount(countHql, input);
        pageOutput.setCount(count);

        return pageOutput;
    }

    public <I> Long findCount(String hql, I input){
        return findOne(hql, input);
    }

    public <I, O> List<O> findList(String hql, I input){
        return find(hql, input, null);
    }

    private <I, O> List<O> find(String hql, I input, PageInput pageInput){
        Assert.notNull(hql, "hql is not null");

        Map<String, Object> params;
        if(input instanceof Map){
            params = (Map<String, Object>) input;
        } else {
            params = BeanUtil.beanToMap(input, false, true);
        }
        Query query = entityManager.createQuery(hql);
        if(params != null){
            params.forEach(query::setParameter);
        }
        if(pageInput != null){
            query.setFirstResult(pageInput.getCurrentPage()*pageInput.getPageSize());
            query.setMaxResults(pageInput.getPageSize());
        }

        List resultList = query.getResultList();

        return (List<O>)resultList;
    }

    public <I> int executeUpdate(String hql, I input){
        Query query = entityManager.createQuery(hql);

        Map<String, Object> params;
        if(input instanceof Map){
            params = (Map<String, Object>) input;
        } else {
            params = BeanUtil.beanToMap(input, false, true);
        }
        params.forEach(query::setParameter);

        return query.executeUpdate();
    }

    private void preAdd(T entity, Date nowDate){
        setEntityFieldValueByName(createTimeName, entity, nowDate);
        setEntityFieldValueByName(updateTimeName, entity, nowDate);
        setEntityFieldValueByName(isDelName, entity, IsDelEnum.YES.getCode());
    }

    private void preUpdate(T entity, Date nowDate){
        setEntityFieldValueByName(updateTimeName, entity, nowDate);
        setEntityFieldValueByName(isDelName, entity, IsDelEnum.YES.getCode());
    }

    private void preDelete(T entity, Date nowDate){
        setEntityFieldValueByName(updateTimeName, entity, nowDate);
        setEntityFieldValueByName(isDelName, entity, IsDelEnum.NO.getCode());
    }

    private void setEntityFieldValueByName(String fieldName, Object entity, Object value){
        try {
            if(fieldName != null){
                String getterName = StrUtils.getGetterName(fieldName);
                Method publicGetterMethod = ClassUtil.getPublicMethod(doMainClass, getterName);
                if(publicGetterMethod != null){
                    Object getValue = publicGetterMethod.invoke(entity);
                    if(getValue == null){
                        String setterName = StrUtils.getSetterName(fieldName);
                        Method publicSetterMethod = ClassUtil.getPublicMethod(doMainClass, setterName, publicGetterMethod.getReturnType());
                        if(publicSetterMethod != null){
                            publicSetterMethod.invoke(entity, value);
                        }
                    }
                }
            }
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private Object getEntityFieldValue(String fieldName, Object entity, Object... params){
        try {
            if(fieldName != null){
                String getterName = StrUtils.getGetterName(fieldName);
                Method publicGetterMethod = ClassUtil.getPublicMethod(doMainClass, getterName);
                if(publicGetterMethod != null){
                    return publicGetterMethod.invoke(entity, params);
                }
            }
        } catch (Exception e){
            throw new RuntimeException(e);
        }

        return null;
    }

    private Class<T> getDomainClass(){
        Type superClass = this.getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) superClass;
        Type actualTypeArgument = parameterizedType.getActualTypeArguments()[0];
        return (Class<T>)actualTypeArgument;
    }

}