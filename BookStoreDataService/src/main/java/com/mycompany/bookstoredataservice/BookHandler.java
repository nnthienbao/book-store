/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookstoredataservice;

import com.mycompany.bookstoredataservice.dao.BookDao;
import com.mycompany.bookstorethriftshare.Book;
import com.mycompany.bookstorethriftshare.BookService;
import com.mycompany.bookstorethriftshare.ResultQueryBook;
import com.mycompany.bookstorethriftshare.SearchNotFoundException;
import java.util.List;
import org.apache.thrift.TException;

/**
 *
 * @author cpu02453-local
 */
public class BookHandler implements BookService.Iface{
    
    private final BookDao bookDao = new BookDao();
    
    @Override
    public ResultQueryBook getList(int page, int limit) throws TException {
        return bookDao.getList(page, limit);
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

	@Override
	public List<Book> searchByKeyword(String keyword) throws TException, SearchNotFoundException {
		try {
			List<Book> results = bookDao.searchByKeyword(keyword);
			if(results.isEmpty())
				throw new SearchNotFoundException("Khong tim thay ket qua nao", 400);
			return results;
		} catch (Exception ex) {
			throw new SearchNotFoundException("Khong tim thay ket qua nao", 400);
		}
	}

	@Override
	public ResultQueryBook getBookByKind(String kind, int page, int limit) throws TException {
		return bookDao.getBookByKind(kind, page, limit);
	}

	@Override
	public List<String> getListKinds() throws TException {
		return bookDao.getListKinds();
	}
}
