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
    User getUserByNameAndPassword(1:string username, 2:string password),
    bool add(1:User newUser)
}
