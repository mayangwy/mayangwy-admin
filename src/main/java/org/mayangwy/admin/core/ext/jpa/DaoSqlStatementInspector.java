package org.mayangwy.admin.core.ext.jpa;

import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.mayangwy.admin.core.ext.druid.LogRecordFilter;
import org.mayangwy.admin.core.utils.ThreadLocals;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DaoSqlStatementInspector implements StatementInspector {

    private static String daoSqlLevel;

    @Value("${spring.datasource.druid.filter.logRecord.daoSqlLevel}")
    public void setDaoSqlLevel(String daoSqlLevel){
        DaoSqlStatementInspector.daoSqlLevel = daoSqlLevel;
    }

    @Override
    public String inspect(String sql) {
        LogRecordFilter.threadSqlLevel.set(daoSqlLevel);
        return sql;
    }

}