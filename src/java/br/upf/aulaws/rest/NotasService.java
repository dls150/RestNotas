/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.upf.aulaws.rest;

import br.upf.aulaws.entity.TbNota;
import br.upf.aulaws.facade.AbstractFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author 150105
 */
//montando o caminho base para montar a 
//URL "http://localhost:8080/RestNotas/webresources/notas
//Após a inclusão da notação @Path, o projeto disponibiliza a classe como WS.
@Stateless
@Path("notas")
public class NotasService extends AbstractFacade<TbNota> {
    
    //JPA: mapeando a unidade de persistencia...
    @PersistenceContext(unitName = "RestNotasPU")
    private EntityManager em;
    
    //JPA: criando objeto ejb para persistência
    @EJB
    private br.upf.aulaws.facade.TbNotaFacade ejbFacade;
    
       
    //JPA: sobrescrevendo o metodo abstrato de getEntityManager
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public NotasService() {
        super(TbNota.class);
    }

    @Context
    private UriInfo context;
        
     /**
     * Serviço responsável por listar todos registros
     * 
     * @return
     */
    @GET
    @Override
    @Path("/listAll") //adicionando caminho na URL
    @Produces(MediaType.APPLICATION_JSON)
    public List<TbNota> findAll() {
        return super.findAll();
    }
    
     /**
     * Serviço responsável por contar a quantidade de registros do mesmo
     * 
     * @return
     */
    @GET
    @Path("/count") //recebendo parâmetro
    @Produces(MediaType.TEXT_PLAIN)
    public String countReste() {
        return String.valueOf(super.count());
    }
        
     /**
     * Serviço responsável por buscar um registro, recebendo como parâmetro o ID do mesmo
     * 
     * @param id
     * @return
     */
    @GET
    @Path("/findByID/{id}") //recebendo parâmetro
    @Produces(MediaType.APPLICATION_JSON)
    public TbNota findById(@PathParam("id") Integer id) {
        return super.find(id);
    }

    /**
     * Serviço responsável por listar todos os registros que contenham parte do título
     * 
     * @param titulo
     * @return
     */
    @GET
    @Path("/findByPartTitulo/[partTitulo]")
    @Produces(MediaType.APPLICATION_JSON)
    public List<TbNota> findByPartTitulo(@PathParam("partTitulo") String titulo) {
        return ejbFacade.findByPartTitulo(titulo);
    }
}
