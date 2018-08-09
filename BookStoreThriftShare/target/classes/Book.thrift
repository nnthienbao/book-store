namespace java com.mycompany.bookstorethriftshare

struct Book {
    1:string id;
    2:string name;
    3:string author
}

exception BookNotFoundException {
    1:string message
}

service BookService
{
    list<Book> getList(),
    Book findById(1:string id),
    bool add(1:Book newBook),
    bool update(1:Book updateBook),
    bool remove(1:string idBook)
}
