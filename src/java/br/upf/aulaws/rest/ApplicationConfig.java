/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.upf.aulaws.rest;
import java.util.Set;
import javax.ws.rs.core.Application;


/**
 *
 * @author 150105
 */
//Define o caminho base para montagem da URL ".../webresources/.."
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application{
    
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }


    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(br.upf.aulaws.rest.NotasService.class);
    } 
    
}
