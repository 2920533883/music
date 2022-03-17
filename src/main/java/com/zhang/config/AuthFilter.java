package com.zhang.config;

import com.zhang.bean.AuthConstant;
import com.zhang.bean.AuthToken;
import com.zhang.util.JwtUtil;
import com.zhang.util.StringUtil;
import lombok.SneakyThrows;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 实现自定义的认证拦截器,接收传过来的token,实现前后端分离的权限认证
 */
public class AuthFilter extends AuthenticatingFilter {

    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        if (((HttpServletRequest) request).getMethod().equals("OPTIONS")) {
            return true;
        }
        return super.onPreHandle(request, response, mappedValue);
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        return null;
    }

    @SneakyThrows
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        String token = JwtUtil.getRequestToken((HttpServletRequest) request);
        if (!StringUtil.isBlank(token)) {
            try {
                return this.executeLogin(request, response);
            } catch (Exception e) {
                // 应用异常
                e.printStackTrace();
                return false;
            }
        } else {
            // cookie中未检查到token或token为空
            return false;
        }
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.sendError(403, AuthConstant.AUTHENTICATE_FAIL);
        return false;
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        String token = JwtUtil.getRequestToken((HttpServletRequest) request);
        AuthToken jwtToken = new AuthToken(token);
        // 提交给AuthRealm进行登录认证
        getSubject(request, response).login(jwtToken);
        return true;
    }
}
