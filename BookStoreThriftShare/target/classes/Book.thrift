namespace java com.mycompany.bookstorethriftshare

struct Book {
    1:string id;
    2:string name;
    3:string kind;
    4:string author;
    5:i32 price
}

exception BookNotFoundException {
    1:string message
}

exception PermissionDeniedException {
    1:string message,
    2:i32 errorCode
}

service BookService
{
    list<Book> getList(),
    Book findById(1:string id),
    bool add(1:Book newBook, 2:string token) throws (1:PermissionDeniedException ex),
    bool update(1:Book updateBook, 2:string token) throws (1:PermissionDeniedException ex),
    bool remove(1:string idBook, 2:string token) throws (1:PermissionDeniedException ex)
}
