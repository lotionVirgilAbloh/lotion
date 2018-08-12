package org.lotionvirgilabloh.lotiondaomysql.controller;

import org.lotionVirgilAbloh.lotionbase.dto.LotionUser;
import org.lotionvirgilabloh.lotiondaomysql.dao.UserRepository;
import org.lotionvirgilabloh.lotiondaomysql.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    public UserRepository userRepository;

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
}

        return lotionUser;
    }
}
