package com.medsec.util;

import com.medsec.dao.UserMapper;
import com.medsec.entity.User;
import org.apache.ibatis.session.SqlSession;

import java.time.Instant;

/**
 * This class encapsulates all database queries.
 */
public class Database {
    SqlSession session;
    public Database() {
        this.session = ConfigListener.sqlSessionFactory.openSession();
    }

    public User getUserById(String id) {
        UserMapper mapper=session.getMapper(UserMapper.class);
        return mapper.selectById(id);
    }

    public User getUserByEmail(String email) {
        UserMapper mapper=session.getMapper(UserMapper.class);
        return mapper.selectByEmail(email);
    }

    public void updateTokenValidFromDate(String uid, Instant token_valid_from) {
        UserMapper mapper=session.getMapper(UserMapper.class);
        User user = new User().id(uid).token_valid_from(token_valid_from);
        mapper.updateToken(user);
        session.commit();
    }

    // Database manipulating methods here

}