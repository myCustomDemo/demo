CREATE TABLE users(
    id int,
    data blob,
    PRIMARY KEY(id, data)
);

INSERT INTO users (id, data) values (123, 0x4a6f686e);
INSERT INTO users (id, data) values (567, 0x416e6e61);
