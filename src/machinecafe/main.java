package machinecafe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
	
	public static ArrayList<Ingredient> instantiateIngredients() {
		ArrayList<Ingredient> listeIngredient = new ArrayList<Ingredient>();
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/machineacafe", "postgres", "postgres")) {
			Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM public.ingredient");
            while (resultSet.next()) {
            	listeIngredient.add(new Ingredient(resultSet.getInt("id"),resultSet.getString("nom"),resultSet.getInt("quantite")));
            }
		} catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }
		return listeIngredient;
	}
	
	public static void updateStocks(ArrayList<Ingredient> liste) {
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/machineacafe", "postgres", "postgres")) {
			for(Ingredient i : liste) {
				String sql = "update ingredient set quantite=? where id=?";
				PreparedStatement stmt = connection.prepareStatement(sql);
				stmt.setInt(1, i.getQuantite());
				stmt.setInt(2, i.getId());
				stmt.executeUpdate();
			}
		} catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }
	}
	
	
	public static void main(String[] args) {
		
		ArrayList<Ingredient> listeIngredient = instantiateIngredients();		
		int posSucre = 0;
		for(int i = 0;i<listeIngredient.size();i++) {
			if(listeIngredient.get(i).getNom().equals("sucre")) {
				posSucre = i;
			}
		}

		
		//Création de la machine à café
		List<Boisson> listeBoisson = new ArrayList<Boisson>();
		MachineACafe machine = new MachineACafe(listeBoisson,listeIngredient);
		
		boolean continuer = true;
		int monnaie = 0;
		while(continuer) {
			System.out.printf("Solde : %d\n", monnaie);
			int choix = menu();
			switch (choix) {
				case 1:
					if(machine.getListe().size() == 0) {
						System.out.println("Il n'y a pas de boisson dans la machine");
						break;
					}
					Boisson boisson = machine.choisirBoisson();
					if(monnaie == 0 || monnaie < boisson.getPrix()) {
						monnaie += kb.insertMonnaie();
					}
					int qteSucre = kb.getSucre();
					if (listeIngredient.get(posSucre).getQuantite() < qteSucre) {
						System.out.println("Pas assez de sucre");
						break;
					}
					if(machine.acheterBoisson(boisson, monnaie)) {
						monnaie -= boisson.getPrix();
						listeIngredient.get(posSucre).setQuantite(listeIngredient.get(posSucre).getQuantite()-qteSucre);
						updateStocks(listeIngredient);
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
