package com.ghca.easyview.im.framework.annotation;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.ghca.easyview.im.framework.util.ReflectionUtil;
import com.ghca.easyview.im.framework.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by Administrator on 2017/3/29.
 */
public class FiledDicJsonAnnotation extends JsonSerializer {

    private static Logger logger = LoggerFactory.getLogger(FiledDicJsonAnnotation.class);

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {

        try {

            String fileName = StringUtil.capitalize(gen.getOutputContext().getCurrentName());
            //获取field
            String getterObjectName = "get" + fileName;


            Object result = ReflectionUtil.invokeMethod(gen.getOutputContext().getCurrentValue(), getterObjectName, new Class[]{},
                    new Object[]{});
            // gen.writeString(String.valueOf(result));

            //serializers.setAttribute(fileName + "Label", String.valueOf(result)); //
            gen.writeString(String.valueOf(result));

        } catch (Exception e) {


            logger.error("注解处理异常：" + e.getMessage());
        }
        gen.writeString(String.valueOf(value));


    }
}
