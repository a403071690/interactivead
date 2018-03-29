package com.zc.service;

import org.solar.bean.Page;
import org.solar.bean.Pageable;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BaseService<T> {

     T getById(Serializable id);

     T getByCode(Serializable id);

     int save(T entity);

     int updateByPrimaryKey(T entity);

     int delete(T entity);

     int delete(Serializable id);

     Page selectPage(Pageable pageable);

     List<T> selectByWhere(Object map);

      /**
       * 参数格式为[属性,搜索值,属性,搜索值,...]
       * where in 参数格式为 "属性+List",搜索值(List或数组)
       */
     List<T> selectByWhere(Object... obj);

     T selectOneByWhere(Object... obj);

     Long selectCountByWhere(Object... obj);

     List<Map> executeSql(String sql);

}