DROP TABLE Section_members;
DROP TABLE Project_members;
DROP TABLE Sections;
DROP TABLE Projects;
DROP TABLE Users;

CREATE TABLE Users (
  _id SERIAL PRIMARY KEY,
  id SERIAL UNIQUE NOT NULL,
  name varchar NOT NULL,
  email varchar UNIQUE NOT NULL,
  password varchar NOT NULL,
  username varchar UNIQUE NOT NULL
);

CREATE TABLE Projects (
  id SERIAL NOT NULL,
  repository varchar NOT NULL,
  userId INT NOT NULL,
  title varchar(50) NOT NULL,
  descriptions text,
  PRIMARY KEY(id),
  FOREIGN KEY (userId) REFERENCES Users(id)
);

CREATE TABLE Sections (
  id SERIAL,
  projectrepo varchar,
  title varchar NOT NULL,
  description text,
  deadline date,
  resources text, 
  alerts varchar(50),
  PRIMARY KEY (id)
);

CREATE TABLE Project_members (
  id SERIAL PRIMARY KEY,
  userId INT NOT NULL,
  repository varchar NOT NULL,
  FOREIGN KEY (userId) REFERENCES Users(_id)
);

CREATE TABLE Section_members (
  section_member_id SERIAL PRIMARY KEY,
  userId INT NOT NULL,
  projectRepo varchar NOT NULL,
  sectionTitle varchar NOT NULL,
  FOREIGN KEY (userId) REFERENCES Users(id)
);

