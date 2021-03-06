package com.ghca.easyview.im.framework.util.json;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.ser.CustomSerializerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

public class JSONUtil {

    private static Logger logger= Logger.getLogger(JSONUtil.class);

    private static ObjectMapper objectMapper;

    /**
     * 懒惰单例模式得到ObjectMapper实例
     * 此对象为Jackson的核心
     */
    private static ObjectMapper getMapper(){
        if (objectMapper== null){
            objectMapper= new ObjectMapper();
            //当找不到对应的序列化器时 忽略此字段
            objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);

            //派生到我的代码片

            // no single-String constructor/factory method
            objectMapper.configure(DeserializationConfig.Feature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true) ;

            //使Jackson JSON支持Unicode编码非ASCII字符
            CustomSerializerFactory serializerFactory= new CustomSerializerFactory();
            serializerFactory.addSpecificMapping(String.class, new StringUnicodeSerializer());
            objectMapper.setSerializerFactory(serializerFactory);
            //支持结束
        }
        return objectMapper;
    }

    /**
     * 创建JSON处理器的静态方法
     * @param content JSON字符串
     * @return
     */
    private static JsonParser getParser(String content){
        try{
            return getMapper().getJsonFactory().createJsonParser(content);
        }catch (IOException ioe){
            return null;
        }
    }

    /**
     * 创建JSON生成器的静态方法, 使用标准输出
     * @return
     */
    private static JsonGenerator getGenerator(StringWriter sw){
        try{
            return getMapper().getJsonFactory().createJsonGenerator(sw);
        }catch (IOException e) {
            return null;
        }
    }

    /**
     * JSON对象序列化
     */
    public static String toJSON(Object obj){
        StringWriter sw= new StringWriter();
        JsonGenerator jsonGen= getGenerator(sw);
        if (jsonGen== null){
            try {
                sw.close();
            } catch (IOException e) {
            }
            return null;
        }
        try {
            //由于在getGenerator方法中指定了OutputStream为sw
            //因此调用writeObject会将数据输出到sw
            jsonGen.writeObject(obj);
            //由于采用流式输出 在输出完毕后务必清空缓冲区并关闭输出流
            jsonGen.flush();
            jsonGen.close();
            return sw.toString();
        } catch (JsonGenerationException jge) {
            logger.error("JSON生成错误" + jge.getMessage());
        } catch (IOException ioe) {
            logger.error("JSON输入输出错误" + ioe.getMessage());
        }
        return null;
    }

    /**
     * JSON对象反序列化
     */
    public static <T> T fromJSON(String json, Class<T> clazz) {
        if (json == null || json.length() == 0) {
            return null;
        }
        String str = null;//编码后解码
        try {
            /**
             * mysql5.7的json字段类型，默认是iso8859-1，需要在这里进行转码
             */
            str = new String(json.getBytes("iso8859-1"), "UTF-8");
            logger.error("fromJSON json="+str);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            JsonParser jp= getParser(str);
            return jp.readValueAs(clazz);
        } catch (JsonParseException jpe){
            logger.error(String.format("反序列化失败, 错误原因:%s", jpe.getMessage()));
        } catch (JsonMappingException jme){
            logger.error(String.format("反序列化失败, 错误原因:%s", jme.getMessage()));
        } catch (IOException ioe){
            logger.error(String.format("反序列化失败, 错误原因:%s", ioe.getMessage()));
        }
        return null;
    }
}
