package com.carrot97.vhr.mapper;

import com.carrot97.vhr.model.Hr;
import com.carrot97.vhr.model.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HrMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Hr record);

    int insertSelective(Hr record);

    Hr selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Hr record);

    int updateByPrimaryKey(Hr record);

    Hr selectByUsername(String username);

    List<Role> selectRolesByHrId(Integer id);

    List<Hr> getHrs(Integer hrid, String keyword);

    List<Hr> getHrsExceptMe(Integer id);
}