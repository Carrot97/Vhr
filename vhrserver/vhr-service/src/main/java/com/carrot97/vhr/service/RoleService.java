package com.carrot97.vhr.service;

import com.carrot97.vhr.mapper.RoleMapper;
import com.carrot97.vhr.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    RoleMapper roleMapper;

    public List<Role> getAllRoles() {
        return roleMapper.getAllRoles();
    }

    public int addRole(Role role) {
        return roleMapper.insert(role);
    }

    public int deleteRoleById(Integer id) {
        return roleMapper.deleteByPrimaryKey(id);
    }
}
