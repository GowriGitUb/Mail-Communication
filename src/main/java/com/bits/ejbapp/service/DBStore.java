package com.bits.ejbapp.service;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@ApplicationScoped
public class DBStore {

	private static final String TEST_DB = "ejb";
	private static final String TEST_HOST = "localhost";
	private static final int TEST_PORT = 27017;
	private static ResourceBundle resourceBundle = null;
	private Datastore datastore = null;

	public Datastore produceDatastore() {
		return this.datastore;
	}

	private String getValue(String key) {
		if (resourceBundle != null) {
			resourceBundle = ResourceBundle.getBundle("ejb", Locale.US);
		}
		if (resourceBundle != null) {
			return resourceBundle.getString(key);
		}
		return null;
	}

	@PostConstruct
	public void constructDbStore() {
		String database = getValue("DB_NAME");
		String host = getValue("DB_HOST");
		String port = getValue("DB_PORT");

		if (database == null || database.trim().length() < 1) {
			database = TEST_DB;
		}
		if (host == null || host.trim().length() < 1) {
			host = TEST_HOST;
		}
		if (port == null || port.trim().length() < 1) {
			port = "" + TEST_PORT;
		}

		Mongo mongo = new MongoClient(host, Integer.parseInt(port));
		Morphia morphia = new Morphia().mapPackage("com.bits.ejbapp.beans");
		this.datastore = morphia.createDatastore((MongoClient) mongo, TEST_DB);
		datastore.ensureIndexes();
	}

}
