package com.my.kotlin.system.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_info")
public class UserInfo {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "create_time")
    private Timestamp createTime;

    /*
     * @Author pangjie
     * @Description //TODO  referencedColumnName -> 关联表中对应的id        OrderBy -> 排序
     *
     *      没有关联表:{
     *      @OneToOne
     *      @JoinColumn(name = "video_id2")
     *      }
     *
     * @Date 17:52 2020/11/18 0018
     * @Param 
     * @return 
     */
//    @OneToOne
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "sys_users_roles",
            joinColumns = {@JoinColumn(name = "user_id",referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "id")})
    @OrderBy("createTime desc")
    private Set<RoleInfo> roleInfos;


    @Column(name = "enable")
    private String enable;
}
