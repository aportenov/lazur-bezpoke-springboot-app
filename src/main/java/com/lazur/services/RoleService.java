package com.lazur.services;


import com.lazur.entities.Role;
import com.lazur.models.users.RoleViewModel;

import javax.management.relation.RoleNotFoundException;
import java.util.List;

public interface RoleService {

    List<RoleViewModel> getAllRoles();

    Role getCurrentRole(String roleName) throws RoleNotFoundException;
}
