package com.mssecurity.mssecurity.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mssecurity.mssecurity.Models.User;
import com.mssecurity.mssecurity.Repositories.UserRepository;
import com.mssecurity.mssecurity.Services.EncryptionService;
import com.mssecurity.mssecurity.Services.JwtService;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
@RequestMapping("/api/public/security")

public class SecurityController {
    @Autowired
    private UserRepository thUserRepository;
    @Autowired
private JwtService jwtService; 
@Autowired
private EncryptionService encryptionService;
@PostMapping("login")
public String login(@RequestBody User theUser, final HttpServletResponse response) throws IOException{
    String token="";
    User actualUser=this.thUserRepository.getUserByEmail(theUser.getEmail());
    if(actualUser!=null && actualUser.getPassword().equals(encryptionService.convertSHA256(theUser.getPassword()))){
        token=jwtService.generateToken(actualUser);
    }else{
        try {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        } catch (java.io.IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    return token;
}

}
