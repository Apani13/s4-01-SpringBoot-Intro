package cat.itacademy.s04.t01.userapi.controllers;

import cat.itacademy.s04.t01.userapi.exceptions.UserNotFoundException;
import cat.itacademy.s04.t01.userapi.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UserController {

    public static List<User> users = new ArrayList<User>();

    @GetMapping("/users")
    public List<User> getUsers(@RequestParam(required = false) String name) {
        if (name == null || name.isBlank()) {
            return users;
        }

        return users.stream()
                .filter(u -> u.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }

    @PostMapping("/users")
    public User addUser(@RequestBody User user) {

        user.setId(UUID.randomUUID());

        users.add(user);

        return user;

    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable UUID id) {
        return users.stream()
                .filter(u -> id.equals(u.getId()))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException(id));
    }


}
