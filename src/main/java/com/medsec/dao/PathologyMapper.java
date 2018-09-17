package com.medsec.dao;

import com.medsec.entity.Pathology;

import java.util.List;

public interface PathologyMapper {
    List<Pathology> selectAllPathologies();
}
