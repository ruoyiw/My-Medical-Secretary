package com.medsec.dao;

import com.medsec.entity.User;
import com.medsec.util.Token;

public interface UserMapper {
    User selectById(String id);
    User selectByEmail(String email);
    void updateToken(Token token);

}
