package cn.he.entity;

import java.io.Serializable;

public class result implements Serializable {
        private boolean success;
        // 提示消息
        private String message;
        // 总记录数
        private Long total;
        // 表示数据
        private Object data;

    public result() {
    }

    public result(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public result(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public result(boolean success, String message, Long total, Object data) {
        this.success = success;
        this.message = message;
        this.total = total;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
