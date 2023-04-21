package org.example.service.impl;

import org.example.service.AopTestService2;

public class AopTestService2Impl implements AopTestService2 {
    @Override
    public void doAction() {
        System.out.println("hello world2");
    }
}
