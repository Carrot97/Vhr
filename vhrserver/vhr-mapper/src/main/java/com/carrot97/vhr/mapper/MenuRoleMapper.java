package com.carrot97.vhr.mapper;

import com.carrot97.vhr.model.MenuRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MenuRole record);

    int insertSelective(MenuRole record);

    MenuRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MenuRole record);

    int updateByPrimaryKey(MenuRole record);

    Integer[] getMidsByRid(Integer rid);

    void deleteByRid(Integer rid);

    Integer batchInsert(@Param("rid") Integer rid, @Param("mids") Integer[] mids);
}