import  java.lang.Math;
import java.util.*;

public class GoPersonne{
    public static void main(String[] args){
        Personne p1 = new Personne();
        Scanner sc= new Scanner(System.in);
        System.out.print("Entrer un nom : - ");  
        String nom = sc.nextLine();
        System.out.print("Entrer un prénom : - ");  
        String prenom = sc.nextLine();
        p1.setNom(nom);
        p1.setPrenom(prenom);
        System.out.print("Entrer un jour de naissance : - ");  
        int jour = sc.nextInt();
        System.out.print("Entrer un mois de naissance : - ");  
        int mois = sc.nextInt();
        System.out.print("Entrer un année de naissance : - ");  
        int annee = sc.nextInt();
        p1.setDateNaiss(jour, mois, annee);
        System.out.println("Vous avez saisi : " + p1.nom + " "  + p1.prenom + " né.e le " + p1.jourNaiss + "/" + p1.moisNaiss + "/" + p1.anneeNaiss);
    
        Personne p2 = new Personne();
        sc= new Scanner(System.in);
        System.out.print("Entrer un nom : - ");  
        nom = sc.nextLine();
        System.out.print("Entrer un prénom : - ");  
        prenom = sc.nextLine();
        p2.setNom(nom);
        p2.setPrenom(prenom);
        p2.setDateNaiss(jour, mois, annee);
        System.out.println("Vous avez saisi : " + p2.nom + " "  + p2.prenom + " né.e le " + p2.jourNaiss + "/" + p2.moisNaiss + "/" + p2.anneeNaiss);
        System.out.println(p1);
        System.out.println(p2);
    }
}