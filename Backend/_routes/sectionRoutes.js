const sectionController = require("../_controller/sectionController");
const express = require("express");
const sectionRouter = express.Router();

sectionRouter.post("/basesection", sectionController.createBaseSection);
sectionRouter.put("/basesection", sectionController.editBaseSection);
sectionRouter.post("/create/:projectrepo", sectionController.createSection);
sectionRouter.get("/getall/:projectrepo", sectionController.getProjectSection);
sectionRouter.post("/delete/:projectrepo", sectionController.sectionDelete)

module.exports = sectionRouter