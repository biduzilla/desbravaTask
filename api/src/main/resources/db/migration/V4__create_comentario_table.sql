CREATE TABLE COMENTARIO (
    ID VARCHAR(36) PRIMARY KEY NOT NULL,
    USUARIO_ID VARCHAR(36) NOT NULL,
    TAREFA_ID VARCHAR(36) NOT NULL,
    COMENTARIO VARCHAR(250) NOT NULL,
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CREATED_BY VARCHAR(50),
    UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UPDATED_BY VARCHAR(50),

    CONSTRAINT FK_COMENTARIO_USUARIO FOREIGN KEY (USUARIO_ID) REFERENCES USUARIO(ID) ON DELETE CASCADE,
    CONSTRAINT FK_COMENTARIO_TAREFA FOREIGN KEY (TAREFA_ID) REFERENCES TAREFA(ID) ON DELETE CASCADE
);
