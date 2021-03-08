package com.carrot97.vhr.controller.system.basic;

import com.carrot97.vhr.model.Department;
import com.carrot97.vhr.model.RespBean;
import com.carrot97.vhr.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/basic/department")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @GetMapping("/")
    public List<Department> getDepartmentTree() {
        return departmentService.getDepartmentsByParentId(-1);
    }

    @PostMapping("/")
    public RespBean addDept(@RequestBody Department department) {
        Department newDept = departmentService.addDept(department);
        if (newDept != null) {
            return new RespBean.Builder().setStatus(200).setMsg("添加成功").setObj(newDept).create();
        } else {
            return new RespBean.Builder().setStatus(500).setMsg("添加失败").create();
        }
    }

    @DeleteMapping("/{id}")
    public RespBean deleteDept(@PathVariable("id") Integer id) {
        Integer res = departmentService.deleteDept(id);
        if (res == -1) {
            return new RespBean.Builder().setStatus(500).setMsg("删除失败，该部门存在子部门").create();
        } else if (res == -2) {
            return new RespBean.Builder().setStatus(500).setMsg("删除失败，该部门存在员工").create();
        } else {
            return new RespBean.Builder().setStatus(200).setMsg("删除成功").create();
        }
    }
}
