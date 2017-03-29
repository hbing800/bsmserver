//package com.ghca.easyview.im.framework.client;
//
//import com.sun.jersey.api.client.Client;
//import com.sun.jersey.api.client.ClientResponse;
//import com.sun.jersey.api.client.WebResource;
//import com.sun.jersey.api.client.config.ClientConfig;
//import com.sun.jersey.api.client.config.DefaultClientConfig;
//import com.sun.jersey.core.util.MultivaluedMapImpl;
//import net.sf.json.JSONObject;
//import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.MultivaluedMap;
//import java.util.Map;
//
///**
// * 通过jersey-client客户端调用Jersey的Web服务模拟CURD
// *
// * Created by Administrator on 2017/3/29.
// */
//public class JerseyClient {
//
//
//    private static Logger logger = LoggerFactory.getLogger(JerseyClient.class);
//
//
//    /**
//     * POST方式请求
//     *
//     * @param url
//     * @param headers
//     * @param queryParamMap
//     * @param jsonBody
//     * @param mediaType
//     * @return
//     * @throws Exception
//     */
//    public static String post(String url, Map<String, String> headers, Map<String, String> queryParamMap, String jsonBody, MediaType mediaType) throws FMException {
//
//
//        logger.debug("POST 方法");
//        logger.debug("POST URL:{}", url);
//
//        ClientConfig clientConfig = new DefaultClientConfig();
//        clientConfig.getClasses().add(JacksonJsonProvider.class);
//        Client client = Client.create(clientConfig);
//        WebResource webResource = client.resource(url);
//
//
//        //添加query参数
//        MultivaluedMap<String, String> queryParam = new MultivaluedMapImpl();
//        webResource = addQueryParams(queryParamMap, webResource, queryParam);
//
//        WebResource.Builder builder = webResource.getRequestBuilder();
//
//        addMediaType(mediaType, builder);
//
//
//        //添加header参数
//        addHeader(headers, builder);
//
//        logger.debug("HEADER 发送heaer:--> {}", webResource.head().getHeaders());
//
//
//        ClientResponse response;
//        //添加body参数
//        if (StringUtils.isNotBlank(jsonBody)) {
//            response = builder.post(ClientResponse.class, jsonBody);
//
//            logger.debug("BODY 发送body:--> {}", jsonBody);
//
//
//        } else {
//            response = builder.post(ClientResponse.class, null);
//
//        }
//
//
//        return parseResponse(response);
//
//    }
//
//
//    /**
//     * put 方式请求
//     *
//     * @param url
//     * @param headers
//     * @param queryParamMap
//     * @param jsonBody
//     * @param mediaType
//     * @return
//     * @throws Exception
//     */
//    public static String put(String url, Map<String, String> headers, Map<String, String> queryParamMap, String jsonBody, MediaType mediaType) throws FMException {
//
//
//        logger.debug("PUT 方法");
//        logger.debug("PUT URL:{}", url);
//
//        ClientConfig clientConfig = new DefaultClientConfig();
//        clientConfig.getClasses().add(JacksonJsonProvider.class);
//        Client client = Client.create(clientConfig);
//        WebResource webResource = client.resource(url);
//
//
//        //添加query参数
//        MultivaluedMap<String, String> queryParam = new MultivaluedMapImpl();
//        webResource = addQueryParams(queryParamMap, webResource, queryParam);
//
//        WebResource.Builder builder = webResource.getRequestBuilder();
//
//        addMediaType(mediaType, builder);
//
//
//        //添加header参数
//        addHeader(headers, builder);
//
//        logger.debug("HEADER 发送heaer:--> {}", webResource.head().getHeaders());
//
//
//        ClientResponse response;
//        //添加body参数
//        if (StringUtils.isNotBlank(jsonBody)) {
//            logger.debug("BODY 发送body:--> {}", jsonBody);
//
//            response = builder.put(ClientResponse.class, jsonBody);
//        } else {
//            response = builder.put(ClientResponse.class, null);
//        }
//
//
//        return parseResponse(response);
//
//    }
//
//
//    /**
//     * GET 方式请求
//     *
//     * @param url
//     * @param headers
//     * @param queryParamMap
//     * @param mediaType
//     * @return
//     * @throws Exception
//     */
//    public static String get(String url, Map<String, String> headers, Map<String, String> queryParamMap, MediaType mediaType) throws FMException {
//
//
//        logger.debug("GET 方法");
//        logger.debug("GET URL:{}", url);
//
//        ClientConfig clientConfig = new DefaultClientConfig();
//        clientConfig.getClasses().add(JacksonJsonProvider.class);
//        Client client = Client.create(clientConfig);
//        WebResource webResource = client.resource(url);
//
//
//        //添加query参数
//        MultivaluedMap<String, String> queryParam = new MultivaluedMapImpl();
//        webResource = addQueryParams(queryParamMap, webResource, queryParam);
//
//        WebResource.Builder builder = webResource.getRequestBuilder();
//
//        addMediaType(mediaType, builder);
//
//
//        //添加header参数
//        addHeader(headers, builder);
//
//        logger.debug("HEADER 发送heaer:--> {}", webResource.head().getHeaders());
//
//
//        ClientResponse response = builder.get(ClientResponse.class);
//
//
//        return parseResponse(response);
//
//    }
//
//
//    /**
//     * DELETE方式请求
//     *
//     * @param url
//     * @param headers
//     * @param queryParamMap
//     * @param mediaType
//     * @return
//     * @throws Exception
//     */
//    public static String delete(String url, Map<String, String> headers, Map<String, String> queryParamMap, MediaType mediaType) throws FMException {
//
//        logger.debug("DELETE 方法");
//        logger.debug("DELETE URL:{}", url);
//
//        ClientConfig clientConfig = new DefaultClientConfig();
//        clientConfig.getClasses().add(JacksonJsonProvider.class);
//        Client client = Client.create(clientConfig);
//        WebResource webResource = client.resource(url);
//
//
//        //添加query参数
//        MultivaluedMap<String, String> queryParam = new MultivaluedMapImpl();
//        webResource = addQueryParams(queryParamMap, webResource, queryParam);
//
//        WebResource.Builder builder = webResource.getRequestBuilder();
//
//        addMediaType(mediaType, builder);
//
//
//        //添加header参数
//        addHeader(headers, builder);
//
//        logger.debug("HEADER 发送heaer:--> {}", webResource.head().getHeaders());
//
//
//        ClientResponse response = builder.delete(ClientResponse.class);
//
//
//        return parseResponse(response);
//
//    }
//
//
//    /**
//     * 解析请求返回
//     *
//     * @param response
//     * @return
//     * @throws Exception
//     */
//    private static String parseResponse(ClientResponse response) throws FMException {
//        int status = response.getStatus();
//
//        String resultMessage = response.getEntity(String.class);
//
//        logger.debug(" HTTP STATUS: " + status);
//        logger.debug(" resultMessage: " + resultMessage);
//
//
//        switch (status) {
//            case 200:
//                logger.debug(" HTTP MESSAGE: " + resultMessage);
//
//                return resultMessage;
//            case 404:
//                logger.error("Not Found");
//                throw new FMException("Not Found");
//            case 405:
//                logger.error("不允许的操作（指定了错误的HTTP方法或API）");
//                throw new FMException("不允许的操作（指定了错误的HTTP方法或API）");
//
//            case 500: {
//                String message = "";
//                String localemessage = "";
//                if (null != resultMessage) {
//                    JSONObject obj = JSONObject.fromObject(resultMessage);
//                    message = obj.getString("message");
//                    localemessage = obj.getString("localemessage");
//                }
//
//                logger.error(" resultMessage: " + localemessage);
//                throw new FMException(message);
//            }
//
//
//            default:
//                throw new FMException("其他未知错误");
//        }
//    }
//
//    /**
//     * 添加MediaType
//     *
//     * @param mediaType
//     * @param builder
//     */
//    private static void addMediaType(MediaType mediaType, WebResource.Builder builder) {
//        if (null != mediaType) {
//            builder.type(mediaType);
//            logger.debug("MediaType {}", mediaType);
//
//        } else {
//            builder.type(MediaType.APPLICATION_JSON_TYPE);
//            logger.debug("MediaType {}", MediaType.APPLICATION_JSON_TYPE.getSubtype());
//
//        }
//    }
//
//    /**
//     * 添加Header
//     *
//     * @param headers
//     * @param builder
//     */
//    private static void addHeader(Map<String, String> headers, WebResource.Builder builder) {
//        if (null != headers) {
//            for (Map.Entry<String, String> entry : headers.entrySet()) {
//                String key = entry.getKey();
//                String value = entry.getValue();
//                builder.header(key, value);
//                logger.debug("HEADER 添加header参数:{} --> {}", key, value);
//
//            }
//        }
//    }
//
//    /**
//     * 添加queryParam
//     *
//     * @param queryParamMap
//     * @param webResource
//     * @param queryParam
//     * @return
//     */
//    private static WebResource addQueryParams(Map<String, String> queryParamMap, WebResource webResource, MultivaluedMap<String, String> queryParam) {
//        if (null != queryParamMap) {
//
//            for (Map.Entry<String, String> entry : queryParamMap.entrySet()) {
//                String key = entry.getKey();
//                String value = entry.getValue();
//                queryParam.add(key, value);
//                logger.debug("添加queryParam参数:{} --> {}", key, value);
//            }
//            webResource = webResource.queryParams(queryParam);
//
//            logger.debug("URL:{}", webResource.getURI());
//
//        }
//        return webResource;
//    }
//
//
//}
