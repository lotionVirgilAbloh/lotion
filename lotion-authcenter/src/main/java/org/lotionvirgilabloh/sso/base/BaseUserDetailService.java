package org.lotionvirgilabloh.sso.base;


import org.lotionvirgilabloh.lotionbase.auth.LotionPermission;
import org.lotionvirgilabloh.sso.service.AuthCenterManager;
import org.lotionvirgilabloh.lotionbase.auth.LotionUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BaseUserDetailService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AuthCenterManager authCenterManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        LotionUser lu =authCenterManager.getByUsername(username);


        // 调用FeignClient查询角色
        Set<String> roles = lu.getRoleSet();

        // 获取用户权限列表
        List<GrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(e -> {
            // 存储用户、角色信息到GrantedAuthority，并放到GrantedAuthority列表
            GrantedAuthority authority = new SimpleGrantedAuthority(e);
            authorities.add(authority);
        });

        // 返回带有用户权限信息的User
        User user =  new User(lu.getUserName(),
                lu.getPassword(), isActive(lu.isActive()), true, true, true, authorities);
        return new BaseUserDetail(lu, user);
    }

    private boolean isActive(int active){
        return active == 1 ? true : false;
    }
}
