package ru.kata.spring.boot_security.demo.Init;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataBaseInit {

    private final UserService userService;

    private final RoleService roleService;

    public DataBaseInit(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    private void dataBaseInit() {
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");
        Set<Role> setAdmin = new HashSet<>();
        Set<Role> setUser = new HashSet<>();

        roleService.addRole(roleAdmin);
        roleService.addRole(roleUser);

        setAdmin.add(roleAdmin);
        setAdmin.add(roleUser);
        setUser.add(roleUser);

        User admin = new User("ivan", "$2a$12$MGQ3/XWtPf2f4ryMf4sst.PPnMSeuzaFUZSh6bTZlq7e8zW8X0XOC",
                "Ivan", "Ivanov", "Moscow", 27, setAdmin);
        User user = new User("elena", "$2a$12$lE7jA6wGpVuB8k7x.aq6ru4DRCk8CSW26SqxytNZL0pN6ECYwwiS2",
                "Elena", "Sidorova","Kirov", 35, setUser);
        userService.saveUser(admin);
        userService.saveUser(user);
    }
}
