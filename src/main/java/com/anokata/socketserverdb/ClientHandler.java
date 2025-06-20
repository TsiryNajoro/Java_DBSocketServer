package com.anokata.socketserverdb;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (
            ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream())
        ) {
            String etudiantData = (String) inputStream.readObject(); 
            
            String[] data = etudiantData.replace("{", "").replace("}", "").split(",");
            String numero = "", nom = "", adresse = "";
            double bourse = 0;
            String action = "";

            for (String field : data) {
                String[] keyValue = field.split(":");
                switch (keyValue[0].trim().replace("\"", "")) {
                    case "numero":
                        numero = keyValue[1].trim().replace("\"", "");
                        break;
                    case "nom":
                        nom = keyValue[1].trim().replace("\"", "");
                        break;
                    case "adresse":
                        adresse = keyValue[1].trim().replace("\"", "");
                        break;
                    case "bourse":
                        bourse = Double.parseDouble(keyValue[1].trim());
                        break;
                    case "action":
                        action = keyValue[1].trim().replace("\"", "");
                        break;
                }
            }

            DatabaseHandler dbHandler = new DatabaseHandler();
            switch (action) {
                case "Ajout":
                    dbHandler.ajouterEtudiant(numero, nom, adresse, bourse);
                    outputStream.writeObject("OK");
                    sendAllEtudiants(outputStream, dbHandler);
                    System.out.println("requête d'ajout enclenchée");
                    break;
                case "Modification":
                    dbHandler.modifierEtudiant(numero, nom, adresse, bourse);
                    outputStream.writeObject("OK");
                    sendAllEtudiants(outputStream, dbHandler);
                    break;
                case "Suppression":
                    dbHandler.supprimerEtudiant(numero);
                    outputStream.writeObject("OK");
                    sendAllEtudiants(outputStream, dbHandler);
                    break;
                case "DemandeListe":  // Nouveau cas
                    outputStream.writeObject("OK");
                    sendAllEtudiants(outputStream, dbHandler);
                    break;
                case "GET_ALL":
                    outputStream.writeObject("OK");
                    sendAllEtudiants(outputStream, dbHandler);
                    break;

                default:
                    outputStream.writeObject("Action non reconnue");
                    break;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (clientSocket != null && !clientSocket.isClosed()) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void sendAllEtudiants(ObjectOutputStream outputStream, DatabaseHandler dbHandler) throws IOException {
    List<String> allEtudiantsJson = dbHandler.getAllEtudiantsAsJsonStrings();

    StringBuilder jsonArray = new StringBuilder("[");
    for (int i = 0; i < allEtudiantsJson.size(); i++) {
        jsonArray.append(allEtudiantsJson.get(i));
        if (i < allEtudiantsJson.size() - 1) {
            jsonArray.append(",");
        }
    }
    jsonArray.append("]");

    outputStream.writeObject(jsonArray.toString()); // Envoi final
}

}
