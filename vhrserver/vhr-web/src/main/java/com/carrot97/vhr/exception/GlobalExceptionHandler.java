package com.carrot97.vhr.exception;

import com.carrot97.vhr.model.RespBean;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.sql.SQLException;

//@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLException.class)
    public RespBean sqlException(SQLException e) {
        if (e instanceof java.sql.SQLIntegrityConstraintViolationException) {
            return new RespBean.Builder().setStatus(500).setMsg("该数据存在外键,操作失败").create();
        } else {
            return new RespBean.Builder().setStatus(500).setMsg("数据库异常,操作失败").create();
        }
    }
}
