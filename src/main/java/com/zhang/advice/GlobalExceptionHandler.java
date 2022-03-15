package com.zhang.advice;


import com.zhang.bean.AuthConstant;
import com.zhang.bean.R;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


/**
 * 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public R myAuthenticationException(AuthenticationException e){
        System.out.println(e.getMessage());
        e.printStackTrace();
        return new R(400, AuthConstant.AUTHENTICATE_FAIL, null);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public R myUnauthorizedException(Exception e){
        System.out.println(e.getMessage());
        e.printStackTrace();
        return new R(400, AuthConstant.AUTHENTICATE_FAIL, null);
    }
    @ExceptionHandler(ServletRequestBindingException.class)
    public R myServletRequestBindingException(Exception e){
        System.out.println("请求参数不可为空！");
        e.printStackTrace();
        return new R(400, "请求参数不可为空！", null);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public R myDataIntegrityViolationException(Exception e){
        System.out.println("请求参数不正确！");
        e.printStackTrace();
        return new R(400, "请求参数不正确！", null);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R myHttpRequestMethodNotSupportedException(Exception e){
        System.out.println("请求不正确！");
        e.printStackTrace();
        return new R(405, "请求不正确" ,null);
    }
    @ExceptionHandler(DuplicateKeyException.class)
    public R mySQLIntegrityConstraintViolationException(Exception e){
        System.out.println("已存在相同数据，无法重复添加！");
        e.printStackTrace();
        return new R(400, "已存在相同数据，无法重复添加！" ,null);
    }

    @ExceptionHandler(Exception.class)
    public R myException(Exception e) {
        e.printStackTrace();
        return new R(500, "服务器出现异常！", null);
    }


}
