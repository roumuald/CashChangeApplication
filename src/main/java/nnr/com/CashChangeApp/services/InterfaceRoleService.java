package nnr.com.CashChangeApp.services;

import nnr.com.CashChangeApp.entites.Role;

import java.util.List;

public interface InterfaceRoleService {
    public Role saveRole(Role role);
    public List<Role> getAllRole();
    public Role updateRole(Long id, Role newRole);
    public void deleteRole(Long id);

}
