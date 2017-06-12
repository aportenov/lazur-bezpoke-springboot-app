package com.lazur.services;

import com.lazur.models.view.UserBindingModel;
import com.lazur.models.view.UserEditBindingModel;
import com.lazur.models.view.UserViewModel;
import javassist.NotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.management.relation.RoleNotFoundException;
import java.util.List;

public interface UserService  extends UserDetailsService{

    void createUser(UserBindingModel userBindingModel) throws RoleNotFoundException;

    List<UserViewModel> getAllUsers();

    UserViewModel findById(Long userId) throws NotFoundException;

    void updateUser(Long id, UserEditBindingModel userBindingModel) throws RoleNotFoundException;
}



