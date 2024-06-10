const Section = require("../_models/section.js");
const pool = require("./_pool.js");

createSection = async(req, res) =>{
    const{projectrepo} = req.params;
    const{title, description, deadline, resources, alerts} = req.body
    try{
        const titleExist = await pool.query(
            `SELECT * FROM Sections WHERE title = $1`,
            [title]
        );
        if(titleExist.rows.length > 0){
            const section = new Section(-1, "error", "error", "error" , "error", "error",);
            return res.status(200).send(section);
        }

        const result = await pool.query(
            `INSERT INTO SECTIONS (projectrepo, title, description, deadline, resources, alerts)
            VALUES($1, $2, $3, $4, $5, $6) RETURNING *`,
            [projectrepo, title, description, deadline, resources, alerts]
        )
        res.status(201).json(result.rows[0]);
    } catch(err){
        console.error(err);
        res.status(500).send(err);
    }
}

getProjectSection = async(req, res) =>{
    const{projectrepo} = req.params;
    try{
        const result = await pool.query(
            `SELECT * FROM Sections WHERE projectrepo = $1`,
            [projectrepo]
        );
        res.status(201).json(result.rows);
    } catch(err){
        console.error(err);
        res.status(500).send(err);
    }
}

addUsertoSection = async(req, res) =>{
    const{sectionid} = req.params;
    const{username, userid} = req.body
    try{
        const findUserbyUsername = await pool.query(
            `SELECT * FROM USERS WHERE username = $1`,
            [username]
        );
        const existoOnProject = await pool.query(
            `SELECT * FROM project_members WHERE userid = $1 AND repository = $2`,
            [findUserbyUsername.id, repository]
        );
        if(existoOnProject.rows.length == 0){
            const section = new Section(-1, "error", "error", "error" , "error", "error",);
            res.status(201).json(section);
        }

        const getUserProjectId = await pool.query(
            `SELECT id from PROJECTS where repository = $1 and userid = $2`,
            [repository, findUserbyUsername]
        );
        getUserSection
        const addSectiontoUserProject = await pool.query(
            `INSERT INTO SECTIONS (projectid, title, description, deadline, resources, alerts)
            VALUES($1, $2, $3, $4, $5, $6) RETURNING *`
        );
    } catch(err){
        console.error(err);
        res.status(500).send(err);
    }
}

sectionDelete = async(req, res) => {
    const{projectrepo} = req.params;
    const{title} = req.body;
    try{
        const result = await pool.query(
            `DELETE FROM Sections WHERE projectrepo = $1 AND title = $2 RETURNING *`,
            [projectrepo, title]
        );
        res.status(200).send(result.rows[0]);
    } catch(err){
        console.error(err);
        res.status(500).send(err);
    }
}

module.exports = {
    createSection,
    getProjectSection,
    sectionDelete,
}