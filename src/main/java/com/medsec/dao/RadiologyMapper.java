package com.medsec.dao;

import com.medsec.entity.Radiology;

import java.util.List;

public interface RadiologyMapper {
    List<Radiology> selectAllRadiologies();
}
