package com.mssecurity.mssecurity.Controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mssecurity.mssecurity.Models.Role;
import com.mssecurity.mssecurity.Models.User;
import com.mssecurity.mssecurity.Repositories.RoleRepository;
import com.mssecurity.mssecurity.Repositories.UserRepository;
import com.mssecurity.mssecurity.Services.EncryptionService;

@CrossOrigin
@RestController
@RequestMapping("/api/users")

public class UserController {
@Autowired
private UserRepository theUserRepository;
@Autowired
private RoleRepository theRoleRepository;
@Autowired
private EncryptionService encryptionService;

@GetMapping("")
public List<User> index(){
    return this.theUserRepository.findAll();
}
@ResponseStatus(HttpStatus.CREATED)
@PostMapping
public User store(@RequestBody User newUser){
    newUser.setPassword(encryptionService.convertSHA256(newUser.getPassword()));
  return this.theUserRepository.save(newUser);
}
@GetMapping("{id}")
public User findById(@PathVariable String id) {
    User theUser = this.theUserRepository
            .findById(id)
            .orElse(null);
    return theUser;
}

@PutMapping("{id}")
public User update(@PathVariable String id, @RequestBody User theNewUser) {
    User theActualUser = this.theUserRepository
            .findById(id)
            .orElse(null);
    if (theActualUser != null) {
        theActualUser.setName(theNewUser.getName());
        theActualUser.setEmail(theNewUser.getEmail());
        theActualUser.setPassword(theNewUser.getPassword());
        return this.theUserRepository.save(theActualUser);
    } else {
        return null;
    }
}


@ResponseStatus(HttpStatus.NO_CONTENT)
@DeleteMapping("{id}")
public void delete(@PathVariable String id) {
    User theUser = this.theUserRepository
            .findById(id)
            .orElse(null);
    if (theUser != null) {
        this.theUserRepository.delete(theUser);
    }
}

@PutMapping("{user_id}/role/{role_id}")
public User matchUserRole(@PathVariable String user_id,@PathVariable String role_id){
    User theActualUser = this.theUserRepository
    .findById(user_id)
    .orElse(null);
    Role theActualRole=this.theRoleRepository
    .findById(role_id)
    .orElse(null);
    if(theActualUser!=null&&theActualRole!=null){
        theActualUser.setRole(theActualRole);
    
    return this.theUserRepository.save(theActualUser);
    }
    else{
        return null;
    }
}

@PutMapping("{user_id}/role")
public User unMatchUserRole(@PathVariable String user_id){
    User theActualUser = this.theUserRepository
    .findById(user_id)
    .orElse(null);
   
    
    if(theActualUser!=null){
        theActualUser.setRole(null);
    
    return this.theUserRepository.save(theActualUser);
    }
    else{
        return null;
    }
}

}
