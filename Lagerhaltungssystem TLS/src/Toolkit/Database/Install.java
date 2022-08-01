package Toolkit.Database;

import java.sql.SQLException;

public class Install {

    private final String create = "CREATE DATABASE IF NOT EXISTS LaHa;",
            createUserQuery = "CREATE TABLE `LaHa`.`users` ( `ID` INT NOT NULL AUTO_INCREMENT , `USERNAME` VARCHAR(256) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL , `PASSWORD` VARCHAR(256) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL , `FirstName` VARCHAR(256) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL , `LastName` VARCHAR(256) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL , `UserGroup` INT(16) NOT NULL , `Gender` VARCHAR(16) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL , PRIMARY KEY (`ID`)) ENGINE = InnoDB;",
            createProducts = "CREATE TABLE `LaHa`.`products` ( `ID` INT NOT NULL AUTO_INCREMENT , `ArticleNumber` VARCHAR(256) NOT NULL , `ArticleName` VARCHAR(256) NOT NULL , `UNITS` INT NOT NULL , `SellPrice` BINARY(255) NOT NULL , `BuyPrice` BINARY(255) NOT NULL , `xPoints` INT NOT NULL , `yPoints` INT NOT NULL , `Steuersatz` INT NOT NULL , `AMOUNT` INT NOT NULL , `minBestand` INT NOT NULL , `maxBestand` INT NOT NULL , `melBestand` INT NOT NULL , `ordered` BOOLEAN NOT NULL , `DeliveryDate` DATE NOT NULL , `LagerOrt` BINARY(255) NOT NULL , PRIMARY KEY (`ID`)) ENGINE = InnoDB;",
            createUserUser = "CREATE USER 'users'@'%' IDENTIFIED BY 'de56ca7458a10051a8411e7fe1fa19f616418753c4140a834fdda455898f1bf4';",
            createUserProducts = "CREATE USER 'username'@'localhost'IDENTIFIED BY ''";


    public Install () throws SQLException {
        Connection con = new Connection("","root","");
        con.QueryResult(create);
        con.QueryResult(createUserQuery);
        con.QueryResult(createProducts);
        con.QueryResult(createUserUser);
        con.QueryResult(createUserProducts);
    }

}
