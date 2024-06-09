const roles = {
    ADMIN : 'admin',
    PROJECT_MANAGER : 'pm',
    MEMBER : 'member',
    VIEW_ONLY : 'viewer'
};

class Role{
    constructor(id, userId, userRole){
        this.id = id;
        this.userId = userId;
        this.role = roles[userRole];
    }
}

module.exports = Role;