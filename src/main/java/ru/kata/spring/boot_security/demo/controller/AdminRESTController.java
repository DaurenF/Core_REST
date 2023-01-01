package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminRESTController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminRESTController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    @RequestMapping(value = "")
    public ModelAndView getAdminPage(Principal principal, @ModelAttribute ("user") User user) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/admin-page");
        Long id = userService.getUserByUsername(principal.getName()).getId();
        modelAndView.addObject("user", userService.getUserById(id));
        modelAndView.addObject("usersList", userService.getAllUsers());
        modelAndView.addObject("rolesList", roleService.getRoles());
        modelAndView.addObject("newUser", new User());
        return modelAndView;
    }

    @RequestMapping("/admin-user")
    public ModelAndView getAdminUserpage(Principal principal){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/admin-user-page");
        modelAndView.addObject("user",userService.getUserByUsername(principal.getName()));
        return modelAndView;
    }

    @GetMapping("/current-user")
    public ResponseEntity<List<User>> getCurrentUser(Principal principal){
        return new ResponseEntity<>(Collections.singletonList(userService.getUserByUsername(principal.getName())), HttpStatus.OK);
    }

    @PostMapping(value = "/create-user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> usersList = userService.getAllUsers();
        return new ResponseEntity<>(usersList, HttpStatus.OK);
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User userById = userService.getUserById(id);
        return new ResponseEntity<>(userById, HttpStatus.OK);
    }

    @PutMapping (value = "/update-user/{id}")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody User user, @PathVariable Long id) {
        userService.updateUser(id, user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete-user/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
        userService.removeUserById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping(value ="/get-roles")
    public ResponseEntity<List<Role>> getRoles() {
        List<Role> roles = roleService.getRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
}
