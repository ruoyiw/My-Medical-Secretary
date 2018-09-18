package com.medsec.dao;

import com.medsec.entity.Doctor;

import java.util.List;

public interface DoctorMapper {
    List<Doctor> selectAllDoctors();
}
