package com.minis.aop.framework.transaction;

import java.sql.Connection;

public class ConnectionThreadLocal {
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    public static void set(Connection connection) {
        threadLocal.set(connection);
    }

    public static Connection get() {
        return threadLocal.get();
    }
}
