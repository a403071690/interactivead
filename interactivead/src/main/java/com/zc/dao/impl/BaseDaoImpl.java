package com.zc.dao.impl;

import java.io.Serializable;
import javax.annotation.Resource;
import org.mybatis.spring.SqlSessionTemplate;
import org.solar.bean.Page;
import org.solar.bean.Pageable;
import org.solar.util.*;
import com.zc.dao.BaseDao;
import org.solar.exception.SolarRuntimeException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public abstract class BaseDaoImpl<T> implements BaseDao<T> {

    @Resource
    protected SqlSessionTemplate sessionTemplate;

    protected List<String> tableFields = new ArrayList();

    public BaseDaoImpl() {
        entityClass = GenericsUtils.getSuperClassGenricType(this.getClass());
        Field[] fieldArray = entityClass.getDeclaredFields();
        for (Field field : fieldArray) {
            tableFields.add(field.getName());
        }
        entityName = entityClass.getSimpleName();
        selectByPrimaryKeyStatement = entityName + ".selectByPrimaryKey";
        deleteByPrimaryKeyStatement = entityName + ".deleteByPrimaryKey";
        selectByWhereStatement = entityName + ".selectByWhere";
        selectByWhereCountStatement = entityName + ".selectByWhereCount";
        insertSelectiveStatement = entityName + ".insertSelective";
        updateByPrimaryKeySelectiveStatement = entityName + ".updateByPrimaryKeySelective";
    }

    public int delete(T entity) {
        Serializable id = (Serializable) GenericsUtils.getFieldValue(entity, "getId");
        return sessionTemplate.delete(deleteByPrimaryKeyStatement, id);
    }

    public int delete(Serializable id) {
        return sessionTemplate.delete(deleteByPrimaryKeyStatement, id);
    }

    public List<T> findByCondition(Map<String, Object> queryMap) {
        List<T> list = sessionTemplate.selectList(selectByWhereStatement, queryMap);
        return list;
    }

    public Page<T> selectPage(Pageable pageAble) {
        Map<String, Object> queryMap = pageAble.getParams();
        queryMap.put("orderProperty", pageAble.getOrderProperty());
        queryMap.put("orderDirection", pageAble.getOrderDirection());
        queryMap.put("pageLimit", pageAble.getPageSize());
        long total;
        if (pageAble.isCountTotalRecord()) {
            total = sessionTemplate.selectOne(selectByWhereCountStatement, queryMap);
        } else {
            total = 2000;
        }
        long totalPage = 0;
        if (total == 0) {
            totalPage = 1;
        } else if (pageAble.getPageSize() == 0) {
            totalPage = 1;
        } else {
            totalPage = total / pageAble.getPageSize();
            if ((total % pageAble.getPageSize()) > 0) {
                totalPage++;
            }
        }
        if (pageAble.getPageNumber() > totalPage) {
            pageAble.setPageNumber(totalPage);
        }
        queryMap.put("pageStart", (pageAble.getPageNumber() - 1) * pageAble.getPageSize());

        List<T> list = selectByWhere(queryMap);

        Page<T> page = new Page<T>(list, total, pageAble);

        return page;
    }


    public T selectByPrimaryKey(Serializable id) {
        if (StringUtil.isEmpty(id)){
            return null;
        }
        return sessionTemplate.selectOne(selectByPrimaryKeyStatement, id);
    }

    public List<Map> executeSql(String sql) {
        return sessionTemplate.selectList("Base.executeSql", sql);
    }

    public List<T> selectByWhere(Object obj) {
        Map map=(Map)obj;
        if (map.get("orderProperty") != null) {
           String orderProperty = (String) map.get("orderProperty");
           if (!tableFields.contains(orderProperty)) {
               throw new SolarRuntimeException(orderProperty + "字段在表中不存在");
           }
           map.put("orderProperty",StringUtil.camel2Underline(orderProperty));
           String orderDirection = (String) map.get("orderDirection");
           if (!"asc".equalsIgnoreCase(orderDirection) && !"desc".equalsIgnoreCase(orderDirection)) {
               map.put("orderDirection","asc");
           }
        };

        return sessionTemplate.selectList(selectByWhereStatement, map);
    }

    public List<T> selectByWhere(Object... obj) {
        Map map = new HashMap();
        for (int i = 0; i < obj.length; i = i + 2) {
            Object value=obj[i + 1];
            if (value instanceof List&&((List)value).isEmpty()){
                return null;
            }
            map.put(obj[i], value);
        }
        return selectByWhere(map);
    }

    public T selectOneByWhere(Object... obj) {
        List<T> li=selectByWhere(obj);
        if (li==null||li.size()==0){
            return null;
        }
        return li.get(0);
    }

    public Long selectCountByWhere(Object... obj) {
        Map map = new HashMap();
        for (int i = 0; i < obj.length; i = i + 2) {
            Object value=obj[i + 1];
            if (value instanceof List&&((List)value).isEmpty()){
                return 0L;
            }
            map.put(obj[i], value);
        }
        return sessionTemplate.selectOne(selectByWhereCountStatement, map);
    }

    final static Class[] dateParam = new Class[]{Date.class};

    public void saveList(List<T> list) {
        for (T t : list) {
            save(t);
        }
    }

    final static Class[] stringParam = new Class[]{String.class};

    public int save(T bean) {
        try {
            Method method = bean.getClass().getMethod("setId", stringParam);
            method.invoke(bean, IDGenerater.getNextId());
            Date nowDate = new Date();
            method = bean.getClass().getMethod("getCreateTime");
            Object obj = method.invoke(bean);
            if (obj == null) {
                method = bean.getClass().getMethod("setCreateTime", dateParam);
                method.invoke(bean, nowDate);
            }
            method = bean.getClass().getMethod("setUpdateTime", dateParam);
            if (method != null) {
                method.invoke(bean, nowDate);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return sessionTemplate.insert(insertSelectiveStatement, bean);

    }

    public int updateByPrimaryKeySelective(T entity) {
        try {
            Method method = entity.getClass().getMethod("setUpdateTime", dateParam);
            if (method != null) {
                method.invoke(entity, new Date());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return sessionTemplate.update(updateByPrimaryKeySelectiveStatement, entity);
    }

    final String entityName;
    final Class<T> entityClass;
    //sql Statement
    final String selectByPrimaryKeyStatement;
    final String deleteByPrimaryKeyStatement;
    final String selectByWhereStatement;
    final String selectByWhereCountStatement;
    final String insertSelectiveStatement;
    final String updateByPrimaryKeySelectiveStatement;
}
