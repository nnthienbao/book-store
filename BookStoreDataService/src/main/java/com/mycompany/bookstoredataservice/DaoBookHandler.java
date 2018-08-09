/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookstoredataservice;

import com.mycompany.bookstoredataservice.dao.BookDao;
import com.mycompany.bookstorethriftshare.Book;
import com.mycompany.bookstorethriftshare.BookNotFoundException;
import com.mycompany.bookstorethriftshare.BookService;
import java.util.List;
import org.apache.thrift.TException;

/**
 *
 * @author cpu02453-local
 */
public class DaoBookHandler implements BookService.Iface{
    
    private final BookDao bookDao = new BookDao();
    
    @Override
    public List<Book> getList() throws TException {
        return bookDao.getList();
    }

    @Override
    public Book findById(String id) throws BookNotFoundException {
        return bookDao.findById(id);
    }

    @Override
    public boolean add(Book newBook) throws TException{
        return bookDao.add(newBook);
    }

    @Override
    public boolean update(Book updateBook) throws TException {
        return bookDao.update(updateBook);
    }

    @Override
    public boolean remove(String idBook) throws TException {
        return bookDao.remove(idBook);
    }
}
