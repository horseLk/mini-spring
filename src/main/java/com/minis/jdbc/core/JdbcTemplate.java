package com.minis.jdbc.core;

import com.minis.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class JdbcTemplate {
    @Autowired
    private DataSource dataSource;

    public JdbcTemplate() {
    }

    public Object query(StatementCallback stmtCallback) {
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.createStatement();
            return stmtCallback.doInStatement(stmt);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Object query(String sql, Object[] args, PreparedStatementCallback pstmtCallback) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            ArgumentPreparedStatementSetter argumentSetter = new ArgumentPreparedStatementSetter(args);
            argumentSetter.setValues(pstmt);
            return pstmtCallback.doInPreparedStatement(pstmt);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public <T> List<T> query(String sql, Object[] args, RowMapper<T> rowMapper) {
        RowMapperResultSetExtractor<T> resultExtractor = new RowMapperResultSetExtractor<>(rowMapper);
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            ArgumentPreparedStatementSetter argumentSetter = new ArgumentPreparedStatementSetter(args);
            argumentSetter.setValues(pstmt);
            rs = pstmt.executeQuery();
            return resultExtractor.extractData(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();;
                pstmt.close();
                conn.close();
            } catch (Exception ignore){
            }
        }

        return null;
    }

    public Integer update(StatementCallback stmtCallback) {
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = dataSource.getConnection();
            stmt = conn.createStatement();
            return (Integer) stmtCallback.doInStatement(stmt);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public Integer update(String sql, Object[] args, PreparedStatementCallback pstmtCallback) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            ArgumentPreparedStatementSetter argumentSetter = new ArgumentPreparedStatementSetter(args);
            argumentSetter.setValues(pstmt);
            return (Integer) pstmtCallback.doInPreparedStatement(pstmt);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

}
