package com.dongyu.company.common.utils;

import com.dongyu.company.common.exception.BizException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * 密码校验器
 *
 * @author TYF
 * @date 2018/11/8
 * @since 1.0.0
 */
public class CredentialsMatcher extends SimpleCredentialsMatcher {

    //自定义密码校验
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken utoken = (UsernamePasswordToken) token;
        //获取用户输入的密码
        String inPassword = new String(utoken.getPassword());
        //获得数据库中的密码
        String dbPassword = (String) info.getCredentials();
        //进行密码的比对
        boolean verify = MD5Util.verify(inPassword, dbPassword);
        if (!verify) {
            throw new BizException("您输入的密码有误");
        }
        return verify;
    }


}
