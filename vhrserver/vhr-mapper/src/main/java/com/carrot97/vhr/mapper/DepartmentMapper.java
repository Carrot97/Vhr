package com.carrot97.vhr.mapper;

import com.carrot97.vhr.model.Department;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Department record);

    int insertSelective(Department record);

    Department selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);

    List<Department> getDepartmentsByParentId(Integer pid);

    Department getDeptByNameAndPid(@Param("name") String name,@Param("pid") Integer parentId);

    List<Department> getAllDepartments();
}