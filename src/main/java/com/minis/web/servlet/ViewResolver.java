package com.minis.web.servlet;

import com.minis.web.servlet.View;

// ViewResolver 就是根据 View 的名字找到实际的 View
public interface ViewResolver {
    View resolveViewName(String viewName) throws Exception;
}
