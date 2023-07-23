package com.spring.data.domain.service;

import com.spring.data.domain.exception.PermissionNotFoundException;
import com.spring.data.domain.model.Permission;
import com.spring.data.domain.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionRegisterService {

    @Autowired
    private PermissionRepository permissionRepository;

    public Permission searchOrFail(Long permissionId) {
        return permissionRepository.findById(permissionId)
                .orElseThrow(() -> new PermissionNotFoundException(permissionId));
    }
}
