package com.carrot97.vhr.service;

import com.carrot97.vhr.mapper.HrMapper;
import com.carrot97.vhr.mapper.HrRoleMapper;
import com.carrot97.vhr.model.Hr;
import com.carrot97.vhr.utils.HrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HrService implements UserDetailsService {

    @Autowired
    HrMapper hrMapper;

    @Autowired
    HrRoleMapper hrRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Hr hr = hrMapper.selectByUsername(username);
        if (hr == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        hr.setRoles(hrMapper.selectRolesByHrId(hr.getId()));
        return hr;
    }

    /* 查询时不返回自己 */
    public List<Hr> getHrs(String keyword) {
        Integer hrid = HrUtils.getCurrentHr().getId();
        return hrMapper.getHrs(hrid, keyword);
    }

    public Integer updateHr(Hr hr) {
        return hrMapper.updateByPrimaryKeySelective(hr);
    }

    @Transactional
    public Boolean updateRolesByHrid(Integer hrid, Integer[] rids) {
        boolean result1 = hrRoleMapper.deleteByHrid(hrid);
        boolean result2 = hrRoleMapper.batchInsertByHrid(hrid, rids);
        return result1 && result2;
    }

    public Integer deleteByHrid(Integer hrid) {
        return hrMapper.deleteByPrimaryKey(hrid);
    }

    public List<Hr> getHrsExceptMe() {
        return hrMapper.getHrsExceptMe(HrUtils.getCurrentHr().getId());
    }
}
