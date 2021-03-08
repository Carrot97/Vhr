package com.carrot97.vhr.controller;

import com.carrot97.vhr.model.RespBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @GetMapping("/login")
    public RespBean login() {
        return new RespBean.Builder()
                .setStatus(500)
                .setMsg("请登录")
                .create();
    }
}
