package machinecafe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class main {
	
	static Keyboard kb = new Keyboard();

	public static int menu() {
		System.out.println("Bienvenue dans votre machine à café");
		System.out.println("=====================================");
		System.out.println("                MENU");
		System.out.println("=====================================");
		System.out.println("1 : Acheter une boisson");		
		System.out.println("2 : Ajouter une boisson");
		System.out.println("3 : Modifier une boisson");
		System.out.println("4 : Supprimer une boisson");
		System.out.println("5 : Ajouter un ingredient");
		System.out.println("6 : Vérifier stock ingrédients");
		System.out.println("7 : Quitter");
		return kb.getChoice(1,7);
	}
	
	
	
	public static void main(String[] args) {
		
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/machineacafe", "postgres", "postgres")) {
			 
            System.out.println("Java JDBC PostgreSQL Example");
            System.out.println("Connected to PostgreSQL database!");
            
            ArrayList<Ingredient> listeIngredient = new ArrayList<Ingredient>();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM public.ingredient");
            while (resultSet.next()) {
            	listeIngredient.add(new Ingredient(resultSet.getString("nom"),resultSet.getInt("quantite")));
            }
 
        } /*catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC driver not found.");
            e.printStackTrace();
        }*/ catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }
		
		//Liste des ingrédients
		Ingredient cafe = new Ingredient("café", 10);
		Ingredient chocolat = new Ingredient("chocolat",10);	
		Ingredient lait = new Ingredient("lait", 10);
		Ingredient sucre = new Ingredient("sucre", 10);
		Ingredient the = new Ingredient("thé", 10);
		ArrayList<Ingredient> listeIngredient = new ArrayList<Ingredient>();
		listeIngredient.add(cafe);
		listeIngredient.add(lait);
		listeIngredient.add(sucre);
		listeIngredient.add(chocolat);
		listeIngredient.add(the);
		
		//Création chocolat chaud
		Map<Ingredient,Integer> z = new HashMap<Ingredient,Integer>();
		z.put(lait, 2);
		z.put(chocolat,3);
		Boisson boisson1 = new Boisson("chocolat chaud", 1, z);
		//Création capuccino
		z = new HashMap<Ingredient,Integer>();
		z.put(cafe,2);
		z.put(lait, 1);
		Boisson boisson2 = new Boisson("capuccino", 2, z);
		//Création cafe
		z = new HashMap<Ingredient,Integer>();
		z.put(cafe, 3);
		Boisson boisson3 = new Boisson("café", 1, z);
		
		//Création de la machine à café
		List<Boisson> listeBoisson = new ArrayList<Boisson>();
		listeBoisson.add(boisson1);
		listeBoisson.add(boisson2);
		listeBoisson.add(boisson3);
		MachineACafe machine = new MachineACafe(listeBoisson,listeIngredient);
		
		boolean continuer = true;
		int monnaie = 0;
		while(continuer) {
			System.out.printf("Solde : %d\n", monnaie);
			int choix = menu();
			switch (choix) {
				case 1:
					Boisson boisson = machine.choisirBoisson();
					if(monnaie == 0 || monnaie < boisson.getPrix()) {
						monnaie += kb.insertMonnaie();
					}
					int qteSucre = kb.getSucre();
					if (sucre.getQuantite() < qteSucre) {
						System.out.println("Pas assez de sucre");
						break;
					}
					if(machine.acheterBoisson(boisson, monnaie)) {
						monnaie -= boisson.getPrix();
						sucre.setQuantite(sucre.getQuantite()-qteSucre);
					}
					break;
				case 2:
					if (machine.getListe().size() == machine.nbBoissonMax) {
						System.out.println("Nombre de boisson maximale atteinte");
						break;
					}
					String name = kb.setName();
					boolean test = false;
					for(Boisson b : machine.getListe()) {
						if(name.equalsIgnoreCase(b.getNom())) {
							test = true;
						}
					}
					if(test) {
						System.out.println("Cette boisson existe déjà");
						break;
					}
					int prix = kb.setPrix();
					Boisson b = new Boisson(name,prix,new HashMap<Ingredient,Integer>());
					if(machine.ajouterOuModifier(listeIngredient, b)) {
						machine.ajouterBoisson(b);
					}
					break;
				case 3:
					Boisson modifBoisson = machine.choisirBoisson();
					if(machine.ajouterOuModifier(listeIngredient, modifBoisson)) {
						System.out.println("Boisson modifée avec succès");
					}
					break;
				case 4:
					machine.supprimerBoisson(machine.choisirBoisson());
					break;
				case 5:
					Ingredient ingredient = machine.choisirIngredient();
					ingredient.setQuantite(kb.insertQte(ingredient));
					break;
				case 6:
					machine.stockIngredients();
					break;
				case 7:
					System.out.println("Au revoir");
					continuer = false;
					break;
			}
		}

	}

}
