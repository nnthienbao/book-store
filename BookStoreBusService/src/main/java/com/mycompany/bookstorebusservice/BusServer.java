/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookstorebusservice;

import com.mycompany.bookstorethriftshare.BookService;
import com.mycompany.bookstorethriftshare.UserService;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 *
 * @author cpu02453-local
 */
public class BusServer {
    public static void main(String args[]) throws TTransportException {
        TTransport clienTransport = new TSocket("localhost", 3000);
        clienTransport.open();
            
        TProtocol clientProtocol = new  TBinaryProtocol(clienTransport);
        
        BookHandler bookHandler = new BookHandler(clientProtocol);
        UserHandler userHandler = new UserHandler(clientProtocol);
        
        TMultiplexedProcessor processor = new TMultiplexedProcessor();
        processor.registerProcessor("bookService", new BookService.Processor<>(bookHandler));
        processor.registerProcessor("userService", new UserService.Processor<>(userHandler));
        
        TServerTransport serverTransport = new TServerSocket(3001);
        TServer server = new TSimpleServer(new TServer.Args(serverTransport).processor(processor));
        
        System.out.println("Starting the BUS server...");
        server.serve();
    }
}
