package com.lazur.serviceImpl;

import com.lazur.entities.Role;
import com.lazur.models.users.RoleViewModel;
import com.lazur.repositories.RoleRepository;
import com.lazur.services.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private static final String ADMIN = "ADMIN";
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String GUEST = "GUEST";
    private static final String ROLE_GUEST = "ROLE_GUEST";

    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(ModelMapper modelMapper, RoleRepository roleRepository) {
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<RoleViewModel> getAllRoles(){
        List<Role> roles = this.roleRepository.findAll();
        List<RoleViewModel> roleViewModels = new ArrayList<>();
        for (Role role : roles) {
            RoleViewModel roleViewModel = this.modelMapper.map(role, RoleViewModel.class);

            roleViewModel.setAuthority(formatRole(role.getAuthority()));
            roleViewModels.add(roleViewModel);
        }
        return roleViewModels;
    }

    private String formatRole(String role) {
        String newRole = null;
        if (role.equalsIgnoreCase(ROLE_ADMIN)){
            newRole = ADMIN;
        }else if(role.equalsIgnoreCase(ROLE_GUEST)){
            newRole = GUEST;
        }

        return newRole;
    }

    @Override
    public Role getCurrentRole(String roleName) throws RoleNotFoundException {
        Role role = this.roleRepository.findByAuthority(roleName);
        if (role == null){
            throw new RoleNotFoundException();
        }
        return role;
    }

}
