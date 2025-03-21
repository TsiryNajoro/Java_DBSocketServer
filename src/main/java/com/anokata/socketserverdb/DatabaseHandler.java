/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anokata.socketserverdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 *
 * @author Na
 */
public class DatabaseHandler {
    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/ton_db", "postgres", "jojo");
    }
    // Méthode pour ajouter un étudiant à la base de données
    public void ajouterEtudiant(String numero, String nom, String adresse, double bourse) {
        String sql = "INSERT INTO ETUDIANT (numero, nom, adresse, bourse) VALUES (?, ?, ?, ?)";

        // Utilisation de la connexion et fermeture automatique du PreparedStatement
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Paramétrage de la requête
            stmt.setString(1, numero);  // Utilisation du numéro d'étudiant comme première valeur
            stmt.setString(2, nom);
            stmt.setString(3, adresse);
            stmt.setDouble(4, bourse);  // Utilisation de bourse comme double

            // Exécution de la requête
            stmt.executeUpdate();  
            System.out.println("Etudiant ajouté avec succès !");
        } catch (SQLException e) {
        }
    }
}
