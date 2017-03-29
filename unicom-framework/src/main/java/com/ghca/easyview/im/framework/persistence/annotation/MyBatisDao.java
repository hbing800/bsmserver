package com.ghca.easyview.im.framework.persistence.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;


/**
 * Description 标识MyBatis的DAO,方便{@link org.mybatis.spring.mapper.MapperScannerConfigurer}的扫描。
 *
 * @author <a href="mailto:pan.luo@ghca.com">Luopan</a>
 * @version 1.0
 * @Company: 上海光华冠群软件有限公司
 * @PROJECT_NAME easyview-vdc-master
 * @Package com.ghca.easyview.cloud.core.persistence.annotation
 * @date 2016/1/14 15:25
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Component
public @interface MyBatisDao {
}
