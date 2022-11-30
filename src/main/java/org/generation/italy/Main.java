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
	}
	
	//Creare in getore db la query per ottenere la lista di tutte le nazioni 
	//mostrando nome, id, nome della regione e nome del continente, ordinata per nome della nazione
	private static void query1() {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Cerca una nazione: ");
		String nation = sc.next();
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

	}

