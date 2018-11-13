package com.dongyu.company.web.account.controller;

import com.dongyu.company.common.constant.Constants;
import com.dongyu.company.common.dto.PageDTO;
import com.dongyu.company.common.vo.ResponseVo;
import com.dongyu.company.user.dto.AddUserDTO;
import com.dongyu.company.user.dto.EditPasswordDTO;
import com.dongyu.company.user.dto.RoleListDTO;
import com.dongyu.company.user.dto.UserDTO;
import com.dongyu.company.user.dto.UserListDTO;
import com.dongyu.company.user.dto.UserQueryDTO;
import com.dongyu.company.user.service.UserService;
import com.dongyu.company.web.account.form.AddUserForm;
import com.dongyu.company.web.account.form.EditPasswordForm;
import com.dongyu.company.web.account.form.UserForm;
import com.dongyu.company.web.account.form.UserQueryForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 用户controller
 *
 * @author TYF
 * @date 2018/11/8
 * @since 1.0.0
 */
@RestController
@Api(tags = "UserController", description = "用户登陆管理相关")
@RequestMapping(value = Constants.WEB_PREFIX + "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("用户登陆")
    @PostMapping(value = "/login")
    public ResponseVo login(@Valid @ModelAttribute UserForm userForm) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userForm, userDTO);
        userService.login(userDTO);
        return ResponseVo.successResponse();
    }


    @RequiresRoles(value = {"admin"}, logical = Logical.AND)
    @ApiOperation("新增用户")
    @PostMapping(value = "/add")
    public ResponseVo add(@Valid @RequestBody AddUserForm addUserForm) {
        AddUserDTO addUserDTO = new AddUserDTO();
        BeanUtils.copyProperties(addUserForm, addUserDTO);
        userService.add(addUserDTO);
        return ResponseVo.successResponse();
    }

    @RequiresRoles(value = {"admin"})
    @ApiOperation("删除用户")
    @DeleteMapping(value = "/deleted")
    public ResponseVo deleted(@ApiParam(name = "userId", value = "用户id") @RequestParam("userId") Long userId) {
        userService.deleted(userId);
        return ResponseVo.successResponse();
    }


    @RequiresRoles(value = {"admin"})
    @GetMapping
    @ApiOperation("获取用户列表")
    public ResponseVo<PageDTO<UserListDTO>> get(@ModelAttribute UserQueryForm userQueryForm) {
        UserQueryDTO userQueryDTO = new UserQueryDTO();
        BeanUtils.copyProperties(userQueryForm, userQueryDTO);
        PageDTO<UserListDTO> pageDTO = userService.getlist(userQueryDTO);
        return ResponseVo.successResponse(pageDTO);
    }

    @GetMapping(value = "/logout")
    @ApiOperation("当前用户退出")
    public ResponseVo logout() {
        try {
            SecurityUtils.getSubject().logout();
            SecurityUtils.getSubject().getSession().removeAttribute("user");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return ResponseVo.successResponse();
    }

    @RequiresRoles(value = {"admin"})
    @GetMapping(value = "/editPassword")
    @ApiOperation("修改密码")
    public ResponseVo get(@ModelAttribute EditPasswordForm editPasswordForm) {
        EditPasswordDTO editPasswordDTO = new EditPasswordDTO();
        BeanUtils.copyProperties(editPasswordForm, editPasswordDTO);
        userService.editPassword(editPasswordDTO);
        return ResponseVo.successResponse();
    }

    @RequiresRoles(value = {"admin"})
    @GetMapping(value = "/roleList")
    @ApiOperation("获取角色下拉列表")
    public ResponseVo<List<RoleListDTO>> getRole() {
        List<RoleListDTO> roleList = userService.getRoleList();
        return ResponseVo.successResponse(roleList);
    }


}
