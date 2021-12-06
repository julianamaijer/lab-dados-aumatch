<-- docker run -p 3306:3306 --name mysql-db -e MYSQL_ROOT_PASSWORD=mysqldb -d mysql !-->


CREATE DATABASE AUMATCH;
USE  AUMATCH;

CREATE TABLE ANIMAL
(
    ID BIGINT NOT NULL AUTO_INCREMENT,
    PORTE VARCHAR(100) NOT NULL,
    NOME VARCHAR(100) NOT NULL,
    IDADE INT NOT NULL,
    SEXO VARCHAR(100) NOT NULL,
    TIPO VARCHAR(100) NOT NULL,
    STATUS VARCHAR(100) NOT NULL,
    PRIMARY KEY (ID)
);

SELECT * FROM ANIMAL;



drop procedure if exists Verificar_Quantidade_ANIMAIS;
delimiter #
create procedure Verificar_Quantidade_ANIMAIS
(
OUT quantidade INT
)
begin
SELECT COUNT(*) INTO quantidade FROM ANIMAL;
end# -- end of stored procedure block
delimiter ; -- switch delimiters again

call Verificar_Quantidade_ANIMAIS(@total);
SELECT @total;