package com.ghca.easyview.im.framework.sys.interceptor;

import com.ghca.easyview.im.framework.util.DateUtil;
import com.ghca.easyview.im.framework.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * 日志拦截器
 *
 * Created by Administrator on 2017/3/27.
 */
public class LogInterceptor  implements HandlerInterceptor {

    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    private static final ThreadLocal<Long> startTimeThreadLocal =
            new NamedThreadLocal<Long>("ThreadLocal StartTime");

    /**
     * {@inheritDoc}
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long beginTime = System.currentTimeMillis();//1、开始时间
        startTimeThreadLocal.set(beginTime);        //线程绑定变量（该数据只有当前请求的线程可见）
        if (logger.isDebugEnabled()) {

            logger.debug("开始计时: {}  URI: {}", new SimpleDateFormat("hh:mm:ss.SSS")
                    .format(beginTime), request.getRequestURI());
        }
        return true;


    }

    /**
     * {@inheritDoc}
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        if (modelAndView != null) {
            logger.info("ViewName: " + modelAndView.getViewName());
        }

    }

    /**
     * {@inheritDoc}
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        long beginTime = startTimeThreadLocal.get();//得到线程绑定的局部变量（开始时间）
        long endTime = System.currentTimeMillis();    //2、结束时间
        String projectPath = request.getContextPath();
        String requestPath = request.getRequestURI();
        if (requestPath.indexOf(projectPath) != -1) {
            requestPath = requestPath.replaceFirst(projectPath, "");
        }

        logger.error("用户IP:" + StringUtil.getRemoteAddr(request) + " 操作参数:" + getParams(request.getParameterMap()) + " 异常信息:" + (ex == null ? "无" : ex.getMessage()));
        // 打印JVM信息。
        if (logger.isDebugEnabled()) {

            //打印请求信息
            logger.debug("请求信息: Exception:{}  RequsetAddr:{}  RequestUserAgent:{}  RequestURI:{}  RequestParams:{}  RequestMethod:{}  ", ex == null ? "No Exception" : ex, StringUtil.getRemoteAddr(request), request.getHeader("user-agent"), request.getRequestURI(), request.getParameterMap(), request.getMethod());


            logger.debug("计时结束：{}  耗时：{}  URI: {}  最大内存: {}m  已分配内存: {}m  已分配内存中的剩余空间: {}m  最大可用内存: {}m",
                    new SimpleDateFormat("hh:mm:ss.SSS").format(endTime), DateUtil.formatDateTime(endTime - beginTime),
                    request.getRequestURI(), Runtime.getRuntime().maxMemory() / 1024 / 1024, Runtime.getRuntime().totalMemory() / 1024 / 1024, Runtime.getRuntime().freeMemory() / 1024 / 1024,
                    (Runtime.getRuntime().maxMemory() - Runtime.getRuntime().totalMemory() + Runtime.getRuntime().freeMemory()) / 1024 / 1024);
        }

    }


    public String getParams(Map paramMap) {
        if (paramMap == null) {
            return null;
        }
        StringBuilder params = new StringBuilder();
        for (Map.Entry<String, String[]> param : ((Map<String, String[]>) paramMap).entrySet()) {
            params.append(("".equals(params.toString()) ? "" : "&") + param.getKey() + "=");
            String paramValue = (param.getValue() != null && param.getValue().length > 0 ? param.getValue()[0] : "");
            params.append(StringUtil.abbr(StringUtil.endsWithIgnoreCase(param.getKey(), "password") ? "" : paramValue, 100));
        }
        return params.toString();
    }
}
