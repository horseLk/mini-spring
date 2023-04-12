package org.example.service;

import org.example.domain.User;

import java.util.List;

public interface UserService {
    User getUserInfo(int userId);

    User getUserInfo2(int userId);

    List<User> getUserInfos(int minUserId);
}
