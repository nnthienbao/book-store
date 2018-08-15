/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookstorewebapp.utils;

import java.util.Base64;


/**
 *
 * @author cpu02453-local
 */
public class Utils {
    public static String toBase64(byte[] bytes) {
        return new sun.misc.BASE64Encoder().encode(bytes);
    }
}
