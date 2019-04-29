package com.dongyu.company.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dongyu.company.common.constants.Constants;
import com.dongyu.company.common.dto.PageDTO;
import com.dongyu.company.common.exception.BizException;
import com.dongyu.company.common.utils.MD5Util;
import com.dongyu.company.user.dao.RoleDao;
import com.dongyu.company.user.dao.UserDao;
import com.dongyu.company.user.dao.UserRoleDao;
import com.dongyu.company.user.dao.UserSpecs;
import com.dongyu.company.user.domain.Role;
import com.dongyu.company.user.domain.User;
import com.dongyu.company.user.domain.UserRole;
import com.dongyu.company.user.dto.AddUserDTO;
import com.dongyu.company.user.dto.EditPasswordDTO;
import com.dongyu.company.user.dto.RoleListDTO;
import com.dongyu.company.user.dto.UserDTO;
import com.dongyu.company.user.dto.UserListDTO;
import com.dongyu.company.user.dto.UserQueryDTO;
import com.dongyu.company.user.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户实现service
 *
 * @author TYF
 * @date 2018/11/9
 * @since 1.0.0
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public User get(String userName) {
        return null;
    }

    @Override
    public void login(UserDTO userDTO) {
        log.info("UserServiceImpl login method start Parm:" + JSONObject.toJSONString(userDTO));
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userDTO.getUserName(), userDTO.getPassword());
        token.setRememberMe(true);
        subject.login(token);
    }

    @Override
    @Transactional
    public boolean add(AddUserDTO addUserDTO) {
        log.info("UserServiceImpl add method start Parm:" + JSONObject.toJSONString(addUserDTO));
        Role role = roleDao.findOneById(addUserDTO.getRoleId());
        if (role == null) {
            throw new BizException("用户角色不存在，请选择正确角色");
        }
        User oldUser = userDao.findByUserName(addUserDTO.getUserName());
        if (oldUser != null) {
            throw new BizException("该用户名已存在，请更换用户名");
        }
        //用户存储
        String password = MD5Util.generate(addUserDTO.getPassword());
        User user = new User();
        user.setUserName(addUserDTO.getUserName());
        user.setPassword(password);
        user.setDeleted(Constants.USER_NOT_DELETED);
        user = userDao.save(user);

        //赋予角色
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId(role.getId());
        userRoleDao.save(userRole);
        return true;
    }

    @Override
    public boolean deleted(Long userId) {
        log.info("UserServiceImpl deleted method start Parm:" + userId);
        User user = userDao.findOneById(userId);
        if (user == null) {
            throw new BizException("该用户不存在");
        }
        userDao.delete(userId);
        UserRole userRole = userRoleDao.findByUserId(userId);
        userRoleDao.delete(userRole.getId());
        return true;
    }

    @Override
    public PageDTO<UserListDTO> getlist(UserQueryDTO dto) {
        log.info("UserServiceImpl getlist method start Parm:" + JSONObject.toJSONString(dto));
        Page<User> userPage = userDao.findAll(UserSpecs.userListQuerySpec(dto), new PageRequest(dto.getPageNo() - 1, dto.getPageSize(), Sort.Direction.ASC, Constants.CREATE_TIME));
        PageDTO<UserListDTO> pageDTO = PageDTO.of(userPage, item -> {
            UserListDTO userListDTO = new UserListDTO();
            BeanUtils.copyProperties(item, userListDTO);
            //获取用户角色信息
            UserRole byUser = userRoleDao.findByUserId(item.getId());
            if (byUser != null) {
                Role role = roleDao.findOneById(byUser.getRoleId());
                userListDTO.setDescript(role.getDescript());
                userListDTO.setRoleName(role.getRoleName());
            }
            return userListDTO;
        });
        return pageDTO;
    }

    @Override
    public List<RoleListDTO> getRoleList() {
        log.info("UserServiceImpl getRoleList method start:");
        List<Role> all = roleDao.findAll();
        if (CollectionUtils.isEmpty(all)) {
            return null;
        }
        List<RoleListDTO> listDTOS = all.stream().map(role -> {
            RoleListDTO roleListDTO = new RoleListDTO();
            BeanUtils.copyProperties(role, roleListDTO);
            return roleListDTO;
        }).collect(Collectors.toList());
        return listDTOS;
    }

    @Override
    public void editPassword(EditPasswordDTO dto) {
        log.info("UserServiceImpl editPassword method start Parm:" + JSONObject.toJSONString(dto));
        User oldUser = userDao.findOneById(dto.getId());
        if (oldUser == null) {
            throw new BizException("该用户不存在");
        }
        //比较输入两次密码是否一致
        if (!dto.getFirstPassword().equals(dto.getPassword())) {
            throw new BizException("两次密码输入不一致，请重新输入");
        }
        //修改密码
        String newPassword = MD5Util.generate(dto.getPassword());
        oldUser.setPassword(newPassword);
        userDao.save(oldUser);
    }
}
