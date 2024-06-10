const userController = require("../_controller/userController.js");
const express = require("express");
const userRouter = express.Router();

userRouter.post("/signup", userController.signUp);
userRouter.post("/login", userController.login);
userRouter.get("/find/:username", userController.findUserbyUsername);
userRouter.put("/update/:id", userController.updateUser);

module.exports = userRouter;