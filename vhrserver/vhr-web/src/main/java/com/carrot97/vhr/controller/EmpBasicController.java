package com.carrot97.vhr.controller;

import com.carrot97.vhr.model.*;
import com.carrot97.vhr.service.*;
import com.carrot97.vhr.utils.POIUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.security.util.Length;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/emp/basic")
public class EmpBasicController {

    @Autowired
    EmpBasicService empBasicService;

    @Autowired
    NationService nationService;

    @Autowired
    PoliticsstatusService politicsstatusService;

    @Autowired
    PositionService positionService;

    @Autowired
    JobLevelService jobLevelService;

    @Autowired
    DepartmentService departmentService;
    
    @GetMapping("/")
    public PageInfo<Employee> getEmpByPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                           @RequestParam(defaultValue = "10") Integer pageSize,
                                           Employee employee, Date[] beginDateScope) {
        return empBasicService.getEmpByPage(pageNum, pageSize, employee, beginDateScope);
    }

    @PostMapping("/")
    public RespBean addEmp(@RequestBody Employee employee) {
        if (empBasicService.addEmp(employee) == 1) {
            return new RespBean.Builder().setStatus(200).setMsg("添加成功").create();
        } else {
            return new RespBean.Builder().setStatus(500).setMsg("添加失败").create();
        }
    }

    @GetMapping("/nations")
    public List<Nation> getAllNations() {
        return nationService.getAllNations();
    }

    @GetMapping("/politicsstatus")
    public List<Politicsstatus> getAllPoliticsstatus() {
        return politicsstatusService.getAllPoliticsstatus();
    }

    @GetMapping("/positions")
    public List<Position> getAllPositions() {
        return positionService.getAllPositions();
    }

    @GetMapping("/jobLevels")
    public List<JobLevel> getAllJobLevels() {
        return jobLevelService.getAllJobLevels();
    }

    @GetMapping("/departments")
    public List<Department> getAllDepartments() {
        return departmentService.getDepartmentsByParentId(-1);
    }

    @GetMapping("/workId")
    public RespBean getWorkId() {
        return new RespBean.Builder().setStatus(200)
                .setObj(String.format("%08d", empBasicService.getMaxWorkId() + 1)).create();
    }

    @PutMapping("/")
    public RespBean updateEmp(@RequestBody Employee employee) {
        if (empBasicService.updateEmp(employee) == 1) {
            return new RespBean.Builder().setStatus(200).setMsg("更新成功").create();
        } else {
            return new RespBean.Builder().setStatus(500).setMsg("更新失败").create();
        }
    }

    @DeleteMapping("/{id}")
    public RespBean deleteById(@PathVariable("id") Integer empId) {
        if (empBasicService.deleteById(empId) == 1) {
            return new RespBean.Builder().setStatus(200).setMsg("删除成功").create();
        } else {
            return new RespBean.Builder().setStatus(500).setMsg("删除失败").create();
        }
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportData() {
        List<Employee> allEmps = empBasicService.getAllEmps();
        return POIUtils.employee2Excel(allEmps);
    }

    @PostMapping("/import")
    public RespBean importData(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            new RespBean.Builder().setStatus(500).setMsg("上传文件为空或上传失败").create();
        }

        List<Employee> employees = POIUtils.excel2Employee(file, nationService.getAllNations(), politicsstatusService.getAllPoliticsstatus(),
                jobLevelService.getAllJobLevels(), positionService.getAllPositions(), departmentService.getAllDepartments());

        for (Employee employee : employees) {
            System.out.println(employee);
        }

        if (employees.isEmpty()) {
            new RespBean.Builder().setStatus(500).setMsg("文件无内容或内容解析失败").create();
        }

        int result = empBasicService.batchAddEmps(employees);

        if (result == employees.size()) {
            return new RespBean.Builder().setStatus(200).setMsg("添加成功").create();
        } else if (result > 0) {
            return new RespBean.Builder().setStatus(500).setMsg("添加部分成功").create();
        } else {
            return new RespBean.Builder().setStatus(500).setMsg("添加失败").create();
        }
    }
}
