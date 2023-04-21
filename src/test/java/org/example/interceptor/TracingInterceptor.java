package org.example.interceptor;


import com.minis.aop.MethodInterceptor;
import com.minis.aop.MethodInvocation;

public class TracingInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("before invoke");
        Object res = invocation.proceed();
        System.out.println("after invoke");
        return res;
    }
}
