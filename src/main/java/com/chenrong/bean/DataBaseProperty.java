package com.chenrong.bean;

public class DataBaseProperty {
	
	private String databaseName;
	
	private String characterSetDatabase;
	
	private String collationDatabase;

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getCharacterSetDatabase() {
		return characterSetDatabase;
	}

	public void setCharacterSetDatabase(String characterSetDatabase) {
		this.characterSetDatabase = characterSetDatabase;
	}

	public String getCollationDatabase() {
		return collationDatabase;
	}

	public void setCollationDatabase(String collationDatabase) {
		this.collationDatabase = collationDatabase;
	}

}
