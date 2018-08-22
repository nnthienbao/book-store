namespace java com.mycompany.bookstorethriftshare

struct Book {
    1:string id;
    2:string name;
    3:string kind;
    4:string author;
    5:i32 price;
    6:string image;
    7:string extImage
}

struct ResultQueryBook {
	1:i64 total;
	2:list<Book> listBooks
}

exception BookNotFoundException {
    1:string message
}

exception PermissionDeniedException {
    1:string message,
    2:i32 errorCode
}

exception SearchNotFoundException {
	1:string message,
	2:i32 errorCode
}

service BookService
{
    ResultQueryBook getList(1:i32 page, 2:i32 limit),
	ResultQueryBook getBookByKind(1:string kind, 2:i32 page, 3:i32 limit),
	list<string> getListKinds(),
    Book findById(1:string id),
    bool add(1:Book newBook, 2:string token) throws (1:PermissionDeniedException ex),
    bool update(1:Book updateBook, 2:string token) throws (1:PermissionDeniedException ex),
    bool remove(1:string idBook, 2:string token) throws (1:PermissionDeniedException ex),
	list<Book> searchByKeyword(1:string keyword) throws (1:SearchNotFoundException ex)
}
