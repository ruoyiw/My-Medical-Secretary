package com.medsec.dao;

import com.medsec.entity.NotificationToken;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

public interface NotificationTokenMapper {

    ArrayList<String> getTokensByUserId(
            @Param("uid") String uid
    );

}
