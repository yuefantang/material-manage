package com.dongyu.company.common.handler;

import com.dongyu.company.common.constants.Constants;
import com.dongyu.company.common.exception.BizException;
import com.dongyu.company.user.dao.RoleDao;
import com.dongyu.company.user.dao.UserDao;
import com.dongyu.company.user.dao.UserRoleDao;
import com.dongyu.company.user.domain.Role;
import com.dongyu.company.user.domain.User;
import com.dongyu.company.user.domain.UserRole;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Shiro认证授权处理
 *
 * @author TYF
 * @date 2018/11/8
 * @since 1.0.0
 */
public class ShiroRelam extends AuthorizingRealm {
    private static final Logger log = LoggerFactory.getLogger(ShiroRelam.class);

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private RoleDao roleDao;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("doGetAuthorizationInfo start：" + principals.toString());
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        UserRole userRole = userRoleDao.findByUserId(user.getId());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if (userRole == null) {
            return info;
        }
        Role role = roleDao.findOneById(userRole.getRoleId());
        //将角色放入shiro中
        info.addRole(role.getRoleName());
        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken utoken = (UsernamePasswordToken) token;
        String username = utoken.getUsername();
        User user = userDao.findByUserName(username);
        if (user == null) {
            throw new BizException("该用户不存在");
        }
        //设置用户session
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("user", user);
        return new SimpleAuthenticationInfo(user, user.getPassword(), this.getClass().getName());
    }
}
