package org.example.service.impl;

import org.example.service.AopTestService;

public class AopTestServiceImpl implements AopTestService {
    @Override
    public void doAction() {
        System.out.println("aop test");
    }
}
