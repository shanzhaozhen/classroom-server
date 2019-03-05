package com.shanzhaozhen.classroom.common;

public class CommonConst {

    public enum SexType {

        MALE("男" ,0),
        FEMALE("女" ,1);

        private String name;

        private Integer value;

        SexType(String name, Integer value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }
    }


}
