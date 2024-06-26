# DEVKILL PM-SYSTEM

## Overview

Devkill PM system adalah aplikasi berbasis android yang befungsi sebagai controlling untuk pm(Project Manager) untuk mengontrol dan menambahkan task ke user user lain. Fitur yang ditawarkan oleh aplikasi ini ialah, login untuk verifikasi user, Project Management yang bisa mengimplementasikan CRUD pada suatu Project, Project Section yang membuat setiap project dapat memiliki Section/ Task yang bisa dilakukan oleh anggota dalam project. Devkill juga menawarkan fitur live update kepada relasi user yang terjadi, ketika suatu user ditambahkan oleh suatu user maka user tersebut akan langsung menampilkan projectnya, begitu juga dengan section yang ditambahkan didalamnya.

## Backend

Backend pada sistem ini menggunakan express.js sebagai pengendali utama dari program, dengan database yang dideploy pada cloud NeonDB. Backend pada program ini memiliki beberapa fungsi diantaranya yaitu, sebagai controller utama request yang masuk, dan sebagai rute untuk request API yang masuk.

#### Deployed Backend server : https://devkill-pm-system.vercel.app/

##### Deployed Backend Request Example:

![image](https://github.com/ryansatj/Devkill-PM-System/assets/134668057/12c5bd52-3248-46ea-b592-11e0045b313e)

##### Postman:
![image](https://github.com/ryansatj/Devkill-PM-System/assets/134668057/037bf18b-9eca-4471-8cee-caa904a7c7a9)

### Program Version

- Express : 4.19.2
- Nodemon : 3.1.0
- Node : 14
- Android Compile SDK: 34
- Min Android API Level : 24
- Min Android ver : 7.0 Nougat
- Retrofit : 2.9.0
- Java version : 8.11

### API Documentation

#### Main routes
- /project
- /account
- /section

##### Sub Routes

/project:
- /create/:userid : create Project
- /get : get all projects
- /edit : edit a project
- /getbyuser/:userid : get project by userid
- /addUser/:repository : add project members to project
- /getuser/:repository : get all project members
- /delete/:repository : delete a project

/account: 
- /signup : create an account
- /login : verificate an account
- /find/:username : find user by username
- /update/:id : update/edit an account

/section:
- /create/:projectrepo : make a section on a project
- /getall/:projectrepo : get all section on a project
- /delete/:projectrepo : delete a section
- /edit/:projectrepo : edit a section


### Database Relation
![DevKill Diagram](https://github.com/ryansatj/Devkill-PM-System/assets/134668057/60e025ec-b387-4f2f-ae38-a24f66552bb0)


## Frontend

Frontend pada Aplikasi ini terdiri dari beberapa komponen utama yaitu, landing Page, login Page, Signup Page, Home Page, Project Page, Section Page, User Page, dan Page untuk menambah Project maupun session, juga dilengkapi dengan fitur fitur untuk menghapus ataupun mengedit suatu objek.

### Frontend Flowchart
![Flowcharts (1)](https://github.com/ryansatj/Devkill-PM-System/assets/134668057/0083ef0e-de0b-4676-b469-9dc11e0c3b00)


## Instalation

### Prerequisites

- Android Version 7 or higher

### Steps

1. Install the client side App (Android)
2. Enjoy my app

## Author

Ryan Safa Tjendana (Undergraduate Student in Computer Engineering Universitas Indonesia)

## Acknowledgements
- Express.js
- NeonDB
- Retrofit
