package dev.fpsaraiva.ecommerce_api.controller;

import dev.fpsaraiva.ecommerce_api.dto.UserDto;
import dev.fpsaraiva.ecommerce_api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/")
    public List<UserDto> getUsers() {
        List<UserDto> users = userService.getAll();
        return users;
    }

    @DeleteMapping("/user/{id}")
    public UserDto delete(@PathVariable long id) {
        return userService.delete(id);
    }

    @GetMapping("/user/search")
    public List<UserDto> queryByName(
            @RequestParam(name="name", required = true)
            String name) {
        return userService.queryByName(name);
    }
}
