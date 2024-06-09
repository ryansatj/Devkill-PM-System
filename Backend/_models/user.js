class User{
    constructor(_id, id, name, email, password, username){
        this._id = _id
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.username = username;
    }
}

module.exports = User;