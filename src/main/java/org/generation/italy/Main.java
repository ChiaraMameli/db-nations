package org.generation.italy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
	
	private static final String url = "jdbc:mysql://localhost:8889/nations";
	private static final String user = "root";
	private static final String password = "root";
	
	public static void main(String[] args) {
		 query1();
		 query2();
	}
	
	//Creare in getore db la query per ottenere la lista di tutte le nazioni 
	//mostrando nome, id, nome della regione e nome del continente, ordinata per nome della nazione
	private static void query1() {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Cerca una nazione: ");
		String nation = sc.nextLine();
		sc.close();

		try(Connection con = DriverManager.getConnection(url, user, password)) {
			
			final String sql = "SELECT countries.name, countries.country_id, regions.name, continents.name"
								 + " FROM countries "
								 + " JOIN regions "
								 + " ON countries.region_id = regions.region_id "
								 + " JOIN continents "
								 + " ON regions.continent_id = continents.continent_id "
								 + " WHERE countries.name LIKE ? "
								 + " ORDER BY countries.name ";
			
			try (PreparedStatement ps = con.prepareStatement(sql)){
				ps.setString(1, nation);
				
				try(ResultSet rs = ps.executeQuery()) {
				
				while(rs.next()) {
					
					final String name = rs.getString(1);
					final int id = rs.getInt(2);
					final String region = rs.getString(3);
					final String continent = rs.getString(4);
					
					System.out.println(name + " (" 
										+ id + ") - "
										+ region + " - "
										+ continent);
				}				
				}
			}
						
		} catch (SQLException ex) {
			
			System.err.println("Error: " + ex.getMessage());
		}

	}
	
	//Dopo aver effettuato la ricerca, richiede all'utente un `id` su cui 
	//effettuare ulteriori analisi restituendo *tutte le lingue parlate* + le statistiche piu' recenti della nazione
	private static void query2() {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Cerca un id: ");
		int id = sc.nextInt();
		sc.close();

		try(Connection con = DriverManager.getConnection(url, user, password)) {
			
			final String sql = "SELECT language, year, population, gdp "
								+ " FROM languages "
								+ " JOIN country_languages "
								+ " ON languages.language_id = country_languages.language_id "
								+ " JOIN country_stats "
								+ " ON country_languages.country_id = country_stats.country_id "
								+ " WHERE country_languages.country_id = ?";
			
			try (PreparedStatement ps = con.prepareStatement(sql)){
				ps.setInt(1, id);
				
				try(ResultSet rs = ps.executeQuery()) {
				
				while(rs.next()) {
					
					final String language = rs.getString(1);
					final int year = rs.getInt(2);
					final long population = rs.getLong(3);
					final int gdp = rs.getInt(4);
					
					System.out.println(language + "\n"
							+ "Anno: " + year + "\n"
							+ "Population: " + population + "\n"
							+ "Gdp: " + gdp);
				}				
				}
			}
						
		} catch (SQLException ex) {
			
			System.err.println("Error: " + ex.getMessage());
		}

	}

	}



