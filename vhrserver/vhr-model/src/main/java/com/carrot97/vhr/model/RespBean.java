package com.carrot97.vhr.model;

public class RespBean {
    private Integer status;
    private String msg;
    private Object obj;

//    private RespBean() {
//    }
//
//    private RespBean(Integer status, String msg, Object obj) {
//        this.status = status;
//        this.msg = msg;
//        this.obj = obj;
//    }

//    public static RespBean success(String msg) {
//        return new RespBean(200, msg, null);
//    }
//
//    public static RespBean success(String msg, Object obj) {
//        return new RespBean(200, msg, obj);
//    }
//
//    public static RespBean error(String msg) {
//        return new RespBean(500, msg, null);
//    }
//
//    public static RespBean error(String msg, Object obj) {
//        return new RespBean(500, msg, obj);
//    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "RespBean{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", obj=" + obj +
                '}';
    }

    /*build类*/
    public static class Builder {
        private Integer status = 200;
        private String msg;
        private Object obj;

        public Builder setStatus(Integer status) {
            this.status = status;
            return this;
        }

        public Builder setMsg(String msg) {
            this.msg = msg;
            return this;
        }

        public Builder setObj(Object obj) {
            this.obj = obj;
            return this;
        }

        private void construct(RespBean respBean) {
            respBean.setStatus(status);
            respBean.setMsg(msg);
            respBean.setObj(obj);
        }

        public RespBean create() {
            RespBean respBean = new RespBean();
            construct(respBean);
            return respBean;
        }
    }
}
