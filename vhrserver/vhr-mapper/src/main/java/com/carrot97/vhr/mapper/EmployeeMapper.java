package com.carrot97.vhr.mapper;

import com.carrot97.vhr.model.Employee;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EmployeeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Employee record);

    int insertSelective(Employee record);

    Employee selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);

    List<Employee> getByDeptId(Integer id);

    List<Employee> getAllEmps(@Param("emp") Employee employee, @Param("begin") Date[] beginDateScope);

    Integer getMaxWorkId();

    Integer BatchInsert(@Param("emps") List<Employee> employees);
}