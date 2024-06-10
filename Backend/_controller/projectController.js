const Project = require("../_models/project.js");
const User = require("../_models/user.js");
const pool = require("./_pool.js");

createProject = async(req, res) =>{
    const{userId} = req.params
    const{repository, title, descriptions} = req.body
    try{
        const checkRepoExist = await pool.query(
            `SELECT * FROM PROJECTS WHERE repository = $1`,
            [repository]
        );

        if (checkRepoExist.rows.length > 0) {
            const errProject = new Project(-1, "repository exist", -1, "error", "error")
            return res.status(201).json(errProject);
        }

        const result = await pool.query(
            `INSERT INTO PROJECTS (repository, title, descriptions, userId)
            VALUES($1, $2, $3, $4) RETURNING *`,
            [repository, title, descriptions, userId]
        );
        const addtorepo = await pool.query(
            `INSERT INTO Project_members(userid, repository) 
            VALUES($1, $2)`,
            [userId, repository]
        );
        res.status(201).json(result.rows[0]);
    }catch(err){
        console.error(err);
        res.status(500).send(err);
    }
}

editProject = async(req, res) =>{
    const{repository, title, descriptions} = req.body
    try{
        const result = await pool.query(
            `UPDATE Projects SET 
            title = $1, descriptions = $2 WHERE repository = $3 RETURNING *`,
            [title, descriptions, repository]
        );
        res.status(201).json(result.rows[0]);
    }catch(err){
        console.error(err);
        res.status(500).send(err);
    }
}

getProjectbyId = async(req, res) =>{
    const{id} = req.body
    try{
        const result = await pool.query(
            `SELECT * FROM Projects WHERE id = $1`,
            [id]
        );
        res.status(201).json(result.rows[0]);
    }catch(err){
        console.error(err);
        res.status(500).send(err);
    }
}

getProjectbyUserId = async(req, res) =>{
    const{userid} = req.params;
    try{
        const result = await pool.query(
            `SELECT * FROM PROJECTS WHERE userid = $1`,
            [userid]
        );
        res.status(201).json(result.rows);
    } catch(err){
        console.error(err);
        res.status(500).send(err);
    }
}

addUsertoProject = async(req, res) => {
    const{repository} = req.params;
    const{username} = req.body;

    try{
        const findUser = await pool.query(
            `SELECT * FROM Users WHERE username = $1`,
            [username]
        );

        if(findUser.rows.length == 0){
            const user = new User(-2, -2, "Not Found", "Not Found", "error", "error");
            return res.status(201).json(user);
        }
        
        const userExist = await pool.query(
            `SELECT * FROM project_members where userid = $1 AND repository = $2`,
            [findUser.rows[0].id, repository]
        );

        if(userExist.rows.length > 0){
            const user = new User(-1, -1, findUser.rows[0].name, "Already exist", "error", "error");
            return res.status(201).json(user)
        }

        const userid = findUser.rows[0].id

        const result = await pool.query(
            `INSERT INTO Project_members(userid, repository) 
            VALUES($1, $2) RETURNING *`,
            [userid, repository]
        );

        const getProject = await pool.query(
            'SELECT * FROM Projects WHERE repository = $1',
            [repository]
        );

        const insertToUser = await pool.query(
            `INSERT INTO PROJECTS (repository, title, descriptions, userId)
            VALUES($1, $2, $3, $4) RETURNING *`,
            [getProject.rows[0].repository, getProject.rows[0].title, getProject.rows[0].descriptions, findUser.rows[0].id]
        );

        res.status(200).json(findUser.rows[0]);

    } catch(err){
        console.error(err);
        res.status(500).send(err);
    }
}

getAllProjectuser = async(req, res) => {
    const{repository} = req.params;
    try{
        const result = await pool.query(
            `SELECT u._id, u.id, u.name, u.email, u.username FROM PROJECT_MEMBERS p
            INNER JOIN USERS u ON p.userId = u.id
            WHERE p.repository = $1`,
            [repository]
        );
        res.status(200).json(result.rows);
    } catch(err){
        console.error(err);
        res.status(500).send(err);
    }
}

deleteProject = async(req, res) => {
    const{repository} = req.params;
    try{
        const result = await pool.query(
            `DELETE FROM Projects WHERE repository = $1 RETURNING *`,
            [repository]
        );
        const deleteUsers = await pool.query(
            `DELETE FROM Project_members WHERE repository = $1`,
            [repository]
        );
        const deleteSections = await pool.query(
            `DELETE FROM Sections WHERE projectrepo = $1`,
            [repository]
        );
        res.status(200).json(result.rows[0]);
    } catch(err){
        console.error(err);
        res.status(500).send(err);
    }
}

module.exports = {
    createProject,
    editProject,
    getProjectbyId,
    getProjectbyUserId,
    addUsertoProject,
    getAllProjectuser,
    deleteProject,
}