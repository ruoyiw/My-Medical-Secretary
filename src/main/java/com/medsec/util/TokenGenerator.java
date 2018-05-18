package com.medsec.util;

import java.sql.Timestamp;
import java.util.UUID;

public class TokenGenerator {
	public static String generateToken(String password){
		return UUID.fromString(UUID.nameUUIDFromBytes(password.getBytes()).toString()).toString();
	}
	public static Timestamp setTokenExpireTime(){
		long nowMillis =System.currentTimeMillis();
		Timestamp timestamp=new Timestamp(nowMillis+30*60*1000);
		return timestamp;
	}
}
