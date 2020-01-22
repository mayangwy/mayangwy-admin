package org.mayangwy.admin.ext;

import cn.hutool.core.util.NumberUtil;
import com.alibaba.druid.filter.FilterEventAdapter;
import com.alibaba.druid.proxy.jdbc.DataSourceProxy;
import com.alibaba.druid.proxy.jdbc.JdbcParameter;
import com.alibaba.druid.proxy.jdbc.ResultSetProxy;
import com.alibaba.druid.proxy.jdbc.StatementProxy;
import lombok.extern.slf4j.Slf4j;
import org.mayangwy.admin.utils.SqlUtils;
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
        logSqlAndParams(statement, logType);
        logQueryCountAndExecuteTime(statement, logType);
    }

    @Override
    protected void statementExecuteUpdateBefore(StatementProxy statement, String sql) {
        setStartTime(statement);
    }

    @Override
    protected void statementExecuteUpdateAfter(StatementProxy statement, String sql, int updateCount) {
        LogType logType = debugEnable ? LogType.DEBUG : LogType.INFO;
        logSqlAndParams(statement, logType);
        logUpdateCountAndExecuteTime(statement, updateCount, logType);
    }

    @Override
    protected void statement_executeErrorAfter(StatementProxy statement, String sql, Throwable error) {
        logSqlAndParams(statement, LogType.ERROR);
    }

    private void setStartTime(StatementProxy statement){
        statement.setLastExecuteStartNano();
    }

    private void logSqlAndParams(StatementProxy statement, LogType logType){
        String oriSql = statement.getLastExecuteSql();
        Map<Integer, JdbcParameter> parameters = statement.getParameters();
        Object[] params = new Object[parameters.size()];
        int i = 0;
        for(Map.Entry<Integer, JdbcParameter> parameter : parameters.entrySet()){
            params[i] = parameter.getValue().getValue();
            i++;
        }
        String mergeSql = SqlUtils.mergeSqlAndParams(oriSql, params);
        log(logType, mergeSql);
    }

    private void logExecuteTypeAndTime(StatementProxy statement, int updateCount, LogType logType){
        String executeType = statement.getLastExecuteType().name();

        String executeTime = getExecuteTime(statement);

        log(logType, "ExecuteType : " + executeType + " , cost time : " + executeTime);
    }

    private void logUpdateCountAndExecuteTime(StatementProxy statement, int updateCount, LogType logType){
        String executeType = statement.getLastExecuteType().name();

        String executeTime = getExecuteTime(statement);

        log(logType, "ExecuteType : " + executeType + " , updateCount : " + updateCount + " , cost time : " + executeTime);
    }

    private void logQueryCountAndExecuteTime(StatementProxy statement, LogType logType){
        String executeType = statement.getLastExecuteType().name();
        try {
            ResultSet resultSet = statement.getRawObject().getResultSet();
            resultSet.last();
            int fetchSize = resultSet.getRow();
            resultSet.beforeFirst();

            String executeTime = getExecuteTime(statement);
            log(logType, "ExecuteType : " + executeType + " , FetchSize : " + fetchSize + " , cost time : " + executeTime);
        } catch (SQLException ignored) {

        }
    }

    private String getExecuteTime(StatementProxy statement){
        statement.setLastExecuteTimeNano();
        double nanos = statement.getLastExecuteTimeNano();
        double millis = nanos / (1000 * 1000);
        return NumberUtil.decimalFormat("#0.00", millis) + " ms";
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