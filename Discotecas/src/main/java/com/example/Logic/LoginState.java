/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Logic;

/**
 *
 * @author jdcm
 */
public class LoginState {
    
    private JWTE token;
    
    private static LoginState instance = null;
    
    protected LoginState(){
        
    }
    
    public static LoginState getInstance(){
        if(instance == null){
            instance = new LoginState();
        }
        return instance;
    }
    
    public void setToken(JWTE token){
        this.token = token;
    }

    public JWTE getToken() {
        return token;
    }
    
    
}
