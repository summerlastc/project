package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;


//封装了   throw new CustomException(resultCode),自定义异常抛出类
public class ExceptionCast {

    //使用此静态方法抛出自定义异常
    public static void cast(ResultCode resultCode){
        throw new CustomException(resultCode);
    }
}
