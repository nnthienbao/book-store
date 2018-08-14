/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookstorebusservice;

import com.mycompany.bookstorethriftshare.User;
import com.mycompany.bookstorethriftshare.UserService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;

public class UserHandler implements UserService.Iface{

    private final TMultiplexedProtocol mulProtocol;
    private final UserService.Client client;
    
    public UserHandler(TProtocol generalProtocol) {
        mulProtocol = new  TMultiplexedProtocol(generalProtocol, "userService");
        client = new UserService.Client(mulProtocol);
    }
    
    @Override
    public User getUserByNameAndPassword(String username, String password) throws TException {
        return client.getUserByNameAndPassword(username, password);
    }

    @Override
    public boolean add(User newUser) throws TException {
        return client.add(newUser);
    }
    
}
