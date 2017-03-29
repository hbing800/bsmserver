package com.ghca.easyview.im.framework.annotation;

import java.lang.annotation.*;

/**
 * Created by Administrator on 2017/3/29.
 */
@Documented //指示将此注解包含在 javadoc 中
@Inherited //指示允许子类继承父类中的注解
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface FiledDicAnnotation {


    //注解值以此种方式："1:hehe,2:haha"
    String filedDic() default "";


}
