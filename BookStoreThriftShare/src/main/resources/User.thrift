namespace java com.mycompany.bookstorethriftshare

struct User {
    1:string username;
    2:string password;
    3:string role
}

exception UserExistedException {
    1:string message,
    2:i32 errorCode
}

exception UserNotFoundException {
    1:string message,
    2:i32 errorCode
}

service UserService
{
    string authenticate(1:string username, 2:string password),
    User getUser(1:string username, 2:string password) throws (1:UserNotFoundException ex),
    bool add(1:User newUser)

}
