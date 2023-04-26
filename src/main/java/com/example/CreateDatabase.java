package com.example;

import org.firebirdsql.gds.impl.GDSType;
import org.firebirdsql.management.FBManager;

public class CreateDatabase {

	public static void main(String[] args) {
		FBManager fbManager = new FBManager(GDSType.getType("EMBEDDED"));
        try {
			System.out.println("Creating database...");
			fbManager.start();
	        fbManager.createDatabase("employee.fdb", "", "");
	        fbManager.stop();
			System.out.println("Database created.");
		} catch (Exception e) {
			System.out.println("Error creating database.");
			e.printStackTrace();
		}
	}

}
