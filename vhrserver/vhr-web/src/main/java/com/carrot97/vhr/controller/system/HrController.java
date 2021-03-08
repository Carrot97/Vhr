package com.carrot97.vhr.controller.system;

import com.carrot97.vhr.model.Hr;
import com.carrot97.vhr.model.RespBean;
import com.carrot97.vhr.model.Role;
import com.carrot97.vhr.service.HrService;
import com.carrot97.vhr.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/hr")
public class HrController {

    @Autowired
    HrService hrService;

    @Autowired
    RoleService roleService;

    @GetMapping("/")
    public List<Hr> getHrs(String keyword) {
        return hrService.getHrs(keyword);
    }

    @PutMapping("/")
    public RespBean updateHr(@RequestBody Hr hr) {
        if (hrService.updateHr(hr) == 1) {
            return new RespBean.Builder().setStatus(200).setMsg("更新成功").create();
        } else {
            return new RespBean.Builder().setStatus(500).setMsg("更新失败").create();
        }
    }

    @GetMapping("/role")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @PutMapping("/role")
    public RespBean updateRolesByHrid(Integer hrid, Integer[] rids) {
        if (hrService.updateRolesByHrid(hrid, rids)) {
            return new RespBean.Builder().setStatus(200).setMsg("更新成功").create();
        } else {
            return new RespBean.Builder().setStatus(500).setMsg("更新失败").create();
        }
    }

    @DeleteMapping("/{hrid}")
    public RespBean deleteByHrid(@PathVariable("hrid") Integer hrid) {
        if (hrService.deleteByHrid(hrid) == 1) {
            return new RespBean.Builder().setStatus(200).setMsg("删除成功").create();
        } else {
            return new RespBean.Builder().setStatus(500).setMsg("删除失败").create();
        }
    }
}
