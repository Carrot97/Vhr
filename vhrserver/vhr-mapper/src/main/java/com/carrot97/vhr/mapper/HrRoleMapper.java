package com.carrot97.vhr.mapper;

import com.carrot97.vhr.model.HrRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HrRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HrRole record);

    int insertSelective(HrRole record);

    HrRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HrRole record);

    int updateByPrimaryKey(HrRole record);

    boolean deleteByHrid(Integer hrid);

    boolean batchInsertByHrid(@Param("hrid") Integer hrid, @Param("rids") Integer[] rids);
}