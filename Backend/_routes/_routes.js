const express = require("express");
const projectRouter = require("./projectRoutes.js");
const userRouter = require("./userRoute.js");
const sectionRouter = require("./sectionRoutes.js");

const router = express.Router();

router.use("/project", projectRouter);
router.use("/account", userRouter);
router.use("/section", sectionRouter);

module.exports = router;
