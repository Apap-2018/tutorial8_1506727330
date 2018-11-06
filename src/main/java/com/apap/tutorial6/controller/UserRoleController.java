package com.apap.tutorial6.controller;

import com.apap.tutorial6.model.UserRoleModel;
import com.apap.tutorial6.security.WebSecurityConfig;
import com.apap.tutorial6.service.UserRoleService;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserRoleController {
    @Autowired
    private UserRoleService userService;

    @Autowired
    private WebSecurityConfig webSecurityConfig;
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    private String addUserSubmit(@ModelAttribute UserRoleModel user){
        userService.addUser(user);
        return "home";
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    private String updatePasswordSubmit(@RequestParam(value = "username") String username,
                                        @RequestParam(value = "passwordLama") String passwordLama,
                                        @RequestParam(value = "passwordBaru") String passwordBaru,
                                        @RequestParam(value = "passwordBaruKonfirmasi") String passwordBaruKonfirmasi){
        System.out.println("NAH");
        System.out.println(username);
        System.out.println(passwordLama);
        UserRoleModel user = userService.findByUsernameAndPassword(username,passwordLama);
        System.out.println(user);
        if (user != null){
            System.out.println("ada nih usernya");
        }
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        if (user.getPassword().equals(webSecurityConfig.encoder().encode(passwordLama))
        && passwordBaru.equals(passwordBaruKonfirmasi)){
            System.out.println("sama nih bisa diganti deh");
            user.setPassword(passwordBaru);
            userService.addUser(user);
        }else{
            System.out.println("SALAH");
        }
        return "home";
    }
}
