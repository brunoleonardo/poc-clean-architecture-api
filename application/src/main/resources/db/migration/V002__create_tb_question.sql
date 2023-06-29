CREATE TABLE IF NOT EXISTS question
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    title       VARCHAR(80) NOT NULL,
    description MEDIUMTEXT  NOT NULL,
    user_id     INT         NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id)
);