package com.minis.web.method.annotation;

import com.minis.web.*;
import com.minis.web.bind.WebDataBinder;
import com.minis.web.bind.annotation.ResponseBody;
import com.minis.web.bind.support.WebBindingInitializer;
import com.minis.web.bind.support.WebDataBinderFactory;
import com.minis.web.method.HandlerMethod;
import com.minis.web.servlet.HandlerAdapter;
import com.minis.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class RequestMappingHandlerAdapter implements HandlerAdapter {

    private WebBindingInitializer webBindingInitializer = null;
    private HttpMessageConverter httpMessageConverter = null;
    public WebBindingInitializer getWebBindingInitializer() {
        return webBindingInitializer;
    }

    public void setWebBindingInitializer(WebBindingInitializer webBindingInitializer) {
        this.webBindingInitializer = webBindingInitializer;
    }

    public HttpMessageConverter getHttpMessageConverter() {
        return httpMessageConverter;
    }

    public void setHttpMessageConverter(HttpMessageConverter httpMessageConverter) {
        this.httpMessageConverter = httpMessageConverter;
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return handleInternal(request, response, (HandlerMethod) handler);
    }

    private ModelAndView handleInternal(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) {
        ModelAndView mav = null;
        try {
            mav = invokeHandlerMethod(request, response, handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mav;

    }

    private ModelAndView invokeHandlerMethod(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) throws Exception {
        WebDataBinderFactory binderFactory = new WebDataBinderFactory();
        Parameter[] methodParameters = handlerMethod.getMethod().getParameters();
        Object[] methodParamObjs = new Object[methodParameters.length];

        int i = 0;
        for (Parameter methodParameter: methodParameters) {
            Object methodParamObj = methodParameter.getType().newInstance();
            // 给这个参数创造WebDataBinder
            WebDataBinder wdb = binderFactory.createBinder(request, methodParamObj, methodParameter.getName());
            if (webBindingInitializer != null) {
                webBindingInitializer.initBinder(wdb);
            }
            wdb.bind(request);
            methodParamObjs[i] = methodParamObj;
            i++;
        }
        Method invocableMethod = handlerMethod.getMethod();

        ModelAndView mav = null;
        Object returnObj = invocableMethod.invoke(handlerMethod.getBean(), methodParamObjs);
        if (invocableMethod.isAnnotationPresent(ResponseBody.class)) { // responseBody
            this.httpMessageConverter.write(returnObj, response);
        } else {
            if (returnObj instanceof ModelAndView) {
                mav = (ModelAndView)returnObj;
            } else if (returnObj instanceof String) { // 字符串也认为是前端页面
                String sTarget = (String)returnObj;
                mav = new ModelAndView();
                mav.setViewName(sTarget);
            }
        }
        return mav;
    }
}
