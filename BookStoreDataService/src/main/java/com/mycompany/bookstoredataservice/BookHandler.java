/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookstoredataservice;

import com.mycompany.bookstoredataservice.dao.BookDaoV2;
import com.mycompany.bookstorethriftshare.Book;
import com.mycompany.bookstorethriftshare.BookService;
import java.util.List;
import org.apache.thrift.TException;

/**
 *
 * @author cpu02453-local
 */
public class BookHandler implements BookService.Iface{
    
    private final BookDaoV2 bookDao = new BookDaoV2();
    
    @Override
    public List<Book> getList() throws TException {
        return bookDao.getList();
    }

    @Override
    public Book findById(String id) throws TException {
        return bookDao.findById(id);
    }

    @Override
    public boolean add(Book newBook, String token) throws TException{
        return bookDao.add(newBook, token);
    }

    @Override
    public boolean update(Book updateBook, String token) throws TException {
        return bookDao.update(updateBook, token);
    }

    @Override
    public boolean remove(String idBook, String token) throws TException {
        return bookDao.remove(idBook, token);
    }
}
