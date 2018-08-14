/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookstoredataservice;

import com.mycompany.bookstoredataservice.dao.UserDao;
import com.mycompany.bookstorethriftshare.User;
import com.mycompany.bookstorethriftshare.UserService;
import org.apache.thrift.TException;

public class UserHandler implements UserService.Iface{

    private final UserDao userDao = new UserDao();
    
    @Override
    public String authenticate(String username, String password) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean add(User newUser) throws TException {
        return userDao.add(newUser);
    }

    @Override
    public User getUser(String username, String password) throws TException {
        return userDao.getUser(username, password);
    }
    
}
