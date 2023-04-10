package com.minis.jdbc.core;

import com.minis.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.*;

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

}
