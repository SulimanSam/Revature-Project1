package com.sam.ConnectionHelper;

public class ConnectionInfo {
    public static final String URL = "jdbc:postgresql://"+System.getenv("DB_ENDPOINT")+System.getenv("P1_DB_NAME");
    public static final String USER_NAME = System.getenv("DB_USERNAME");
    public static final String PASSWORD = System.getenv("DB_PASSWORD");
}
