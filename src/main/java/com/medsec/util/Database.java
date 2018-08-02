package com.medsec.util;

import com.medsec.dao.PatientMapper;
import com.medsec.dao.UserMapper;
import com.medsec.entity.Patient;
import com.medsec.entity.User;
import org.apache.ibatis.session.SqlSession;

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

    public void updateToken(Token token) {
        UserMapper mapper=session.getMapper(UserMapper.class);
        mapper.updateToken(token);
        session.commit();
    }

    // Database manipulating methods here

}