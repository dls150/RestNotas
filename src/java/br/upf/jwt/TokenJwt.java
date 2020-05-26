/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.upf.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.util.Date;

/**
 *
 * @author 150105
 */
public class TokenJwt {
    
    private final Key chave;
    private String jwt;
    
    //Construtor
    public TokenJwt(Key chave){
        this.chave = chave;
    }
    
    /**
     * Método responsável por gerar o Token
     * 
     * @param nomeUsuario
     * @param dataExpiracao
     * @return
     */
    public String gerarToken(String nomeUsuario, Date dataExpiracao){
        jwt = Jwts.builder()
                .setHeaderParam("typ","JWT")                //definindo parametros do cabeçalho
                .setSubject(nomeUsuario)                    //assunto do token
                .setIssuer("djessyca")                      //quem é o emissor do token
                .setIssuedAt(new Date())                    //data de criação
                .setExpiration(dataExpiracao)               //data de expiração do token
                .signWith(SignatureAlgorithm.HS256, chave)  //assinar o token
                .compact();                                 // construir o JWT
        
        return jwt;        
    }
    
    /**
     * Método utilizado para validar o Token
     * @return
     */
    public boolean tokenValido(){
        try{
            Jwts.parser().setSigningKey(chave).parseClaimsJws(jwt);
            return true;
        } catch (Exception e){
            return false;
        }            
    }
    
    /**
     * Método uilizado para recuperar informação de quem é o emissor do token
     * @return
     */
    public String recuperarIssuerDoToken(){
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(chave).parseClaimsJws(jwt);
        return claimsJws.getBody().getIssuer();
    }
    
    /**
     * Método uilizado para recuperar informação do assunto do token
     * @return
     */
    public String recuperarSubjectDoToken(){
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(chave).parseClaimsJws(jwt);
        return claimsJws.getBody().getSubject();
    }    
}
