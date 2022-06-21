package com.my.kotlin.system.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "sys_menu_info")
public class MenuInfo {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "permission")
    private String permission;

    @Column(name = "parent_id")
    private Integer parentId;

    @Column(name = "level_num")
    private Integer levelNum;

    @Column(name = "sort")
    private Integer sort;

    @Column(name = "component")
    private String component;

    @Column(name = "sub_count")
    private String subCount;

    @Column(name = "create_time")
    private Timestamp createTime;


}
