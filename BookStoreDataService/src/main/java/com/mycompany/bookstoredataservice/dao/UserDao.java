/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookstoredataservice.dao;

import com.mycompany.bookstoredataservice.utils.Utils;
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
    public boolean add(User newUser) throws UserExistedException {
        DB db = FactoryDb.getDBBookStore();
        if(db.check(newUser.getUsername()) != -1) {
            UserExistedException ex = new UserExistedException("Da ton tai user", 100);
            throw ex;
        }
        db.add(newUser.getUsername().getBytes(), Utils.toByte(newUser));
        
        return true;
    }

    @Override
    public User getUser(String username, String password) throws UserNotFoundException {
        DB db = FactoryDb.getDBBookStore();

        byte[] bytes = db.get(username.getBytes());
        if(bytes == null) throw new UserNotFoundException("Khong tim thay user", 101);
        
        return (User) Utils.toObject(bytes);
    }
    
}
