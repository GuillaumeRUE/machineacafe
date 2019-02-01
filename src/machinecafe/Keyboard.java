package machinecafe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class Keyboard {
	
	Scanner scan;
	
	public int getChoice(int min, int max) {
		int choice = -1;
		boolean valide = true;
		while(valide) {
			try {
				scan = new Scanner(System.in);
				choice = scan.nextInt();
				if(choice < min || choice > max) {
					System.out.println("Choix invalide, recommencez");
				} else {
					valide = false;
				}
			} catch (InputMismatchException e) {
				System.out.println("Entrez un nombre SVP");
			}
		}
		
		return choice;		
	}
	
	public int insertMonnaie() {
		int monnaie = 0;
		boolean valide = true;
		while(valide) {
			System.out.println("Insérez de la monnaie");
			try {
				scan = new Scanner(System.in);
				monnaie = scan.nextInt();
				if(monnaie < 1) {
					System.out.println("Il faut insérer de l'argent");
				} 
				else if(monnaie > 100) {
					System.out.println("Autant d'argent pour un café?");
				}
				else {
					valide = false;
				}
			} catch (InputMismatchException e) {
				System.out.println("Entrez un nombre SVP");
			}
		}
		
		return monnaie;	
	}
	
	public int insertQte(Ingredient ingredient) {
		int qte = 0;
		boolean valide = true;
		while (valide) {
			System.out.println("Quantité :");
			try {
				scan = new Scanner(System.in);
				qte = scan.nextInt();
				if(qte < 1) {
					System.out.println("Vous ne pouvez pas entrer une quantité negative");
				}
				else if (qte > 100 || ingredient.getQuantite()+qte > 100) {
					System.out.printf("Impossible d'ajouter autant, quantité max possible : %d\n", 100-ingredient.getQuantite());
				}
				else {
					valide = false;
				}
			} catch (InputMismatchException e) {
				System.out.println("Entrez un nombre SVP");
			}
		}
		return qte+ingredient.getQuantite();
	}
	
	public String setName() {
		String name = "";
		boolean valide = true;
		while (valide) {
			System.out.println("Nom de la boisson : ");
			try {
				scan = new Scanner(System.in);
				name = scan.next();
				if(name.length() == 0) {
					System.out.println("La boisson doit avoir un nom");
				} else {
					if(name.matches("[0-9]+")) {
						System.out.println("Le nom de la boisson ne peut pas contenir de nombre");
					} else {
						valide = false;
					}
				}
			} catch (InputMismatchException e) {
				System.out.println("Entrez du texte SVP");
			}
		}
		return name;
	}
	
	public int setPrix() {
		int prix = 0;
		boolean valide = true;
		while(valide) {
			System.out.println("prix de la boisson : ");
			try {
				scan = new Scanner(System.in);
				prix = scan.nextInt();
				if(prix < 1) {
					System.out.println("La boisson ne peut pas être gratuite");
				} else {
					if(prix > 5) {
						System.out.println("Ce n'est plus un café à ce prix");
					} else {
						valide = false;
					}
				}
			} catch (InputMismatchException e) {
				System.out.println("Entrez un nombre svp");
			}
		}
		return prix;
	}
	
	public void setQte(Ingredient i, Boisson b) {
		int qte = 0;
		boolean valide = true;
		while(valide) {
			System.out.printf("Quantité de %s :\n", i.getNom());
			try {
				scan = new Scanner(System.in);
				qte = scan.nextInt();
				if (qte > -1 && qte < 6) {
					b.getListeIngredient().put(i, qte);
					valide = false;
				} else {
					System.out.println("Entrez une quantité valide SVP");
				}
			} catch (InputMismatchException e) {
				System.out.println("Entrez un nombre SVP");
			}
			
		}
	}
	

}
