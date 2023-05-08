package com.minis.aop.framework.transaction;

import com.minis.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;

public class TransactionManager {
    @Autowired
    private DataSource dataSource;


    protected Connection doBegin() {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    protected void doCommit(Connection conn) {
        if (conn != null) {
            try {
                conn.commit();
                conn.setAutoCommit(true);
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void doRollback(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
                conn.setAutoCommit(true);
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
