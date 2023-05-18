/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stockservice;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe permettant de se connecter à la base de donnée postGres stocké sur Héroku
 * @author adcoudour
 */
public class ConnectionManager {
    public ConnectionManager() {
        
    }
    
    /**
     * Méthode permettant de se connecter à la base de donnée présent sur héroku
     * @return Connexion à la base de donnée
     * @throws SQLException Erreur SQL si il y a eu une erreur de connexion à la base
     */
    public java.sql.Connection getConnection() throws SQLException {
        Dotenv dotenv = Dotenv.load();
        String dbUrl = dotenv.get("DATABASE_URL");
        String username = dotenv.get("DATABASE_USERNAME");
        String password = dotenv.get("DATABASE_PASSWORD");
        return DriverManager.getConnection(dbUrl, username, password);
    }
}
