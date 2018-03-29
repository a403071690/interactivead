package com.zc.service.impl;

import java.io.Serializable;
import java.util.List;
import org.solar.bean.Page;
import org.solar.bean.Pageable;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;
import com.zc.service.BaseService;
import com.zc.dao.BaseDao;
@Transactional
public class BaseServiceImpl<T> implements BaseService<T> {

    protected BaseDao<T> baseDao;

    @Override
    @Transactional(readOnly = true)
    public T getById(Serializable id) {
        return this.baseDao.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(readOnly = true)
    public T getByCode(Serializable code) {
        List<T> list = this.baseDao.selectByWhere("code", String.valueOf(code));
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public int save(T entity) {
        return this.baseDao.save(entity);
    }

    @Override
    public int updateByPrimaryKey(T entity) {
        return this.baseDao.updateByPrimaryKeySelective(entity);
    }

    @Override
    public int delete(T entity) {
        return this.baseDao.delete(entity);
    }

    @Override
    public int delete(Serializable id) {
        return this.baseDao.delete(id);
    }

    @Override
    public Page selectPage(Pageable pageable) {
        return baseDao.selectPage(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> selectByWhere(Object map) {
        return baseDao.selectByWhere(map);
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> selectByWhere(Object... obj) {
        return baseDao.selectByWhere(obj);
    }

    @Override
    @Transactional(readOnly = true)
    public T selectOneByWhere(Object... obj) {
        List<T> li= baseDao.selectByWhere(obj);
        if (li==null||li.size()==0){
            return null;
        }
        return li.get(0);
    }

    @Override
    @Transactional(readOnly = true)
    public Long selectCountByWhere(Object... obj) {
        return baseDao.selectCountByWhere(obj);
    }

    @Override
    public List<Map> executeSql(String sql) {
        return baseDao.executeSql(sql);
    }

}
