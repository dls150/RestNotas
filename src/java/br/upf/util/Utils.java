/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.upf.util;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author 150105
 */
public class Utils {
   
    /**
     * Gerar uma chave a ser utilizada para a assinatura do Token
     * 
     * @return
     */
    public static Key gerarChave(){
        /* string keyString = "djessyca" -> após a execução do SHA-256 e a execução do EncodeBase64 */ 
        String keyString = "NTExYTRlZTNmZjZhYTY3OWI3NWU1MjI3NjBjNjY1NzI5ZmJiMzA2MmMwY2ViMmVmN2E1MGMxZjRmYzc4YTEyZg==";
        Key key = new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, "HmacSHA256");
        return key;
    }
    
    /**
     * Método que gera uma data de expiração que posteriormente vai definir a validade de um token em minutos
     * 
     * @param tempoEmMinutos
     * @return
     */
    public static Date definirDatadeExpiracao(long tempoEmMinutos){
        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(tempoEmMinutos);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault().systemDefault()).toInstant());
    }
    
}
