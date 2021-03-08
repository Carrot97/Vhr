package com.carrot97.vhr.service;

import com.carrot97.vhr.mapper.MenuMapper;
import com.carrot97.vhr.mapper.MenuRoleMapper;
import com.carrot97.vhr.model.Hr;
import com.carrot97.vhr.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MenuService {

    @Autowired
    MenuMapper menuMapper;

    @Autowired
    MenuRoleMapper menuRoleMapper;

    public List<Menu> getMenusByHrId() {
        return menuMapper.getMenusByHrId(((Hr) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
    }

//    @Cacheable
    public List<Menu> getMenusWithRoles() {
        return menuMapper.getMenusWithRoles();
    }

//    @Cacheable
    public List<Menu> getMenuTree() {
        return menuMapper.getMenuTree();
    }

//    @Cacheable
    public Integer[] getMidsByRid(Integer rid) {
        return menuRoleMapper.getMidsByRid(rid);
    }

    @Transactional
    public boolean updateMenuRole(Integer rid, Integer[] mids) {
        menuRoleMapper.deleteByRid(rid);
        if (mids == null || mids.length == 0) {
            return true;
        }
        Integer result = menuRoleMapper.batchInsert(rid, mids);
        return result == mids.length;
    }
}
