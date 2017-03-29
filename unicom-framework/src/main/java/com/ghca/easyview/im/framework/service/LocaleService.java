package com.ghca.easyview.im.framework.service;

import com.ghca.easyview.im.framework.util.CookieUtil;
import com.ghca.easyview.im.framework.util.StringUtil;
import com.ghca.easyview.im.framework.web.Servlets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import java.util.Locale;

/**
 * Description  根据缓存中设置语言获取国际化资源信息
 *
 * Created by bingbing on 2017/3/29.
 */
@Service
public class LocaleService extends BaseService {

    @Autowired
    CookieLocaleResolver resolver;

    @Autowired
    private MessageSource messageSource;

    /**
     * 获取用户设置的语言版本
     *
     * @return
     */
    public Locale getLocale() {
        Locale locale = null;
        //获取语言缓存值
        String cookieLanguage = CookieUtil.getCookie(Servlets.getRequest(), "language");
        //如果不为空
        if (StringUtil.isNoneBlank()) {

            try {
                locale = new Locale(cookieLanguage);
            } catch (Exception ex) {
                logger.error("获取用户语言异常:{}", ex);
            }

        }
        if (null == locale) {
            locale = resolver.resolveLocale(Servlets.getRequest());
        }
        return locale;
    }


}
