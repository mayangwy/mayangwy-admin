package org.mayangwy.admin.core.ext.jpa;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.query.spi.QueryImplementor;
import org.hibernate.transform.Transformers;
import org.mayangwy.admin.core.base.annotation.CreateTime;
import org.mayangwy.admin.core.base.annotation.IsDel;
import org.mayangwy.admin.core.base.annotation.UpdateTime;
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
import java.util.stream.Collectors;

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

    public Object deleteLogic(Serializable id){
        Assert.notBlank(isDelName, "isDelName is not blank");
        Assert.notNull(id, "id is not null");

        String hql = "update " + doMainClass.getSimpleName() + " set "
                + isDelName + " = :"+ isDelName +" , " + updateTimeName + " = : " + updateTimeName + " where "
                + idName + " = :" + idName;

        Map<String, Object> params = new HashMap<>();
        params.put(isDelName, IsDelEnum.NO.getCode());
        params.put(updateTimeName, new Date());
        params.put(idName, id);

        Query query = entityManager.createQuery(hql);
        params.forEach(query::setParameter);
        return query.executeUpdate();
    }

    public Object deleteLogics(List<Serializable> ids){
        Assert.notBlank(isDelName, "isDelName is not blank");
        Assert.notEmpty(ids, "ids is not empty");

        String hql = "update " + doMainClass.getSimpleName() + " set "
                + isDelName + " = :" + isDelName + " , " + updateTimeName + " = :" + updateTimeName + " " + " where "
                + idName + " in (:" + idName + ") ";

        Map<String, Object> params = new HashMap<>();
        params.put(isDelName, IsDelEnum.NO.getCode());
        params.put(updateTimeName, new Date());
        params.put(idName, ids);

        Query query = entityManager.createQuery(hql);
        params.forEach(query::setParameter);
        return query.executeUpdate();
    }

    public Object delete(Serializable id){
        Assert.notBlank(idName, "idName is not blank");
        Assert.notNull(id, "id is not null");

        String hql = "delete from " + doMainClass.getSimpleName() + " where " + idName + " = :" + idName;

        Map<String, Object> params = new HashMap<>();
        params.put(idName, id);

        Query query = entityManager.createQuery(hql);
        params.forEach(query::setParameter);
        return query.executeUpdate();
    }

    public T findById(Serializable id){
        Assert.notNull(id, "id is not null");

        String hql = "from " + doMainClass.getSimpleName() + " where " + idName + " = :"+ idName;
        Map<String, Object> params = new HashMap<>();
        params.put(idName, id);
        if(isDelName != null){
            hql = hql + " and " + isDelName + " = :" + isDelName;
            params.put(isDelName, IsDelEnum.YES);
        }
        Query query = entityManager.createQuery(hql);
        params.forEach(query::setParameter);
        List resultList = query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(doMainClass)).getResultList();
        return (T)resultList.get(0);
    }

    public List<T> findAll(){
        if(StrUtil.isBlank(isDelName)){
            String hql = "from " + doMainClass.getSimpleName();
            return find(hql, doMainClass);
        } else {
            String hql = "from " + doMainClass.getSimpleName() + " where " + isDelName + " = :" + isDelName;
            HashMap<String, Object> params = MapUtil.newHashMap();
            params.put(isDelName, IsDelEnum.YES.getCode());
            return find(hql, params, doMainClass);
        }
    }

    public <I, O> List<O> find(String hql, Class<O> cla){
        return find(hql, null, cla);
    }

    public <I, O> List<O> find(String hql, I input, Class<O> cla){
        Map<String, Object> params = BeanUtil.beanToMap(input);
        Query query = entityManager.createQuery(hql);
        if(params != null){
            params.forEach(query::setParameter);
        }
        List resultList = query.unwrap(QueryImplementor.class).setResultTransformer(Transformers.aliasToBean(cla)).getResultList();

        return (List<O>)resultList;
    }

    public <I> int executeUpdate(String hql, I input){
        Map<String, Object> params = BeanUtil.beanToMap(input);
        Query query = entityManager.createQuery(hql);
        params.forEach(query::setParameter);
        return query.executeUpdate();
    }

    public <I, O> List<O> findBySql(String sql, Class<O> cla){
        return findBySql(sql, null, cla);
    }

    public <I, O> List<O> findBySql(String sql, I input, Class<O> cla){
        Map<String, Object> params = BeanUtil.beanToMap(input);
        Query query = entityManager.createNativeQuery(sql);
        params.forEach(query::setParameter);
        List resultList = query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(cla)).getResultList();
        return (List<O>)resultList;
    }

    public <I> int executeUpdateBySql(String sql, I input){
        Map<String, Object> params = BeanUtil.beanToMap(input);
        Query query = entityManager.createNativeQuery(sql);
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