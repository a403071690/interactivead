package com.zc.dao;

import org.solar.bean.Page;
import org.solar.bean.Pageable;
import java.util.Map;
import java.io.Serializable;
import java.util.List;

public interface BaseDao<T> {

    int delete(T entity);

    int delete(Serializable id);

    Page<T> selectPage(Pageable pageAble);

    T selectByPrimaryKey(Serializable id);

    List<T> selectByWhere(Object map);

    List<T> selectByWhere(Object... obj);

    T selectOneByWhere(Object... obj);

    Long selectCountByWhere(Object... obj);

    int save(T entity);

    void saveList(List<T> list);

    int updateByPrimaryKeySelective(T entity);

    List<Map> executeSql(String sql);

}
