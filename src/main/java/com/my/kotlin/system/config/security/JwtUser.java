package com.my.kotlin.system.config.security;

import com.my.kotlin.system.entity.MenuInfo;
import com.my.kotlin.system.entity.RoleInfo;
import com.my.kotlin.system.entity.UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtUser implements UserDetails {

    private UserInfo userInfo;
    private List<MenuInfo> menuInfoList;
    
    public JwtUser(UserInfo userInfo, List<MenuInfo> menuInfoList) {
        this.userInfo = userInfo;
        this.menuInfoList = menuInfoList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<RoleInfo> roleInfos = userInfo.getRoleInfos();
        List<SimpleGrantedAuthority> collect = new ArrayList<>();
        //获得所有权限名称
        roleInfos.forEach(f->{
            List<MenuInfo> menus = f.getMenus();
            collect.addAll(menus.stream().map(resource -> new SimpleGrantedAuthority(resource.getPermission()))
                    .collect(Collectors.toList()));
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return userInfo.getPassword();
    }

    @Override
    public String getUsername() {
        return userInfo.getUserName();
    }
    /**
     * 账户是否未过期
     **/
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    /**
     * 账户是否未锁定
     **/
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    /**
     * 密码是否未过期
     **/
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    /**
     * 账户是否激活
     **/
    @Override
    public boolean isEnabled() {
        return true;
    }
}