/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookstorewebapp.authenticate;

import com.mycompany.bookstorewebapp.client.ClientFactory;
import com.mycompany.bookstorewebapp.utils.JWTManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class CustomAuthenticationProvider implements AuthenticationProvider {
    
    private ClientFactory clientFactory;

    public ClientFactory getClientFactory() {
        return clientFactory;
    }

    public void setClientFactory(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();

        String password = authentication.getCredentials().toString();         
        try {
            String token = clientFactory.getUserClient().authenticate(username, password);
            System.out.println("Token: " + token);
            String role = JWTManager.decode(token).getClaim("role").asString();
            if(token != null) {
                UserInfo userInfo = new UserInfo(username, role, token);
                Authentication auth = new UsernamePasswordAuthenticationToken(userInfo, authentication.getCredentials(), buildAuthority(role));              
                return auth;
            }
        } catch (TException ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UsernamePasswordAuthenticationToken.class);
    }
    
    private List<GrantedAuthority> buildAuthority(String role) {
        Set<GrantedAuthority> setAuths = new HashSet<>();
        setAuths.add(new SimpleGrantedAuthority(role));
        List<GrantedAuthority> result = new ArrayList<>(setAuths);

        return result;
    }
    
}
