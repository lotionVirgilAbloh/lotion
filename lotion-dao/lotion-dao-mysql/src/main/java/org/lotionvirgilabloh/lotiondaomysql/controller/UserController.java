package org.lotionvirgilabloh.lotiondaomysql.controller;

import org.lotionvirgilabloh.lotiondaomysql.dao.UserRepository;
import org.lotionvirgilabloh.lotiondaomysql.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("job")
public class UserController {

    @Autowired
    public UserRepository userRepository;

    @RequestMapping("getByUsernameAndPwd")
    public User getByUsernameIsAndUserpwdIs(String username,String pwd){
        return userRepository.getByUsernameIsAndUserpwdIs(username,pwd);
    }

    @RequestMapping("getByUsername")
    public User getByUsername(String username){
        return userRepository.getByUsernameIs(username);
    }
}
