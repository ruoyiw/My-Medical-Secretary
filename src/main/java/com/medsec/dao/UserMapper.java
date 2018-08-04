package com.medsec.dao;

import com.medsec.entity.User;

public interface UserMapper {
    User selectById(String id);
    User selectByEmail(String email);
    void updateToken(User user);

}
