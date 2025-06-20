package com.anokata.socketserverdb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class DatabaseHandler {
    private static DatabaseHandler instance;
    private Connection connection;

    DatabaseHandler() {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/db_etudiant", "postgres", "jojo");
            System.out.println("Connexion à la base de données réussie.");
        } catch (SQLException e) {
            System.err.println("Erreur de connexion à la base de données : " + e.getMessage());
        }
    }

    public static synchronized DatabaseHandler getInstance() {
        if (instance == null) {
            instance = new DatabaseHandler();
        }
        return instance;
    }

public void ajouterEtudiant(String numero, String nom, String adresse, double bourse) {
    String sql = "INSERT INTO \"ETUDIANT\" (numero, nom, adresse, bourse) VALUES (?, ?, ?, ?)";
    executeUpdate(sql, numero, nom, adresse, bourse);
}

public void modifierEtudiant(String numero, String nom, String adresse, double bourse) {
    String sql = "UPDATE \"ETUDIANT\" SET nom=?, adresse=?, bourse=? WHERE numero=?";
    executeUpdate(sql, nom, adresse, bourse, numero);
}

public void supprimerEtudiant(String numero) {
    String sql = "DELETE FROM \"ETUDIANT\" WHERE numero=?";
    executeUpdate(sql, numero);
}


public List<String> getAllEtudiantsAsJsonStrings() {
    List<String> etudiantsJson = new ArrayList<>();
    String sql = "SELECT numero, nom, adresse, bourse FROM \"ETUDIANT\"";

    try (Statement stmt = connection.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

        while (rs.next()) {
            JSONObject etudiant = new JSONObject();
            etudiant.put("numero", rs.getString("numero"));
            etudiant.put("nom", rs.getString("nom"));
            etudiant.put("adresse", rs.getString("adresse"));
            etudiant.put("bourse", rs.getDouble("bourse"));

            etudiantsJson.add(etudiant.toString());
        }

    } catch (SQLException e) {
        System.err.println("Erreur lors de la récupération des étudiants : " + e.getMessage());
    }

    return etudiantsJson;
}


    private void executeUpdate(String sql, Object... params) {
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                if (params[i] instanceof String) {
                    stmt.setString(i + 1, (String) params[i]);
                } else if (params[i] instanceof Double) {
                    stmt.setDouble(i + 1, (Double) params[i]);
                }
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'exécution de la requête : " + e.getMessage());
        }
    }
}
