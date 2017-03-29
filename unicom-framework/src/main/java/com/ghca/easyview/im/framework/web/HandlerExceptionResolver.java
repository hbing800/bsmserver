package com.ghca.easyview.im.framework.web;

import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * Description  全局异常处理类
 *
 * Created by bingbing on 2017/3/27.
 */
public class HandlerExceptionResolver extends SimpleMappingExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // Expose ModelAndView for chosen error view.
        String viewName = determineViewName(ex, request);

        if (null != ex) {//打印异常到控制台
            //ex.printStackTrace();
            LoggerFactory.getLogger(viewName == null ? this.getClass() + "" : viewName).error(ex.getMessage(), ex);

        }

        String accept = request.getHeader("accept");
        boolean isAjaxRequest = false;
        if (accept != null && accept.indexOf("application/json") != -1) {
            isAjaxRequest = true;
            System.out.println("isAjaxRequest="+isAjaxRequest);
        }


        if (isAjaxRequest) {
            PrintWriter writer = null;
            try {
                try {
                    writer = response.getWriter();
                    response.setStatus(500);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                writer.write(ex.getMessage());
                writer.flush();
                writer.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        } else if (viewName != null) {
            // Apply HTTP status code for error views, if specified.
            // Only apply it if we're processing a top-level request.
            Integer statusCode = determineStatusCode(request, viewName);
            if (statusCode != null) {
                applyStatusCodeIfPossible(request, response, statusCode);
            }
            return getModelAndView(viewName, ex, request);
        }
        return null;

    }
}
