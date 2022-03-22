package com.zhang.realm;

import com.zhang.bean.*;
import com.zhang.service.UserService;
import com.zhang.util.JwtUtil;
import com.zhang.util.StringUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import javax.annotation.Resource;

/**
 * 自定义安全数据Realm
 */
public class AuthRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    /**
     * 重写，绕过身份令牌异常导致的shiro报错
     * @param authenticationToken
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken authenticationToken) {
        return authenticationToken instanceof AuthToken;
    }

    /**
     * 执行授权逻辑
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    /**
     * 执行认证逻辑
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获得token
        String token = (String) authenticationToken.getCredentials();
        //获得token中的用户信息
        String username = JwtUtil.getUsername(token);
        //判空
        if (StringUtil.isBlank(username)) {
            throw new AuthenticationException(AuthConstant.TOKEN_BLANK);
        }
        //查询用户是否存在
        User user = userService.selectUserByUsername(username);
        if (user == null) {
            throw new AuthenticationException(AuthConstant.TOKEN_INVALID);
            //token过期
        } else if (!(JwtUtil.verifyToken(token, username, user.getPassword()))) {
            throw new AuthenticationException(AuthConstant.TOKEN_EXPIRE);
        }
        return new SimpleAuthenticationInfo(token, token, "auth_realm");
    }
}
