package com.lazur.services;

import com.lazur.models.users.UserBindingModel;
import com.lazur.models.users.UserViewModel;
import javassist.NotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.management.relation.RoleNotFoundException;
import java.util.List;

public interface UserService  extends UserDetailsService{

    void createUser(UserBindingModel userBindingModel) throws RoleNotFoundException;

    List<UserViewModel> getAllUsers();

    UserViewModel findById(Long userId) throws NotFoundException;

    void updateUser(Long id, UserBindingModel userBindingModel) throws RoleNotFoundException;

    void deleteUser(Long id) throws NotFoundException;
}




