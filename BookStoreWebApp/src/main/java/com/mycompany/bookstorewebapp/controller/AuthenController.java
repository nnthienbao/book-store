/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookstorewebapp.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.mycompany.bookstorethriftshare.User;
import com.mycompany.bookstorewebapp.client.ClientFactory;
import com.mycompany.bookstorewebapp.utils.JWTManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthenController {
    
    @Autowired
    private ClientFactory clientFactory;
    
    @RequestMapping("/login")
    public String getLogin() {
        return "login";
    }
    
    @PostMapping("/login")
    public String postLogin(@RequestParam("username") String username, 
            @RequestParam("password") String password,
            HttpServletRequest request) {
        try {
            String token = clientFactory.getUserClient().authenticate(username, password);
            DecodedJWT decode = JWTManager.decode(token);
            String role = decode.getClaim("role").asString();
            request.getSession().setAttribute("username", username);
            request.getSession().setAttribute("role", role);
            request.getSession().setAttribute("token", token);
            return "redirect:/admin";
        } catch (TException ex) {
            Logger.getLogger(AuthenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "redirect:/login";
    }
}
