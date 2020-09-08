package com.xuegao.springboot_tool.mybatis.interceptor;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

/**
 * <br/> @PackageName：com.xuegao.springboot_tool.mybatis.interceptor
 * <br/> @ClassName：PrintSqlInterceptor
 * <br/> @Description：
 * <br/> @author：花名 xuegao
 * <br/> @date：2020/9/8 18:47
 */
// 执行 Executor (update, query, flushStatements, commit, rollback, getTransaction, close, isClosed)
// 请求参数处理 ParameterHandler (getParameterObject, setParameters)
// 返回结果集处理 ResultSetHandler (handleResultSets, handleOutputParameters)
// SQL语句构建 StatementHandler (prepare, parameterize, batch, update, query)
// @Intercepts({
//         @Signature(type = ParameterHandler.class, method = "setParameters", args = PreparedStatement.class),
//         @Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}),
//         @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}),
//         @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
//         @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})
// })

@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class PrintSqlInterceptor implements Interceptor {
    private final static Logger log = LoggerFactory.getLogger(PrintSqlInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // invocation.getArgs()的结果，一个是MappedStatement，一个是参数信息。
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = null;
        if (invocation.getArgs().length > 1) {
            parameter = invocation.getArgs()[1];
        }

        String sqlId = mappedStatement.getId();
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        Configuration configuration = mappedStatement.getConfiguration();
        long start = System.currentTimeMillis();
        Object returnValue = invocation.proceed();
        long time = System.currentTimeMillis() - start;
        showSql(configuration, boundSql, time, sqlId);
        return returnValue;

        // 作者：南诏2018
        // 链接：https://juejin.im/post/6869587337874472974
        // 来源：掘金
        // 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    }

    // showSql里把?替换为参数的真实值。
    private static void showSql(Configuration configuration, BoundSql boundSql, long time, String sqlId) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        //替换空格、换行、tab缩进等
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (parameterMappings.size() > 0 && parameterObject != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));
            } else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    }
                }
            }
        }
        logs(time, sql, sqlId);
    }

    private static String getParameterValue(Object obj) {
        String value;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(new Date()) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }
        }
        return value.replace("$", "\\$");
    }

    // logs拼装日志。
    private static void logs(long time, String sql, String sqlId) {
        StringBuilder sb = new StringBuilder()
                .append(" Time：").append(time)
                .append(" ms - ID：").append(sqlId)
                .append(StringPool.NEWLINE).append("Execute SQL：")
                .append(sql).append(StringPool.NEWLINE);
        log.info(sb.toString());
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}