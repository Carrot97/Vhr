package com.carrot97.vhr.controller.system.basic;

import com.carrot97.vhr.model.JobLevel;
import com.carrot97.vhr.model.RespBean;
import com.carrot97.vhr.service.JobLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/system/basic/joblevel")
public class JobLevelController {

    @Autowired
    JobLevelService jobLevelService;

    @RequestMapping("/")
    public List<JobLevel> getAllJobLevels() {
        return jobLevelService.getAllJobLevels();
    }

    @PostMapping("/")
    public RespBean addJobLevel(@RequestBody JobLevel jobLevel) {
        if (jobLevelService.addJobLevel(jobLevel) == 1) {
            return new RespBean.Builder().setStatus(200).setMsg("添加成功").create();
        } else {
            return new RespBean.Builder().setStatus(500).setMsg("添加失败").create();
        }
    }

    @PutMapping("/")
    public RespBean updateJobLevel(@RequestBody JobLevel jobLevel) {
        if (jobLevelService.updateJobLevel(jobLevel) == 1) {
            return new RespBean.Builder().setStatus(200).setMsg("更新成功").create();
        } else {
            return new RespBean.Builder().setStatus(500).setMsg("更新失败").create();
        }
    }

    @DeleteMapping("/{id}")
    public RespBean deleteJobLevel(@PathVariable Integer id) {
        if (jobLevelService.deleteJobLevel(id) == 1) {
            return new RespBean.Builder().setStatus(200).setMsg("删除成功").create();
        } else {
            return new RespBean.Builder().setStatus(500).setMsg("删除失败").create();
        }
    }

    @DeleteMapping("/")
    public RespBean deleteJobLevels(Integer[] ids) {
        System.out.println("收到批量删除请求");
        if (jobLevelService.deleteJobLevels(ids) == ids.length) {
            return new RespBean.Builder().setStatus(200).setMsg("删除成功").create();
        } else {
            return new RespBean.Builder().setStatus(500).setMsg("删除失败").create();
        }
    }
}
