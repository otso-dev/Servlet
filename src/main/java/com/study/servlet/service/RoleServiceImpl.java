package com.study.servlet.service;

import com.study.servlet.entity.Role;
import com.study.servlet.repository.RoleRepository;
import com.study.servlet.repository.RoleRepositoryImpl;

public class RoleServiceImpl implements RoleService{
	
	private static RoleService instance;
	
	public static RoleService getIntance() {
		if(instance==null) {
			instance = new RoleServiceImpl();
		}
		return instance;
	}
	
	private RoleRepository roleRepository;
	
	private RoleServiceImpl() {
		roleRepository = RoleRepositoryImpl.getInstance();
	}

	@Override
	public Role getRole(String rolename) {
		
		return roleRepository.findByRoleName(rolename);
	}

}
