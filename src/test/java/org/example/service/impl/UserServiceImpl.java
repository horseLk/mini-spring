package org.example.service.impl;

import com.minis.beans.factory.annotation.Autowired;
import com.minis.jdbc.core.JdbcTemplate;
import org.example.dao.UserDao;
import org.example.domain.User;
import org.example.service.UserService;

public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User getUserInfo(int userId) {
        String sql = "select * from t_user where id=" + userId;
        User user = (User) userDao.getUserInfoById(userId);
        return user;
    }
}
