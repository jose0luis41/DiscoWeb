package com.example.Logic;

/**
 * 
 * @author jdcm
 */
public class JWTE {
    
    private String token;
    
    public JWTE(){
        
    }
    public JWTE(String id, String token){
        this.token = token;
    }

   

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
	


}
