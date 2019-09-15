package com.xuecheng.framework.exception;


import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常捕获类
 * 定义异常的作用是当程序出现异常，返回的信息用户能看懂
 */
@ControllerAdvice       //控制器增强
public class ExceptionCatch {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionCatch.class);//出现异常，记录日志

    //定义系统异常的自定义信息返回，定义Map,配置异常信息的对应的错误代码
    //ImmutableBiMap,google中的工具包中的类型，数据放进去只可读，不可更改，线程安全
    private static ImmutableMap<Class<?extends Throwable>,ResultCode> EXCEPTIONS;
    //定义map的builder对象，构建ImmutableMap
    protected static ImmutableMap.Builder<Class<?extends Throwable>,ResultCode> builder=ImmutableMap.builder();




    //捕获 CustomException异常
    @ExceptionHandler(CustomException.class)    //控制器增强的功能是使用这个注解捕获所有指定的异常
    @ResponseBody           //将异常返回信息转成json，返回给前端，
    public ResponseResult customException(CustomException e) {

        LOGGER.error("catch exception : {}\r\nexception: ", e.getMessage(), e);//打印日志
        ResultCode resultCode = e.getResultCode();
        ResponseResult responseResult = new ResponseResult(resultCode);

        return responseResult;
    }


    //捕获 CustomException异常
    @ExceptionHandler(Exception.class)    //控制器增强的功能是使用这个注解捕获所有指定的异常
    @ResponseBody           //将异常返回信息转成json，返回给前端，
    public ResponseResult exception(Exception e) {

        LOGGER.error("catch exception : {}\r\nexception: ", e.getMessage(), e);
        if(EXCEPTIONS==null){
            EXCEPTIONS=builder.build();//EXCEPTIONS  Map构建成功
        }
        //从Map当中找异常类型对应的错误代码，如果找到将错误代码响应给用户，找不到，返回9999
        ResultCode resultCode = EXCEPTIONS.get(e.getClass());
        if(resultCode!=null){
            return new ResponseResult(resultCode);
        }else {
            //对于所有系统异常返回9999
            return new ResponseResult(CommonCode.SERVER_ERROR);
        }

        }

        //静态代码块，在类加载时就创建
    static {
        //向ImmutableMap中添加系统异常类对应的错误代码

        builder.put(HttpRequestMethodNotSupportedException.class,CommonCode.INVALID_PARAM);



    }
}