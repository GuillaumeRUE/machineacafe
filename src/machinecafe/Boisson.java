package machinecafe;

import java.util.ArrayList;
import java.util.Map;

public class Boisson {
	
	//Attributs
	private String nom;
	private int prix;
	private Map<Ingredient,Integer> listeIngredient;

	//Constructeur
	public Boisson(String nom,int prix, Map<Ingredient,Integer> listeIngredient) {
		setNom(nom);
		setPrix(prix);
		setListeIngredient(listeIngredient);
	}
	
	//Getters & Setters
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getPrix() {
		return prix;
	}

	public void setPrix(int prix) {
		this.prix = prix;
	}

	public Map<Ingredient, Integer> getListeIngredient() {
		return listeIngredient;
	}

	public void setListeIngredient(Map<Ingredient, Integer> listeIngredient) {
		this.listeIngredient = listeIngredient;
	}

	
	
	

}
