package org.example.service.impl;

import com.minis.beans.factory.annotation.Autowired;
import org.example.service.AService;
import org.example.service.BaseService;

public class BaseServiceImpl implements BaseService {

    @Autowired
    private AService aservice;

    public AService getAservice() {
        return aservice;
    }

    public void setAservice(AService aservice) {
        this.aservice = aservice;
    }

    @Override
    public void go() {
        aservice.sayHello();
    }
}
