package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserRESTController {
    private final UserService userService;

    @Autowired
    public UserRESTController(UserService userService) {

        this.userService = userService;
    }

    @RequestMapping(value = "")
    public ModelAndView getUserPage(Model model, Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/user-page");
        return modelAndView;
    }

    @GetMapping("/getUser")
    public ResponseEntity<List<User>> getUser(Principal principal) {
        Long id = userService.getUserByUsername(principal.getName()).getId();
        return new ResponseEntity<>(Collections.singletonList(userService.getUserById(id)), HttpStatus.OK);
    }
}
