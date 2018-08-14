/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookstorewebapp.client;

import com.mycompany.bookstorethriftshare.Book;
import com.mycompany.bookstorethriftshare.BookService;
import java.util.List;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class BookClient implements BookService.Iface {

    private final TMultiplexedProtocol mulProtocol;
    private final BookService.Client client;
    
    public BookClient(TProtocol generalProtocol) {
        mulProtocol = new TMultiplexedProtocol(generalProtocol, "bookService");
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
    public boolean add(Book newBook, String token) throws TException {
        return client.add(newBook, token);
    }

    @Override
    public boolean update(Book updateBook, String token) throws TException {
        return client.update(updateBook, token);
    }

    @Override
    public boolean remove(String idBook, String token) throws TException {
        return client.remove(idBook, token);
    }
    
}
