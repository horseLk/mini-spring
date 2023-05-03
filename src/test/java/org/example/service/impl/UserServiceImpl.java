package org.example.service.impl;

import com.minis.beans.factory.annotation.Autowired;
import com.minis.jdbc.core.JdbcTemplate;
import org.example.dao.UserDao;
import org.example.domain.User;
import org.example.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User getUserInfo(int userId) {
        User user = userDao.getUserInfoById(userId);
        return user;
    }

    public User getUserInfo2(int userId) {
        User user = userDao.getUserInfoById2(userId);

        return user;
    }

    public List<User> getUserInfos(int minUserId) {
        List<User> users = userDao.getUsersWithMinId(minUserId);
        return users;
    }

    @Override
    public void UpdateUserInfo(User user) {
        int line = userDao.updateUser1(user.getId(), user.getName());
        System.out.println(line);
    }
}
