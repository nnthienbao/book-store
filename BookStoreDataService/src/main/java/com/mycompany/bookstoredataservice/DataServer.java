/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookstoredataservice;

import com.mycompany.bookstorethriftshare.BookService;
import com.mycompany.bookstorethriftshare.UserService;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;

/**
 *
 * @author cpu02453-local
 */
public class DataServer {
    public static void main(String args[]) throws TTransportException {
        BookHandler bookHandler = new BookHandler();
        UserHandler userHandler = new UserHandler();
        
        TMultiplexedProcessor processor = new TMultiplexedProcessor();        
        processor.registerProcessor("bookService", new BookService.Processor<>(bookHandler));
        processor.registerProcessor("userService", new UserService.Processor<>(userHandler));
        
        TServerTransport serverTransport = new TServerSocket(3000);
        TServer server = new TSimpleServer(new TServer.Args(serverTransport).processor(processor));
        
        System.out.println("Starting the DATA server...");
        server.serve();
    }
}
