package org.mayangwy.admin.ext;

import com.alibaba.druid.filter.FilterEventAdapter;
import com.alibaba.druid.proxy.jdbc.JdbcParameter;
import com.alibaba.druid.proxy.jdbc.ResultSetProxy;
import com.alibaba.druid.proxy.jdbc.StatementProxy;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class LogRecordFilter extends FilterEventAdapter {

    @Override
    protected void statementExecuteQueryBefore(StatementProxy statement, String sql) {
        setStartTime(statement);
    }

    @Override
    protected void statementExecuteQueryAfter(StatementProxy statement, String sql, ResultSetProxy resultSet) {
        logSqlAndParamsAndExecuteTime(statement);
    }

    @Override
    protected void statementExecuteUpdateBefore(StatementProxy statement, String sql) {
        setStartTime(statement);
    }

    @Override
    protected void statementExecuteUpdateAfter(StatementProxy statement, String sql, int updateCount) {
        logSqlAndParamsAndExecuteTime(statement);
        logUpdateCount(updateCount);
    }

    @Override
    protected void statement_executeErrorAfter(StatementProxy statement, String sql, Throwable error) {
        logSqlAndParamsAndExecuteTime(statement);
    }

    private void setStartTime(StatementProxy statement){
        statement.setLastExecuteTimeNano(System.currentTimeMillis());
    }

    private void logSqlAndParamsAndExecuteTime(StatementProxy statement){
        String executeType = statement.getLastExecuteType().name();
        String oriSql = statement.getLastExecuteSql();
        log.info(executeType + " - " + oriSql);
        Map<Integer, JdbcParameter> parameters = statement.getParameters();
        for(Map.Entry<Integer, JdbcParameter> parameter : parameters.entrySet()){
            log.info(executeType + " - Parameter index " + parameter.getKey() + " : " + parameter.getValue().getValue());
        }
        log.info(executeType + " - cost time : " + (System.currentTimeMillis() - statement.getLastExecuteTimeNano()));
    }

    private void logUpdateCount(int updateCount){
        log.info("executeUpdate updateCount : " + updateCount);
    }

}