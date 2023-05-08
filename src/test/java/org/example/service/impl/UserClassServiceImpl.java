package org.example.service.impl;

import com.minis.beans.factory.annotation.Autowired;
import org.example.dao.UserClassDao;
import org.example.dao.UserDao;
import org.example.domain.User;
import org.example.domain.UserClass;
import org.example.service.UserClassService;

public class UserClassServiceImpl implements UserClassService {
    @Autowired
    private UserClassDao userClassDao;

    @Autowired
    private UserDao userDao;

    @Override
    public int doUpdateClass(UserClass userClass) {
        return userClassDao.updateClass(userClass);
    }

    @Override
    public void doUpdateWithTransaction(User user, UserClass userClass) {
        System.out.println("update user class");
        userClassDao.updateClass(userClass);
        int t = 1/0;
        System.out.println("update user");
        userDao.updateUser2(user.getId(), user.getName());
    }
}
