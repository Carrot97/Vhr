package com.carrot97.vhr.controller;

import com.carrot97.vhr.controller.system.HrController;
import com.carrot97.vhr.model.Hr;
import com.carrot97.vhr.service.HrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    HrService hrService;

    @RequestMapping("/hrs")
    public List<Hr> getHrsExceptMe() {
        return hrService.getHrsExceptMe();
    }
}
