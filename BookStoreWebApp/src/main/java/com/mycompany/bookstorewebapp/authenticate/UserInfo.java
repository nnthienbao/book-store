/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookstorewebapp.authenticate;

import java.security.Principal;

public class UserInfo implements Principal {

    private String name;
    private String token;
    private String role;

    public UserInfo(String name, String role, String token) {
        this.name = name;
        this.token = token;
        this.role = role;
    }       
    
    @Override
    public String getName() {
        return name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    
}
