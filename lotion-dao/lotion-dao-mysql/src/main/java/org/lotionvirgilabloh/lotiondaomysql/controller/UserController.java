package org.lotionvirgilabloh.lotiondaomysql.controller;

import org.lotionvirgilabloh.lotionbase.auth.LotionPermission;
import org.lotionvirgilabloh.lotionbase.auth.LotionRole;
import org.lotionvirgilabloh.lotionbase.auth.LotionUser;
import org.lotionvirgilabloh.lotiondaomysql.dao.PermissionRepository;
import org.lotionvirgilabloh.lotiondaomysql.dao.RoleRepositoty;
import org.lotionvirgilabloh.lotiondaomysql.dao.UserRepository;
import org.lotionvirgilabloh.lotiondaomysql.entity.Permission;
import org.lotionvirgilabloh.lotiondaomysql.entity.Role;
import org.lotionvirgilabloh.lotiondaomysql.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("service/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepositoty roleRepositoty;

    @Autowired
    PermissionRepository permissionRepositoty;

    @RequestMapping("getByUsernameAndPwd")
    public User getByUsernameIsAndUserpwdIs(String username,String pwd){
        return userRepository.getByUsernameIsAndUserpwdIs(username,pwd);
    }

    @RequestMapping("getByUsername")
    public LotionUser getByUsername(String username){
        User user =userRepository.getByUsernameIs(username);
        LotionUser lotionUser=null;
        if (user!=null){
            lotionUser=new LotionUser();
            lotionUser.setUserName(user.getUsername());
            lotionUser.setPassword(user.getUserpwd());
            lotionUser.setRoleSet(user.getRoles ());
        }

        return lotionUser;
    }
    @RequestMapping("getAllPerm")
    public List<LotionPermission> getAllPermission(){
        List<Permission> permissions = permissionRepositoty.findAll ();
        List<LotionPermission> lop =new ArrayList<>();
        for(Permission p :permissions){
            LotionPermission lotionPermission =new LotionPermission();
            BeanUtils.copyProperties(p,lotionPermission);
            lotionPermission.setRoles (p.getRoles ());
            System.out.println (p.getRoles ().size ());
            lop.add(lotionPermission);
        }
        return lop;
    }
    @RequestMapping("getAllRole")
    public List<LotionRole> getAllRole(){
        List<Role> lr =roleRepositoty.findAll ();
        List<LotionRole> roles =new ArrayList<>();
        for(Role r :lr){
            LotionRole lotionRole =new LotionRole();
            BeanUtils.copyProperties(r,lotionRole);
            roles.add(lotionRole);
        }
        return roles;
    }

}
