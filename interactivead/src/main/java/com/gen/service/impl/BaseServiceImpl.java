package com.gen.service.impl;

import java.io.Serializable;
import java.util.List;
import org.solar.bean.Page;
import org.solar.bean.Pageable;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;
import com.gen.service.BaseService;
import com.gen.dao.BaseDao;
@Transactional
public class BaseServiceImpl<T> implements BaseService<T> {

    protected BaseDao<T> baseDao;

    @Transactional(readOnly = true)
    public T getById(Serializable id) {
        return this.baseDao.selectByPrimaryKey(id);
    }

    @Transactional(readOnly = true)
    public T getByCode(Serializable code) {
        List<T> list = this.baseDao.selectByWhere("code", String.valueOf(code));
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public int save(T entity) {
        return this.baseDao.save(entity);
    }

    public int updateByPrimaryKey(T entity) {
        return this.baseDao.updateByPrimaryKeySelective(entity);
    }

    public int delete(T entity) {
        return this.baseDao.delete(entity);
    }

    public int delete(Serializable id) {
        return this.baseDao.delete(id);
    }

    public Page selectPage(Pageable pageable) {
        return baseDao.selectPage(pageable);
    }

    @Transactional(readOnly = true)
    public List<T> selectByWhere(Object map) {
        return baseDao.selectByWhere(map);
    }

    @Transactional(readOnly = true)
    public List<T> selectByWhere(Object... obj) {
        return baseDao.selectByWhere(obj);
    }

    @Transactional(readOnly = true)
    public T selectOneByWhere(Object... obj) {
        List<T> li= baseDao.selectByWhere(obj);
        if (li==null||li.size()==0){
            return null;
        }
        return li.get(0);
    }

    @Transactional(readOnly = true)
    public Long selectCountByWhere(Object... obj) {
        return baseDao.selectCountByWhere(obj);
    }

    public List<Map> executeSql(String sql) {
        return baseDao.executeSql(sql);
    }

}
