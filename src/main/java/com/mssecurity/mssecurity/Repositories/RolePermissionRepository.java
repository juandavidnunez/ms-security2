package com.mssecurity.mssecurity.Repositories;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.mssecurity.mssecurity.Models.RolePermission;

import java.util.List;

public interface RolePermissionRepository  extends MongoRepository<RolePermission,String> {

    @Query("{'role.$id': ObjectId(?0)}")
    List<RolePermission> getPermissionsByRole(String roleId);

    @Query("{'permission.$id': ObjectId(?0)}")
    List<RolePermission> getRolesByPermission(String permissionId);

    @Query("{'role.$id': ObjectId(?0), 'permission.$id': ObjectId(?1)}")
    RolePermission getRolePermission(String roleId, String permissionId);
}

