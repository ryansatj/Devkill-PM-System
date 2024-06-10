const User = require('../_models/user.js');
const pool = require("./_pool.js");
const crypto = require("crypto");

signUp = async(req, res) =>{
    const{name, email, password, username} = req.body;
    try{
        const emailIfExist = await pool.query(
            `SELECT * FROM USERS WHERE email = $1`,
            [email]
        );

        const usernameIfExist = await pool.query(
            `SELECT * FROM USERS WHERE username = $1`,
            [username]
        );
        if(emailIfExist.rows.length != 0){
           const user = new User(-1, -1, "error", "error", "error", "error");
            return res.status(200).json(user);

        } else if(usernameIfExist.rows.length != 0){
            const user = new User(-2, -2, "error", "error", "error", "error");
            return res.status(200).json(user);
        }

        const result = await pool.query(
            `INSERT INTO USERS (name, email, password, username)
            VALUES($1, $2, $3, $4) RETURNING *`,
            [name, email, md5Hash(password), username]
        );
        res.status(201).json(result.rows[0]);
    } catch(err){
        console.error(err);
        res.status(500).send(err);
        
    }
}

login = async(req, res) =>{
    const{email, password} = req.body;
    try{
        const result = await pool.query(
            `SELECT * FROM USERS WHERE email = $1 AND password = $2`,
            [email, md5Hash(password)]
        );
        if (result.rows.length === 0) {
            const user = new User(0, 0, "false", "false", "false", "false")
            return res.status(200).json(user);
        }

        const user = new User(result.rows[0]);
        res.cookie('usersession', JSON.stringify(user), { maxAge: 60000 }); // 1 minute = 60,000 milliseconds
        res.status(201).json(result.rows[0]);
        
    }catch(err){
        console.error(err);
        res.status(500).json("false");
    }
}

findUserbyUsername = async(req, res) =>{
    const{username} = req.params;
    try{
        const result = await pool.query(
            `SELECT * FROM USERS WHERE username = $1`,
            [username]
        );
        res.status(201).json(result.rows[0]);
    }catch(err){
        console.error(err);
        res.status(500).json(err);
    }
}

findUserbyId = async(req, res) =>{
    const{id} = req.body;
    try{
        const result = await pool.query(
            `SELECT * FROM USERS WHERE id = $1`,
            [id]
        );
        res.status(201).json(result.rows[0]);
    }catch(err){
        console.error(err);
        res.status(500).json(err);
    }
}

updateUser = async(req, res) =>{
    const{id} = req.params;
    const{name, username, email} = req.body;
    try{
        const checkUser = await pool.query(
            `SELECT * FROM USERS WHERE _id = $1`,
            [id]
        );
        if(checkUser.rows[0].username != username){
            const check = await pool.query(
                `SELECT * FROM USERS WHERE username = $1`,
                [username]
            );
            if(check.rows.length > 0){
                const user = new User(-1,-1, "Username Already used", null, null, null);
                return res.status(201).json(user);
            }
        }
        if(checkUser.rows[0].email != email){
            const checkEmail = await pool.query(
                `SELECT * FROM USERS WHERE email = $1`,
                [email]
            );
            if(checkEmail.rows.length > 0){
                const user = new User(-2,-2, "Email already Used", null, null, null);
                return res.status(201).json(user);
            }
        }

        const result = await pool.query(
            `UPDATE USERS SET username = $1, email = $2, name = $3 WHERE id = $4 RETURNING *`,
            [username, email, name, id]
        );
        res.status(201).json(result.rows[0]);
    }catch(err){
        console.error(err);
        res.status(500).json(err);
    }
}

function md5Hash(text) {
    return crypto.createHash('md5').update(text).digest('hex');
    }

module.exports = {
    signUp,
    login,
    findUserbyUsername,
    findUserbyId,
    updateUser
};