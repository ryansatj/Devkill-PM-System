const projectController = require("../_controller/projectController");
const express = require("express");
const projectRouter = express.Router();

projectRouter.post("/create/:userId", projectController.createProject);
projectRouter.get("/get", projectController.getProjectbyId);
projectRouter.put("/edit", projectController.editProject);
projectRouter.get("/getbyuser/:userid", projectController.getProjectbyUserId);
projectRouter.post("/addUser/:repository", projectController.addUsertoProject);
projectRouter.get("/getuser/:repository", projectController.getAllProjectuser);
projectRouter.delete("/delete/:repository", projectController.deleteProject);

module.exports = projectRouter;