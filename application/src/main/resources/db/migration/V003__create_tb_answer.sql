CREATE TABLE IF NOT EXISTS answer
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    description MEDIUMTEXT NOT NULL,
    question_id INT        NOT NULL,
    user_id     INT        NOT NULL,
    FOREIGN KEY (question_id) REFERENCES question (id),
    FOREIGN KEY (user_id) REFERENCES user (id)
);