package com.minis.batis;

import com.minis.jdbc.core.JdbcTemplate;
import com.minis.jdbc.core.PreparedStatementCallback;

public class DefaultSqlSession implements SqlSession {
    private JdbcTemplate jdbcTemplate;

    private SqlSessionFactory sqlSessionFactory;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
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
        return jdbcTemplate.query(sql, args, pstmtcallback);
    }

    private void buildParameter(){
    }

    private Object resultSet2Obj() {
        return null;
    }
}
