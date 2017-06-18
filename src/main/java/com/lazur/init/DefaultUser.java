package com.lazur.init;

import com.lazur.entities.Role;
import com.lazur.entities.User;
import com.lazur.repositories.RoleRepository;
import com.lazur.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
@Component
public class DefaultUser {

    private final String ROLE_ADMIN = "ROLE_ADMIN";
    private final String ROLE_GUEST = "ROLE_GUEST";
    private final String USERNAME = "1";
    private final String PASSWORD = "1";


    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public DefaultUser(RoleRepository roleRepository,
                       UserRepository userRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostConstruct
    public void init(){

        long roleSize = this.roleRepository.count();
        if (roleSize == 0) {
            Role admin = new Role();
            admin.setAuthority(ROLE_ADMIN);
            this.roleRepository.save(admin);

            Role guest = new Role();
            guest.setAuthority(ROLE_GUEST);
            this.roleRepository.save(guest);
        }

        long userCount = this.userRepository.count();
        if (userCount == 0) {
            User user = new User();
            user.setUsername(USERNAME);
            String encryptedPassword = this.bCryptPasswordEncoder.encode(PASSWORD);
            user.setPassword(encryptedPassword);
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);
            user.setEnabled(true);
            user.addRole(this.roleRepository.findByAuthority(ROLE_ADMIN));
            this.userRepository.save(user);
        }

    }
}
