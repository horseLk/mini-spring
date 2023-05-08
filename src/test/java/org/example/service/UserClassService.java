package org.example.service;

import org.example.domain.User;
import org.example.domain.UserClass;

public interface UserClassService {
    int doUpdateClass(UserClass userClass);

    void doUpdateWithTransaction(User user, UserClass userClass);
}
