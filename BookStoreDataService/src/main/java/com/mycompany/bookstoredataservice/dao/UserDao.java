/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookstoredataservice.dao;

import com.mycompany.bookstorethriftshare.User;
import com.mycompany.bookstorethriftshare.UserExistedException;
import com.mycompany.bookstorethriftshare.UserNotFoundException;
import com.mycompany.bookstorethriftshare.UserService;
import kyotocabinet.DB;
import org.apache.thrift.TException;

public class UserDao implements UserService.Iface{

    @Override
    public String authenticate(String username, String password) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.        
    }

    @Override
    public boolean add(User newUser) throws TException {
        DB db = FactoryDb.getDBBookStore();
        String keyUserPassword = "user:" + newUser.username + ".password";
        if(db.check(keyUserPassword) != -1) {
            UserExistedException ex = new UserExistedException("Da ton tai user", 100);
            throw ex;
        }
        String keyUserRole = "user:" + newUser.username + ".role";
        db.add(keyUserPassword, newUser.password);
        db.add(keyUserRole, newUser.role);
        
        return true;
    }

    @Override
    public User getUser(String username, String password) throws UserNotFoundException {
        DB db = FactoryDb.getDBBookStore();
        String keyUserPassword = "user:" + username + ".password";
        String valueUserPassword = db.get(keyUserPassword);
        if(valueUserPassword == null || !password.equals(valueUserPassword)) {
            UserNotFoundException ex = new UserNotFoundException("Khong tim thay user", 101);
            throw ex;
        }
        String keyUserRole = "user:" + username + ".role";
        String valueUserRole = db.get(keyUserRole);
        
        return new User(username, password, valueUserRole);
    }
    
}
