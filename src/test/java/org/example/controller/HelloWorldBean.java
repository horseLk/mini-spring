package org.example.controller;

import com.minis.beans.factory.annotation.Autowired;
import com.minis.web.RequestMapping;
import org.example.service.AService;

public class HelloWorldBean {

    @Autowired
    private AService aservice;

    public AService getAservice() {
        return aservice;
    }

    public void setAservice(AService aservice) {
        this.aservice = aservice;
    }

    @RequestMapping("/helloworld")
    public String doGet() {
        aservice.sayHello();
        return "hello world!";
    }

    public String doPost() {
        return "hello world!";
    }
}
