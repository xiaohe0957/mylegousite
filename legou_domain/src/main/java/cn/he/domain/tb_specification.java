package cn.he.domain;

import java.io.Serializable;

public class tb_specification implements Serializable {
    private Integer id;
    private String specName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }
}
