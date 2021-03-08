package com.carrot97.vhr.controller.system.basic;

import com.carrot97.vhr.model.Position;
import com.carrot97.vhr.model.RespBean;
import com.carrot97.vhr.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/basic/position")
public class PositionController {

    @Autowired
    PositionService positionService;

    @RequestMapping("/")
    public List<Position> getAllPositions() {
        return positionService.getAllPositions();
    }

    @PostMapping("/")
    public RespBean addPosition(@RequestBody Position position) {
        if (positionService.addPosition(position) == 1) {
            return new RespBean.Builder().setStatus(200).setMsg("添加成功").create();
        } else {
            return new RespBean.Builder().setStatus(500).setMsg("添加失败").create();
        }
    }

    @PutMapping("/")
    public RespBean updatePosition(@RequestBody Position position) {
        if (positionService.updatePosition(position) == 1) {
            return new RespBean.Builder().setStatus(200).setMsg("更新成功").create();
        } else {
            return new RespBean.Builder().setStatus(500).setMsg("更新失败").create();
        }
    }

    @DeleteMapping("/{id}")
    public RespBean deletePosition(@PathVariable Integer id) {
        if (positionService.deletePosition(id) == 1) {
            return new RespBean.Builder().setStatus(200).setMsg("删除成功").create();
        } else {
            return new RespBean.Builder().setStatus(500).setMsg("删除失败").create();
        }
    }

    @DeleteMapping("/")
    public RespBean deletePositions(Integer[] ids) {
        System.out.println("收到批量删除请求");
        if (positionService.deletePositions(ids) == ids.length) {
            return new RespBean.Builder().setStatus(200).setMsg("删除成功").create();
        } else {
            return new RespBean.Builder().setStatus(500).setMsg("删除失败").create();
        }
    }
}
