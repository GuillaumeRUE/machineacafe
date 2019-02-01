package machinecafe;

public class Ingredient {
	
	//Attributs
	private String nom;
	private int quantite;
	
	//Constructeur
	public Ingredient(String nom, int quantite) {
		setNom(nom);
		setQuantite(quantite);
	}
	
	//Méthodes
	
	/**
	 * Fonction permettant de remplir le stock
	 * d'un ingrédient
	 * @param n : quantité à ajouter
	 */
	public void fill(int n) {
		setQuantite(getQuantite() + n);
		System.out.printf("Ajout effectué avec succés, le stock de %s est maintenant de %d\n", this.getNom(),this.getQuantite());
	}

	
	//Getters & Setters
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

}
