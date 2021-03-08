package com.carrot97.vhr.controller;

import com.carrot97.vhr.model.RespBean;
import com.carrot97.vhr.model.Salary;
import com.carrot97.vhr.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salary/sob")
public class SalaryController {
    @Autowired
    SalaryService salaryService;

    @GetMapping("/")
    public List<Salary> getAllSalaries() {
        return salaryService.getAllSalaries();
    }

    @PostMapping("/")
    public RespBean addSalary(@RequestBody Salary salary) {
        if (salaryService.addSalary(salary) == 1) {
            return new RespBean.Builder().setStatus(200).setMsg("添加成功").create();
        } else {
            return new RespBean.Builder().setStatus(500).setMsg("添加失败").create();
        }
    }

    @PutMapping("/{id}")
    public RespBean updateSalary(@PathVariable("id") Integer id, @RequestBody Salary salary) {
        if (salaryService.updateSalary(id, salary) == 1) {
            return new RespBean.Builder().setStatus(200).setMsg("修改成功").create();
        } else {
            return new RespBean.Builder().setStatus(500).setMsg("修改失败").create();
        }
    }

    @DeleteMapping("/{id}")
    public RespBean deleteSalary(@PathVariable("id") Integer id) {
        if (salaryService.deleteSalary(id) == 1) {
            return new RespBean.Builder().setStatus(200).setMsg("删除成功").create();
        } else {
            return new RespBean.Builder().setStatus(500).setMsg("删除失败").create();
        }
    }
}
