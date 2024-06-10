# DEVKILL PM-SYSTEM

## Overview

Devkill PM system adalah aplikasi berbasis android yang befungsi sebagai controlling untuk pm(Project Manager) untuk mengontrol dan menambahkan task ke user user lain. Fitur yang ditawarkan oleh aplikasi ini ialah, login untuk verifikasi user, Project Management yang bisa mengimplementasikan CRUD pada suatu Project, Project Section yang membuat setiap project dapat memiliki Section/ Task yang bisa dilakukan oleh anggota dalam project. Devkill juga menawarkan fitur live update kepada relasi user yang terjadi, ketika suatu user ditambahkan oleh suatu user maka user tersebut akan langsung menampilkan projectnya, begitu juga dengan section yang ditambahkan didalamnya.

## Backend

Backend pada sistem ini menggunakan express.js sebagai pengendali utama dari program, dengan database yang dideploy pada cloud NeonDB. Backend pada program ini memiliki beberapa fungsi diantaranya yaitu, sebagai controller utama request yang masuk, dan sebagai rute untuk request API yang masuk.

### Program Version

- Express : 4.19.2
- Nodemon : 3.1.0
- Node : 14
- Android Compile SDK: 34
- Min Android API Level : 24
- Min Android ver : 7.0 Nougat
- Retrofit : 2.9.0
- Java version : 8.11

## Frontend

Frontend pada Aplikasi ini terdiri dari beberapa komponen utama yaitu, landing Page, login Page, Signup Page, Home Page, Project Page, Section Page, User Page, dan Page untuk menambah Project maupun session, juga dilengkapi dengan fitur fitur untuk menghapus ataupun mengedit suatu objek.

## Instalation

### Prerequisites

- Node.js (version 14) for Server
- Android Version 7 or higher

### Steps

1. Fork the repository.
2. Install prequisites on Server (npm install).
3. Run the server (npm run start).
4. Run the client side (Android)

## Author

Ryan Safa Tjendana (Undergraduate Student in Computer Engineering Universitas Indonesia)

## Acknowledgements
- Express.js
- NeonDB
- Retrofit