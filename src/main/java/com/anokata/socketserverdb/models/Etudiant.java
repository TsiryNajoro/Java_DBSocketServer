/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anokata.socketserverdb.models;

import java.io.Serializable;
/**
 *
 * @author Na
 */
public class Etudiant implements Serializable {
    private String numero;      // Numéro de l'étudiant (clé primaire)
    private String nom;         // Nom de l'étudiant
    private String adresse;     // Adresse de l'étudiant
    private double bourse;      // Montant de la bourse (en double, peut être un montant d'argent)

    // Constructeur
    public Etudiant(String numero, String nom, String adresse, double bourse) {
        this.numero = numero;
        this.nom = nom;
        this.adresse = adresse;
        this.bourse = bourse;
    }

    // Getters et setters
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public double getBourse() {
        return bourse;
    }

    public void setBourse(double bourse) {
        this.bourse = bourse;
    }

    // Optionnel : Méthode toString pour une représentation lisible de l'objet
    @Override
    public String toString() {
        return "Etudiant [numero=" + numero + ", nom=" + nom + ", adresse=" + adresse + ", bourse=" + bourse + "]";
    }
}