package com.minis.web.servlet;

import com.minis.beans.BeansException;
import com.minis.web.context.WebApplicationContext;
import com.minis.web.context.support.AnnotationConfigWebApplicationContext;
import com.minis.web.method.HandlerMethod;
import com.minis.web.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class DispatcherServlet extends HttpServlet {
    public static final String WEB_APPLICATION_CONTEXT_ATTRIBUTE = DispatcherServlet.class.getName() + ".CONTEXT";

    private static final String HANDLER_ADAPTER_BEAN_NAME = "handlerAdapter";

    private static final String VIEW_RESOLVER_BEAN_NAME = "viewResolver";

    private String sContextConfigLocation;

    private WebApplicationContext webApplicationContext;
    // parentApplicationContext 将 Listener 和 DispatcherServlet 区分开
    private WebApplicationContext parentApplicationContext;

    private HandlerAdapter handlerAdapter;

    private HandlerMapping handlerMapping;

    private ViewResolver viewResolver;

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
        initViewResolvers(this.webApplicationContext);
    }

    protected void initHandlerMappings(WebApplicationContext wac) {
        this.handlerMapping = new RequestMappingHandlerMapping(wac);
    }

    protected void initHandlerAdapters(WebApplicationContext wac) {
        try {
            this.handlerAdapter = (HandlerAdapter) wac.getBean(HANDLER_ADAPTER_BEAN_NAME);
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }

    private void initViewResolvers(WebApplicationContext wac) {
        try {
            this.viewResolver = (ViewResolver) wac.getBean(VIEW_RESOLVER_BEAN_NAME);
        } catch (BeansException e) {
            e.printStackTrace();
        }
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
        ModelAndView mav = ha.handle(processedRequest, response, handlerMethod);
        if (mav != null) {
            render(processedRequest, response, mav);
        }
    }

    private void render(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) throws Exception {
        String sTarget = mv.getViewName();
        Map<String, Object> modelMap = mv.getModel();
        View view = resolveViewName(sTarget, modelMap, request);
        view.render(modelMap, request, response);
    }

    private View resolveViewName(String viewName, Map<String, Object> modelMap, HttpServletRequest request) throws Exception {
        if (this.viewResolver != null) {
            View view = viewResolver.resolveViewName(viewName);
            if (view != null) {
                return view;
            }
        }
        return null;
    }
}
