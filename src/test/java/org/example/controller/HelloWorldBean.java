package org.example.controller;

import com.minis.beans.factory.annotation.Autowired;
import com.minis.web.bind.annotation.RequestMapping;
import com.minis.web.bind.annotation.ResponseBody;
import com.minis.web.servlet.ModelAndView;
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
    @ResponseBody
    public Output doGet(Input input) {
        aservice.sayHello();
        return new Output(input.x, input.y);
    }

    @RequestMapping("/error")
    public String error() {
        return "error";
    }

    @RequestMapping("/test")
    public ModelAndView test() {
        ModelAndView mv = new ModelAndView("test");
        mv.addAttribute("msg", "hello world");
        return mv;
    }

    public String doPost() {
        return "hello world!";
    }
}
