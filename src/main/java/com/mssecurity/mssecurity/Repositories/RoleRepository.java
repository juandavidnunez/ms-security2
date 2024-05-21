package com.mssecurity.mssecurity.Repositories;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.mssecurity.mssecurity.Models.Role;

public interface RoleRepository extends MongoRepository<Role,String> {
    

    Role save(Role newRole);
}

