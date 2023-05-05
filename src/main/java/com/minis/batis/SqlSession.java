package com.minis.batis;

import com.minis.jdbc.core.JdbcTemplate;
import com.minis.jdbc.core.PreparedStatementCallback;

public interface SqlSession {
    void setJdbcTemplate(JdbcTemplate jdbcTemplate);
    void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory);

    void setSqlParser(SqlParser sqlParser);

    Object selectOne(String sqlid, Object[] args, PreparedStatementCallback pstmtcallback);

    int update(String sqlId, Object[] args, PreparedStatementCallback pstmtcallback);
}
