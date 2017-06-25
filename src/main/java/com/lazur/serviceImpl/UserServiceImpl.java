package com.lazur.serviceImpl;

import com.lazur.entities.Role;
import com.lazur.entities.User;
import com.lazur.massages.Errors;
import com.lazur.models.users.UserBindingModel;
import com.lazur.models.users.UserViewModel;
import com.lazur.repositories.UserRepository;
import com.lazur.services.RoleService;
import com.lazur.services.UserService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private static final String ADMIN = "ADMIN";
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String GUEST = "GUEST";
    private static final String ROLE_GUEST = "ROLE_GUEST";

    private final ModelMapper modelMapper;
    private final RoleService roleService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(ModelMapper modelMapper,
                           RoleService roleService,
                           UserRepository userRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.modelMapper = modelMapper;
        this.roleService = roleService;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findOneByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(Errors.INVALID_CREDENTIALS);
        }

        return user;
    }

    @Override
    public void createUser(UserBindingModel userBindingModel) throws RoleNotFoundException {
        User user = new User();
        user.setUsername(userBindingModel.getUsername());
        String encryptedPassword = this.bCryptPasswordEncoder.encode(userBindingModel.getPassword());
        user.setPassword(encryptedPassword);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        user.addRole(this.roleService.getCurrentRole(format(userBindingModel.getRole())));

        this.userRepository.save(user);

    }

    @Override
    public List<UserViewModel> getAllUsers() {
        List<User> users = this.userRepository.findAll();
        List<UserViewModel> userViewModels = new ArrayList<>();
        for (User user : users) {
            UserViewModel userViewModel = this.modelMapper.map(user, UserViewModel.class);
            userViewModel.setRole(user.getRoles().stream().findFirst().map(role -> format(role.getAuthority())).get().toString());
            userViewModels.add(userViewModel);

        }

        return userViewModels;
    }

    private <E> E format(String role) {
        String newRole = null;
        if (role.equalsIgnoreCase(ROLE_ADMIN)) {
            newRole = ADMIN;
        } else if (role.equalsIgnoreCase(ROLE_GUEST)) {
            newRole = GUEST;
        } else if (role.equalsIgnoreCase(GUEST)) {
            newRole = ROLE_GUEST;
        } else if (role.equalsIgnoreCase(ADMIN)) {
            newRole = ROLE_ADMIN;
        }

            return (E) newRole;
        }

        @Override
        public UserViewModel findById (Long userId) throws NotFoundException {
            User user = this.userRepository.findOne(userId);
            if (user == null) {
                throw new NotFoundException("User not found");

            }

            UserViewModel userViewModel = this.modelMapper.map(user, UserViewModel.class);
            userViewModel.setRole(user.getRoles().stream().findFirst().map(role -> format(role.getAuthority())).get().toString());
            return userViewModel;
        }

        @Override
        public void updateUser (Long id, UserBindingModel userBindingModel) throws RoleNotFoundException {
            User user = this.userRepository.findOne(id);
            if (userBindingModel.getPassword() != null) {
                user.setPassword(this.bCryptPasswordEncoder.encode(userBindingModel.getPassword()));
            }

            user.setUsername(userBindingModel.getUsername());
            Role role = this.roleService.getCurrentRole(format(userBindingModel.getRole()));
            Set<Role> roleSet = new HashSet<>();
            roleSet.add(role);
            user.setRoles(roleSet);

            this.userRepository.save(user);
        }

    @Override
    public void deleteUser(Long id) throws NotFoundException {
        User user = this.userRepository.findOne(id);
        if (user == null){
            throw new UsernameNotFoundException(Errors.USER_NOT_FOUND);
        }

        this.userRepository.delete(user);
    }

}
