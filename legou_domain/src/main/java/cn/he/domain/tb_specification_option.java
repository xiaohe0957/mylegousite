package cn.he.domain;

import java.io.Serializable;

public class tb_specification_option implements Serializable {
        private Integer id;
        private String optionName;
        private Integer specId;
        private Integer orders;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public Integer getSpecId() {
        return specId;
    }

    public void setSpecId(Integer specId) {
        this.specId = specId;
    }

    public Integer getOrders() {
        return orders;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "tb_specification_option{" +
                "id=" + id +
                ", optionName='" + optionName + '\'' +
                ", specId=" + specId +
                ", orders=" + orders +
                '}';
    }
}
