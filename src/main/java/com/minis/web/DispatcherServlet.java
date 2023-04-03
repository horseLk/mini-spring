package com.minis.web;

import com.minis.web.servlet.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
public class DispatcherServlet extends HttpServlet {
    public static final String WEB_APPLICATION_CONTEXT_ATTRIBUTE = DispatcherServlet.class.getName() + ".CONTEXT";

    private String sContextConfigLocation;

    private WebApplicationContext webApplicationContext;
    // parentApplicationContext 将 Listener 和 DispatcherServlet 区分开
    private WebApplicationContext parentApplicationContext;

    private HandlerAdapter handlerAdapter;

    private HandlerMapping handlerMapping;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.parentApplicationContext = (WebApplicationContext) this.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

        this.sContextConfigLocation = config.getInitParameter("contextConfigLocation");

        this.webApplicationContext = new AnnotationConfigWebApplicationContext(sContextConfigLocation, this.parentApplicationContext);
        refresh();
    }

    //对所有的mappingValues中注册的类进行实例化，默认构造函数
    protected void refresh() {
        initHandlerMappings(this.webApplicationContext);
        initHandlerAdapters(this.webApplicationContext);
    }

    protected void initHandlerMappings(WebApplicationContext wac) {
        this.handlerMapping = new RequestMappingHandlerMapping(wac);
    }

    protected void initHandlerAdapters(WebApplicationContext wac) {
        this.handlerAdapter = new RequestMappingHandlerAdapter(wac);
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(WEB_APPLICATION_CONTEXT_ATTRIBUTE, this.webApplicationContext);
        try {
            doDispatch(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception{
        HttpServletRequest processedRequest = request;
        HandlerMethod handlerMethod = null;
        handlerMethod = this.handlerMapping.getHandler(processedRequest);
        if (handlerMethod == null) {
            return;
        }

        HandlerAdapter ha = this.handlerAdapter;
        ha.handle(processedRequest, response, handlerMethod);
    }
}
