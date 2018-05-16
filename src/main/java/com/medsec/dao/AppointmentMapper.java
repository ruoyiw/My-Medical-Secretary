package com.medsec.dao;

import com.medsec.entity.Appointment;
import org.apache.ibatis.annotations.Param;

public interface AppointmentMapper {

    /*
        You can pass multiple parameters to a mapper method.
        If you do, they will be named by the literal "param" followed
        by their position in the parameter list by default,
        for example: #{param1}, #{param2} etc.
        If you wish to change the name of the parameters (multiple only),
        then you can use the @Param("paramName") annotation on the parameter.
     */
    Appointment getAppointmentById(@Param("id") String id,
                                   @Param("pid") String pid);
}
