package org.mayangwy.admin.ext;

import cn.hutool.core.util.NumberUtil;
import com.alibaba.druid.filter.FilterEventAdapter;
import com.alibaba.druid.proxy.jdbc.DataSourceProxy;
import com.alibaba.druid.proxy.jdbc.JdbcParameter;
import com.alibaba.druid.proxy.jdbc.ResultSetProxy;
import com.alibaba.druid.proxy.jdbc.StatementProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 *
 * 自定义打印sql过滤器  create at 2020/1/18
 * @author mayang
 *
 */
@Component
@Slf4j
public class LogRecordFilter extends FilterEventAdapter {

    /**
     *
     * 控制日志打印级别
     *
     */
    @Value("${spring.datasource.druid.filter.logRecord.debugEnable:false}")
    private boolean debugEnable;

    //此处定义一个mergeSQL的配置，控制是否将参数与sql绑定好后打印

    @Override
    public void init(DataSourceProxy dataSource) {
        log.info("init LogRecordFilter");
    }

    /**
     *
     * 查询sql执行前，设置开始时间
     * @param statement
     * @param sql
     *
     */
    @Override
    protected void statementExecuteQueryBefore(StatementProxy statement, String sql) {
        setStartTime(statement);
    }

    /**
     *
     * 查询sql执行后，打印执行sql、入参和执行时间
     * @param statement
     * @param sql
     * @param resultSet
     *
     */
    @Override
    protected void statementExecuteQueryAfter(StatementProxy statement, String sql, ResultSetProxy resultSet) {
        LogType logType = debugEnable ? LogType.DEBUG : LogType.INFO;
        logSqlAndParamsAndExecuteTime(statement, logType);
        logQueryCount(statement, logType);
    }

    @Override
    protected void statementExecuteUpdateBefore(StatementProxy statement, String sql) {
        setStartTime(statement);
    }

    @Override
    protected void statementExecuteUpdateAfter(StatementProxy statement, String sql, int updateCount) {
        LogType logType = debugEnable ? LogType.DEBUG : LogType.INFO;
        logSqlAndParamsAndExecuteTime(statement, logType);
        logUpdateCount(updateCount, logType);
    }

    @Override
    protected void statement_executeErrorAfter(StatementProxy statement, String sql, Throwable error) {
        logSqlAndParamsAndExecuteTime(statement, LogType.ERROR);
    }

    private void setStartTime(StatementProxy statement){
        statement.setLastExecuteStartNano();
    }

    private void logSqlAndParamsAndExecuteTime(StatementProxy statement, LogType logType){
        String executeType = statement.getLastExecuteType().name();
        String oriSql = statement.getLastExecuteSql();
        log(logType, executeType + " - " + oriSql);
        Map<Integer, JdbcParameter> parameters = statement.getParameters();
        for(Map.Entry<Integer, JdbcParameter> parameter : parameters.entrySet()){
            log(logType, executeType + " - Parameter index " + parameter.getKey() + " : " + parameter.getValue().getValue());
        }
        statement.setLastExecuteTimeNano();
        double nanos = statement.getLastExecuteTimeNano();
        double millis = nanos / (1000 * 1000);
        log(logType, executeType + " - cost time : " + NumberUtil.formatPercent(millis, 0) + " ms");
    }

    private void logUpdateCount(int updateCount, LogType logType){
        log(logType, "ExecuteUpdate updateCount : " + updateCount);
    }

    private void logQueryCount(StatementProxy statement, LogType logType){
        try {
            ResultSet resultSet = statement.getRawObject().getResultSet();
            resultSet.last();
            int fetchSize = resultSet.getRow();
            resultSet.beforeFirst();
            log(logType, statement.getLastExecuteType().name() + " - FetchSize : " + fetchSize);
        } catch (SQLException ignored) {

        }
    }

    private void log(LogType logType, String logInfo, Object... objects){
        switch (logType) {
            case DEBUG:
                log.debug(logInfo, objects);
                break;
            case ERROR:
                log.error(logInfo, objects);
                break;
            case INFO:
            default:
                log.info(logInfo, objects);
                break;
        }
    }

    private enum LogType {
        DEBUG, INFO, ERROR;
    }

}