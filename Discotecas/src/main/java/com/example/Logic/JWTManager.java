package com.example.Logic;

import java.io.UnsupportedEncodingException;

import com.auth0.jwt.*;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.appengine.repackaged.com.google.gson.Gson;
import com.google.appengine.repackaged.com.google.gson.GsonBuilder;

/**
 * 
 * @author jdcm
 *
 * @param <T>
 */
public class JWTManager<T> {
	
	private static Algorithm algorithm;
	
	private T objectReference;
	
	private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public JWTManager() throws UnsupportedEncodingException, JWTCreationException{
		algorithm = Algorithm.HMAC256("secret");
	}

	private DecodedJWT verificarToken(String token) throws JWTVerificationException{
		
			JWTVerifier verifier = JWT.require(algorithm).withIssuer("auth0").build();
			DecodedJWT jwt = verifier.verify(token);
			return jwt;
	}
	
	/**
	 * 
	 * @param token json token
	 * @return objeto extraido del token
	 * @throws JWTVerificationException
	 */
	public T getObjectInstance(String token)throws JWTVerificationException{ //TODO revisar
		
		String tokenContent = verificarToken(token).getPayload();
		
		return (T) gson.fromJson(tokenContent, (Class<T>) objectReference);
		
	}

}
