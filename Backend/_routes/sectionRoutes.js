const sectionController = require("../_controller/sectionController");
const express = require("express");
const sectionRouter = express.Router();

sectionRouter.post("/create/:projectrepo", sectionController.createSection);
sectionRouter.get("/getall/:projectrepo", sectionController.getProjectSection);
sectionRouter.post("/delete/:projectrepo", sectionController.sectionDelete)
sectionRouter.put("/edit/:projectrepo", sectionController.editSection);

module.exports = sectionRouter