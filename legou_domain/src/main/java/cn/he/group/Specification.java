package cn.he.group;

import cn.he.domain.tb_specification;
import cn.he.domain.tb_specification_option;

import java.io.Serializable;
import java.util.List;

public class Specification implements Serializable {
        private tb_specification tbSpecification;
        private List<tb_specification_option> tbSpecificationOptionList;

        public tb_specification getTbSpecification() {
            return tbSpecification;
        }

        public void setTbSpecification(tb_specification tbSpecification) {
            this.tbSpecification = tbSpecification;
        }

        public List<tb_specification_option> getTbSpecificationOptionList() {
            return tbSpecificationOptionList;
        }

        public void setTbSpecificationOptionList(List<tb_specification_option> tbSpecificationOptionList) {
            this.tbSpecificationOptionList = tbSpecificationOptionList;
        }

//        public Specification(tb_specification tbSpecification, List<tb_specification_option> tbSpecificationOptionList) {
//            this.tbSpecification = tbSpecification;
//            this.tbSpecificationOptionList = tbSpecificationOptionList;
//        }
    }
