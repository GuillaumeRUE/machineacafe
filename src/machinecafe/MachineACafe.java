package machinecafe;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MachineACafe {

	//attributs
	private List<Boisson> liste;
	private ArrayList<Ingredient> listeIngredient;
	
	public static final int nbBoissonMax = 5;

	//Constructeur
	public MachineACafe(List<Boisson> liste, ArrayList<Ingredient> listeIngredient) {
		setListe(liste);
		setListeIngredient(listeIngredient);
	}
	
	//Méthodes
	
	/**
	 * Suppression d'un boisson
	 * @param b : boisson à supprimer
	 */
	public void supprimerBoisson(Boisson b) {
		System.out.println("La boisson a été retiré avec succés");
		this.liste.remove(this.liste.indexOf(b));
	}
	
	public void ajouterBoisson(Boisson b) {
		this.liste.add(b);
		System.out.println("La boisson a été ajouté avec succés");
	}
	
	public void ajouterIngredient(Ingredient i) {
		this.listeIngredient.add(i);
		System.out.printf("L'ingrédient %s a été ajouté avec succés", i.getNom());
	}
	
	public void stockIngredients() {
		for (Ingredient l : listeIngredient) {
			System.out.printf("Nom : %s - Quantite : %d\n", l.getNom(), l.getQuantite());
		}
	}
	
	public boolean acheterBoisson(Boisson b, int monnaie) {
		if(monnaie < b.getPrix()) {
			System.out.println("Monnaie insuffisante");
			return false;
		}
		//verif ingredients
		for (Ingredient i : this.listeIngredient) {
			if (b.getListeIngredient().containsKey(i)) {
				if(b.getListeIngredient().get(i) > i.getQuantite()) {
					System.out.printf("Il n'y a pas assez de %s\n", i.getNom());
					return false;
				}
			}
		}
		for (Ingredient i : this.listeIngredient) {
			if (b.getListeIngredient().containsKey(i)) {
				i.setQuantite(i.getQuantite() - b.getListeIngredient().get(i));
			}
		}
		System.out.printf("%s en cours de préparation\n", b.getNom());
		return true;
	}
	
	public void listerBoisson() {
		System.out.println("Liste des boissons :");
		int i = 0;
		for (Boisson b : this.liste) {
			System.out.printf("%d : %s - %d€\n", i,b.getNom(),b.getPrix());
			i++;
		}
	}
	
	public void listerIngredient() {
		System.out.println("Liste des ingrédients :");
		int j = 0;
		for (Ingredient i : this.listeIngredient) {
			System.out.printf("%d : %s\n",j, i.getNom());
			j++;
		}
	}
	
	public Boisson choisirBoisson() {
		this.listerBoisson();
		Keyboard kb = new Keyboard();
		return this.liste.get(kb.getChoice(0, this.liste.size()-1));
	}
	
	public Ingredient choisirIngredient() {
		this.listerIngredient();
		Keyboard kb = new Keyboard();
		return this.listeIngredient.get(kb.getChoice(0, this.listeIngredient.size()-1));
	}
	
	public boolean ajouterOuModifier(ArrayList<Ingredient> listeIngredient, Boisson b) {
		int cpt = 0;
		boolean the = false;
		boolean autre = false;
		for (Ingredient i : listeIngredient) {
			if(!i.getNom().equals("sucre")) {
				Keyboard kb = new Keyboard();
				kb.setQte(i, b);
				if(b.getListeIngredient().get(i) != 0) {
					if(i.getNom().equals("thé")) {
						the = true;
					} else {
						autre = true;
					}
				} else {
					cpt++;
				}
				
			}	
		}
		if (cpt == 4) {
			System.out.println("Impossible de créer une eau chaud ou sucrée");
			return false;
		}
		if (the && autre) {
			System.out.println("Impossible de mélanger du thé avec autre chose que l'eau et du sucre");
			return false;
		}
		return true;
	}
	
	//Getters & Setters
	public List<Boisson> getListe() {
		return liste;
	}

	public void setListe(List<Boisson> liste) {
		this.liste = liste;
	}

	public ArrayList<Ingredient> getListeIngredient() {
		return listeIngredient;
	}

	public void setListeIngredient(ArrayList<Ingredient> listeIngredient) {
		this.listeIngredient = listeIngredient;
	}
	
	
	
	
}
