package com.carrot97.vhr.service;

import com.carrot97.vhr.mapper.DepartmentMapper;
import com.carrot97.vhr.mapper.EmployeeMapper;
import com.carrot97.vhr.model.Department;
import com.carrot97.vhr.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    EmployeeMapper employeeMapper;

//    @Cacheable
    public List<Department> getDepartmentsByParentId(Integer pid) {
        return departmentMapper.getDepartmentsByParentId(pid);
    }

//    @Cacheable
    public List<Department> getAllDepartments() {
        return departmentMapper.getAllDepartments();
    }

//    @CacheEvict
    @Transactional
    public Department addDept(Department department) {
        // 添加默认字段，将新建部门插入数据库
        department.setEnabled(true);
        department.setIsParent(false);
        int res1 = departmentMapper.insertSelective(department);
        // 利用名字和父部门查出刚刚插入的部门（获取其ID）
        department = departmentMapper.getDeptByNameAndPid(department.getName(), department.getParentId());
        // 取出新部门的父部门
        Department parentDept = departmentMapper.selectByPrimaryKey(department.getParentId());
        // 给新部门添加path
        department.setDepPath(parentDept.getDepPath() + '.' + department.getId());
        // 更新新部门
        int res2 = departmentMapper.updateByPrimaryKey(department);
        // 更新老部门
        parentDept.setIsParent(true);
        int res3 = departmentMapper.updateByPrimaryKey(parentDept);

        if ((res1 == 1) && (res2 == 1) && (res3 == 1)) {
            return department;
        } else {
            return null;
        }
    }

//    @CacheEvict
    @Transactional
    public Integer deleteDept(Integer id) {
        Department department = departmentMapper.selectByPrimaryKey(id);
//        if (department.getIsParent()) return -1;
        // 查询要删除部门是否为父部门
        List<Department> departments = departmentMapper.getDepartmentsByParentId(id);
        if (!departments.isEmpty()) return -1;
        // 查询要删除部门是否存在员工
        List<Employee> employees = employeeMapper.getByDeptId(id);
        if (!employees.isEmpty()) return -2;
        // 删除部门
        departmentMapper.deleteByPrimaryKey(id);
        // 检查其父部门是否需要改变isParent属性
        departments = departmentMapper.getDepartmentsByParentId(department.getParentId());
        if (departments.isEmpty()) {
            Department parentDept = new Department();
            parentDept.setId(department.getParentId());
            parentDept.setIsParent(false);
            departmentMapper.updateByPrimaryKeySelective(parentDept);
        }
        return 0;
    }
}
