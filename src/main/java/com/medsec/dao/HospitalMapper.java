package com.medsec.dao;

import com.medsec.entity.Hospital;

import java.util.List;

public interface HospitalMapper {
    List<Hospital> selectAllHospitals();
}
