package edu.esi.uclm.dao;

import org.bson.Document;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.internal.MongoClientImpl;

public class PruebaDao {
	MongoClientImpl mongoClient;
	MongoDatabase db;

	public MongoDatabase connection() {
		try {
			ConnectionString connectionString = new ConnectionString(
					"mongodb+srv://jaime:passjaime@cluster0.meaqj.mongodb.net/SiGeVac?retryWrites=true&w=majority");
			MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(connectionString)
					.build();
			mongoClient = (MongoClientImpl) MongoClients.create(settings);
			db = mongoClient.getDatabase("SiGeVa");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return db;
	}

	public void insertarPaciente(String nombre, String dni) {
		Document document = new Document();
		document.put("Nombre", nombre);
		document.put("DNI", dni);
		db.getCollection("PruebaIntegracionEclipse").insertOne(document);
	}
	
	public void closeConnection() {
		mongoClient.close();
	}

}
