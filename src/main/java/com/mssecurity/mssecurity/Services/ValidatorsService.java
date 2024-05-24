package com.mssecurity.mssecurity.Services;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mssecurity.mssecurity.Models.*;
import com.mssecurity.mssecurity.Repositories.PermissionRepository;
import com.mssecurity.mssecurity.Repositories.RolePermissionRepository;
import com.mssecurity.mssecurity.Repositories.StatisticRepository;
import com.mssecurity.mssecurity.Repositories.UserRepository;

@Service
public class ValidatorsService {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private PermissionRepository thePermissionRepository;
    @Autowired
    private UserRepository theUserRepository;
    @Autowired
    private RolePermissionRepository theRolePermissionRepository;
    @Autowired
    private StatisticRepository theStatisticRepository;
    private static final String BEARER_PREFIX = "Bearer ";
    public boolean validationRolePermission(HttpServletRequest request,String url,String method){
        boolean success=false;
        User theUser=this.getUser(request);
        if(theUser!=null){
            Role theRole=theUser.getRole();
            System.out.println("Antes URL "+url+" metodo "+method);
            url = url.replaceAll("[0-9a-fA-F]{24}|\\d+", "?");
            System.out.println("URL "+url+" metodo "+method);
            Permission thePermission=this.thePermissionRepository.getPermission(url,method);

            if(theRole!=null && thePermission!=null){
                Statistic theStatistic=this.theStatisticRepository.getStatisticByPermission(thePermission.get_id());
                if(theStatistic==null){
                    Statistic newStatistic=new Statistic();
                    newStatistic.setPermission(thePermission);
                    this.theStatisticRepository.save(newStatistic);
                }else{
                    theStatistic.setNumberVisits(theStatistic.getNumberVisits()+1);
                    this.theStatisticRepository.save(theStatistic);
                }
                System.out.println("Rol "+theRole.get_id()+ " Permission "+thePermission.get_id());
                RolePermission theRolePermission=this.theRolePermissionRepository.getRolePermission(theRole.get_id(),thePermission.get_id());
                System.out.println("aqui> "+theRolePermission.get_id());
                if (theRolePermission!=null){
                    success=true;
                }
            }else{
                success=false;
            }
        }
        return success;
    }
    public User getUser(final HttpServletRequest request) {
        User theUser=null;
        String authorizationHeader = request.getHeader("Authorization");
        System.out.println("Header "+authorizationHeader);
        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {
            String token = authorizationHeader.substring(BEARER_PREFIX.length());
            System.out.println("Bearer Token: " + token);
            User theUserFromToken=jwtService.getUserFromToken(token);
            if(theUserFromToken!=null) {
                theUser= this.theUserRepository.findById(theUserFromToken.get_id())
                        .orElse(null);
                theUser.setPassword("");
            }
        }
        return theUser;
    }
}
