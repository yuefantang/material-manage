package com.dongyu.company.user.service;

import com.dongyu.company.common.dto.PageDTO;
import com.dongyu.company.user.domain.User;
import com.dongyu.company.user.dto.AddUserDTO;
import com.dongyu.company.user.dto.EditPasswordDTO;
import com.dongyu.company.user.dto.RoleListDTO;
import com.dongyu.company.user.dto.UserDTO;
import com.dongyu.company.user.dto.UserListDTO;
import com.dongyu.company.user.dto.UserQueryDTO;

import java.util.List;

/**
 * 用户业务处理service
 *
 * @author TYF
 * @date 2018/11/9
 * @since 1.0.0
 */
public interface UserService {

    //根据用户名查询用户
    User get(String userName);

    //用户登陆
    void login(UserDTO userDTO);

    //新增用户
    boolean add(AddUserDTO addUserDTO);

    //逻辑删除用户
    boolean deleted(Long userId);

    //获取用户列表
    PageDTO<UserListDTO> getlist(UserQueryDTO userQueryDTO);

    //获取角色下拉列表
    List<RoleListDTO> getRoleList();

    //修改密码
    void editPassword(EditPasswordDTO editPasswordDTO);
}
