INSERT INTO roles(name) VALUES('ROLE_ADMIN');
INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO users(email, name, password, username) VALUES('admin@fakemail.com','Admin','$2a$10$bD.kXoZRQPhUvk.V0qy2aOtR74VYTx8/hoUWXkp.lHZgdkFdfAot2','admin');
INSERT INTO user_roles(user_id, role_id) VALUES(1,1);
