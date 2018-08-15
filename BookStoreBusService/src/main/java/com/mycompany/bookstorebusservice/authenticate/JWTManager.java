/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookstorebusservice.authenticate;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mycompany.bookstorethriftshare.User;

public class JWTManager {
    private static final String SECRET = "AAAAAAAAAAA";
    private static final Algorithm algorithm = Algorithm.HMAC256(SECRET);
    
    public static String sign(User user) {
        String token = JWT.create()
                .withClaim("username", user.getUsername())
                .withClaim("role", user.getRole())
                .sign(algorithm);
        return token;
    }
    
    public static boolean verify(String token) {
        return true;
    }
    
    public static DecodedJWT decode(String token) {
        return JWT.decode(token);
    }
}
