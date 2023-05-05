package com.minis.batis;

import com.minis.jdbc.core.JdbcTemplate;
import com.minis.jdbc.core.PreparedStatementCallback;

import java.lang.reflect.Field;
import java.util.Date;

public class DefaultSqlSession implements SqlSession {
    private JdbcTemplate jdbcTemplate;

    private SqlSessionFactory sqlSessionFactory;

    private SqlParser sqlParser;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    public SqlParser getSqlParser() {
        return sqlParser;
    }

    @Override
    public void setSqlParser(SqlParser sqlParser) {
        this.sqlParser = sqlParser;
    }

    @Override
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public Object selectOne(String sqlId, Object[] args, PreparedStatementCallback pstmtcallback) {
        MapperNode node = this.sqlSessionFactory.getMapperNode(sqlId);
        if (node == null) {
            throw new RuntimeException("sqlId node exist.");
        }
        String sql = node.getSql();
        args = processArgs(args, node.getAttrs());
        return jdbcTemplate.query(sql, args, pstmtcallback);
    }

    public int update(String sqlId, Object[] args, PreparedStatementCallback pstmtcallback) {
        MapperNode node = this.sqlSessionFactory.getMapperNode(sqlId);
        if (node == null) {
            throw new RuntimeException("sqlId node exist.");
        }
        String sql = node.getSql();
        args = processArgs(args, node.getAttrs());
        return jdbcTemplate.update(sql, args, pstmtcallback);
    }

    private Object[] processArgs(Object[] args, String[] attrs) {
        if (attrs.length == 0) {
            return args;
        }
        if (args.length == 0) {
            throw new RuntimeException("parameter is empty.");
        }
        Object target = args[0];
        if (isBaseType(target)) {
            return args;
        }
        Class<?> targetClass = target.getClass();
        Object[] newArgs = new Object[attrs.length];
        for (int i = 0; i < attrs.length; i++) {
            try {
                Field f = targetClass.getDeclaredField(attrs[i]);
                f.setAccessible(true);
                newArgs[i] = f.get(target);
            } catch (Exception e) {
                throw new RuntimeException("field " + attrs[i] + "not found in class " + targetClass.getName());
            }
        }
        return newArgs;
    }

    private boolean isBaseType(Object argValue) {
        Object arg = argValue;
        //判断参数类型，调用相应的JDBC set方法
        if (arg instanceof String) {
            return true;
        } else if (arg instanceof Integer) {
            return true;
        } else return arg instanceof Date;
    }

    private void buildParameter(){
    }

    private Object resultSet2Obj() {
        return null;
    }
}
