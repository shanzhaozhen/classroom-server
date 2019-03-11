package com.shanzhaozhen.classroom.bean;

import javax.persistence.*;

@Entity
@Table(name = "t_file_info")
public class TFileInfo extends BaseBean {

    private static final long serialVersionUID = -7847566141393570588L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fileName;

    private String relativePath;

    private String realPath;

    private String ip;

    private String wechatId;

}
