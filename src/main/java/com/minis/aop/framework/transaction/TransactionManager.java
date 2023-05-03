package com.minis.aop.framework.transaction;

import com.minis.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;

public class TransactionManager {
    @Autowired
    private DataSource dataSource;

    Connection conn = null;

    protected void doBegin() {
        try {
            conn = dataSource.getConnection();
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doCommit() {
        if (conn != null) {
            try {
                conn.commit();
                conn.setAutoCommit(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void doRollback() {
        if (conn != null) {
            try {
                conn.rollback();
                conn.setAutoCommit(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
