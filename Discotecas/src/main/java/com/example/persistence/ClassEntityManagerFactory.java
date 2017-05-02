package com.example.persistence;


import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ClassEntityManagerFactory {

	
	    
	    private static final EntityManagerFactory EMF_INSTANCE = Persistence.createEntityManagerFactory("unitEcho");
	    
	    private ClassEntityManagerFactory(){
	        
	    }
	    
	    public static EntityManagerFactory get() {
	        return EMF_INSTANCE;
	    }
	    
	
	
	
	
}
