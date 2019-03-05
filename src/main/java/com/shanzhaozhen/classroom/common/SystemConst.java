package com.shanzhaozhen.classroom.common;

public class SystemConst {


    public enum RequestMethod {

        GET("查看", "GET"),
        POST("新增", "POST"),
        PUT("修改", "PUT"),
        PATCH("更新", "PATCH"),
        DELETE("删除", "DELETE");

        private String name;
        private String value;

        RequestMethod(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }


    public enum MenuType {

        PATH("路径" ,0),
        MENU("菜单" ,1),
        API("API" ,2),
        Button("按钮" ,3);

        private String name;

        private Integer value;

        MenuType(String name, Integer value) {
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
