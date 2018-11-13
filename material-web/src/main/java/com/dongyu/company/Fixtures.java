package com.dongyu.company;

import com.dongyu.company.common.constants.Constants;
import com.dongyu.company.common.utils.MD5Util;
import com.dongyu.company.user.dao.UserDao;
import com.dongyu.company.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Production fixtures
 */
@Component
@Transactional
//@Profile("dev")
public class Fixtures implements Ordered, ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserDao userDao;
//
//    @Autowired
//    private RoleRepository roleRepository;

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
           // this.configure();
        }
    }

    private void configure() {
        String password = MD5Util.generate("123456");
        User user = new User();
        user.setUserName("admin");
        user.setPassword(password);
        user.setDeleted(Constants.USER_NOT_DELETED);
        userDao.save(user);
//        final Role role = new Role();
//        role.setName("admin");
//        role.setDescript("manger");
//        role.setUserId(String.valueOf(save.getId()));
//
//        this.roleRepository.save(role);

    }
}
