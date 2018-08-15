/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookstorebusservice;

import com.mycompany.bookstorebusservice.authenticate.JWTManager;
import com.mycompany.bookstorethriftshare.Book;
import com.mycompany.bookstorethriftshare.BookService;
import com.mycompany.bookstorethriftshare.PermissionDeniedException;
import java.util.List;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;

/**
 *
 * @author cpu02453-local
 */
public class BookHandler implements BookService.Iface{
    
    private TMultiplexedProtocol mulProtocol;
    private final BookService.Client client;
    
    public BookHandler(TProtocol generalProtocol) {
        mulProtocol = new  TMultiplexedProtocol(generalProtocol, "bookService");
        client = new BookService.Client(mulProtocol);
    }

    @Override
    public List<Book> getList() throws TException {
        return client.getList();
    }     

    @Override
    public Book findById(String id) throws TException {
        return client.findById(id);
    }

    @Override
    public boolean add(Book newBook, String token) throws PermissionDeniedException, TException {
        if(!requiredAdmin(token)) throw new PermissionDeniedException("Khong co quyen truy cap", 403);
        
        return client.add(newBook, token);
    }

    @Override
    public boolean update(Book updateBook, String token) throws PermissionDeniedException, TException {
        if(!requiredAdmin(token)) throw new PermissionDeniedException("Khong co quyen truy cap", 403);
        
        return client.update(updateBook, token);
    }

    @Override
    public boolean remove(String idBook, String token) throws PermissionDeniedException, TException {
        if(!requiredAdmin(token)) throw new PermissionDeniedException("Khong co quyen truy cap", 403);
        
        return client.remove(idBook, token);
    }
    
    private boolean requiredAdmin(String token) {
        if(!JWTManager.verify(token)) return false;
        String role = JWTManager.decode(token).getClaim("role").asString();
        if(!role.equals("ROLE_ADMIN")) return false;
        return true;
    }
}
