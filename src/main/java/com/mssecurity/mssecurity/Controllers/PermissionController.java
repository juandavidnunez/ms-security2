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

import com.mssecurity.mssecurity.Models.Permission;
import com.mssecurity.mssecurity.Repositories.PermissionRepository;





@CrossOrigin
@RestController
@RequestMapping("/permissions")
public class PermissionController {
    @Autowired
    private PermissionRepository thePermissionRepository;
    @GetMapping("")
    public List<Permission> index(){
        return this.thePermissionRepository.findAll();
    }
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping
    public Permission store(@RequestBody Permission newPermission){
      return this.thePermissionRepository.save(newPermission);
    }
    @GetMapping("{id}")
    public Permission findById(@PathVariable String id) {
        Permission thePermission = this.thePermissionRepository
                .findById(id)
                .orElse(null);
        return thePermission;
    }
    
    @PutMapping("{id}")
    public Permission update(@PathVariable String id, @RequestBody Permission theNewPermission) {
        Permission theActualPermission = this.thePermissionRepository
                .findById(id)
                .orElse(null);
        if (theActualPermission != null) {
       
            return this.thePermissionRepository.save(theActualPermission);
        } else {
            return null;
        }
    }
    
    
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        Permission thePermission = this.thePermissionRepository
                .findById(id)
                .orElse(null);
        if (thePermission != null) {
            this.thePermissionRepository.delete(thePermission);
        }
    }
}
