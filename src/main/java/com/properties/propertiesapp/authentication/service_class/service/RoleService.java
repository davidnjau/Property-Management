package com.properties.propertiesapp.authentication.service_class.service;


import com.properties.propertiesapp.authentication.entity.Role;

public interface RoleService {

    boolean isRoleExists(String roleName);
    Role addRole(Role role);
    Role getRoleDetails(String roleName);
    void addRoleToUser(Long roleId, String userId);
//    List<Role> getAllRoles();

}
