package org.mayangwy.admin.core.ext.jpa;

import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.mayangwy.admin.core.ext.druid.LogRecordFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DaoSqlLevelStatementInspector implements StatementInspector {

    private static String daoSqlLevel;

    @Value("${spring.datasource.druid.filter.logRecord.daoSqlLevel}")
    public void setDaoSqlLevel(String daoSqlLevel){
        DaoSqlLevelStatementInspector.daoSqlLevel = daoSqlLevel;
    }

    @Override
    public String inspect(String sql) {
        LogRecordFilter.threadSqlLevel.set(daoSqlLevel);
        return sql;
    }

}