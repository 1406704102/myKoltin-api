package com.my.kotlin.system.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sys_dict_detail_info")
public class DictDetailInfo {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "dict_id")
    private Integer dictId;

    @Column(name = "dict_detail_name")
    private String dictDetailName;

    @Column(name = "dict_detail_key")
    private String dictDetailKey;

    @Column(name = "create_time")
    private Timestamp createTime;
}
