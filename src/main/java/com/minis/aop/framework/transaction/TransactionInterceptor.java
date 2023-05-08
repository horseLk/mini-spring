package com.minis.aop.framework.transaction;

import com.minis.aop.MethodInterceptor;
import com.minis.aop.MethodInvocation;

import java.sql.Connection;

public class TransactionInterceptor implements MethodInterceptor {
    private TransactionManager transactionManager;

    public TransactionManager getTransactionManager() {
        return transactionManager;
    }

    public void setTransactionManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("insert transaction...");
        Connection conn = transactionManager.doBegin();
        ConnectionThreadLocal.set(conn);
        Object ret = null;
        try {
            ret = invocation.proceed();
        } catch (Throwable e) {
            System.out.println("roll back");
            transactionManager.doRollback(conn);
            e.printStackTrace();
            return null;
        }
        System.out.println("commit");
        transactionManager.doCommit(conn);
        return ret;
    }


}
