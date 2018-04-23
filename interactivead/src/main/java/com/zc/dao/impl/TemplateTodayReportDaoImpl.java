package com.zc.dao.impl;

import com.zc.dao.TemplateRealtimeReportDao;
import com.zc.dao.TemplateTodayReportDao;
import com.zc.entity.TemplateRealtimeReport;
import com.zc.entity.TemplateTodayReport;
import org.springframework.stereotype.Repository;

/**
 * @Description:
 * @author solar
 * @date 2018年04月08日 17:40
 */
@Repository("TemplateTodayReportDao")
public class TemplateTodayReportDaoImpl extends BaseDaoImpl<TemplateTodayReport> implements TemplateTodayReportDao {

//    private final String demoSqlId = entityName + ".demoId";

//    public List demoMethod(Object obj) {
//        return sessionTemplate.selectList(demoSqlId, obj);
//    }

}