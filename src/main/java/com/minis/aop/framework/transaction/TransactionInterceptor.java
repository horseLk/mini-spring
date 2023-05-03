package com.minis.aop.framework.transaction;

import com.minis.aop.MethodInterceptor;
import com.minis.aop.MethodInvocation;

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
        transactionManager.doBegin();
        Object ret = null;
        try {
            ret = invocation.proceed();
        } catch (Throwable e) {
            transactionManager.doRollback();
            e.printStackTrace();
        }
        transactionManager.doCommit();
        return ret;
    }
}
