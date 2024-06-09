class Project{
    constructor(id, repository, userid, title, description){
        this.id = id
        this.repository = repository
        this.userid = userid
        this.title = title
        this.description = description
    }

    addSection(section){
        this.sections.push(section);
    }
}

module.exports = Project;