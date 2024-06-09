class Section{
    constructor(id, title, description, deadline, resources, alerts){
        this.id = id
        this.title = title
        this.description = description
        this.deadline = deadline
        this.resources = resources
        this.alerts = alerts
    }
}

module.exports = Section;