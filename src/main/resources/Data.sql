

CREATE TABLE user (
                id INT NOT NULL AUTO_INCREMENT,
                firstname VARCHAR(50) NOT NULL,
                lastname VARCHAR(50) NOT NULL,
                email VARCHAR(50) NOT NULL UNIQUE,
                password VARCHAR(50) NOT NULL,
                pseudo VARCHAR(20) NOT NULL,
                PRIMARY KEY (id)
);


CREATE INDEX user_idx
 ON user
 ( id );

CREATE UNIQUE INDEX user_email
 ON user
 ( email );

