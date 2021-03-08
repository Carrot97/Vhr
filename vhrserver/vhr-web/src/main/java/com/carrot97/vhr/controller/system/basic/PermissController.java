package com.carrot97.vhr.controller.system.basic;

import com.carrot97.vhr.model.Menu;
import com.carrot97.vhr.model.RespBean;
import com.carrot97.vhr.model.Role;
import com.carrot97.vhr.service.MenuService;
import com.carrot97.vhr.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/basic/permiss")
public class PermissController {

    @Autowired
    RoleService roleService;

    @Autowired
    MenuService menuService;

    @GetMapping("/")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping("/menus")
    public List<Menu> getMenuTree() {
        return menuService.getMenuTree();
    }

    @GetMapping("/mids/{rid}")
    public Integer[] getMidsByRid(@PathVariable("rid") Integer rid) {
        return menuService.getMidsByRid(rid);
    }

    @PutMapping("/")
    public RespBean updateMenuRole(Integer rid, Integer[] mids) {
        if (menuService.updateMenuRole(rid, mids)) {
            return new RespBean.Builder().setStatus(200).setMsg("更新成功").create();
        } else {
            return new RespBean.Builder().setStatus(500).setMsg("更新失败").create();
        }
    }

    @PostMapping("/")
    public RespBean addRole(@RequestBody Role role) {
        if (roleService.addRole(role) == 1) {
            return new RespBean.Builder().setStatus(200).setMsg("添加成功").create();
        } else {
            return new RespBean.Builder().setStatus(500).setMsg("添加失败").create();
        }
    }

    @DeleteMapping("/{id}")
    public RespBean deleteRoleById(@PathVariable("id") Integer id) {
        if (roleService.deleteRoleById(id) == 1) {
            return new RespBean.Builder().setStatus(200).setMsg("删除成功").create();
        } else {
            return new RespBean.Builder().setStatus(500).setMsg("删除失败").create();
        }
    }
}
