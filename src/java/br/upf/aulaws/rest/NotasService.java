/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.upf.aulaws.rest;

import br.upf.aulaws.entity.TbNota;
import br.upf.aulaws.facade.AbstractFacade;
import br.upf.jwt.TokenJwt;
import br.upf.util.Utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import java.security.Key;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
     * Serviço responsável por buscar um registro, recebendo como parâmetro o ID
     * do mesmo
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
     * Serviço responsável por listar todos os registros que contenham parte do
     * título
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

    /**
     * Serviço responsável por adicionar um registro utilizando método POST
     *
     * @param entity
     */
    @POST
    @Path("/add")
    @Override
    @Produces({MediaType.APPLICATION_JSON})
    public void create(TbNota entity) {
        super.create(entity);
    }

    /**
     * Serviço responsável por salvar um registro editado
     *
     * @param entity
     */
    @PUT
    @Path("/edit")
    @Produces({MediaType.APPLICATION_JSON})
    public void editNota(TbNota entity) {
        super.edit(entity);
    }

    /**
     * Serviço responsável por remover um registro
     *
     * @param id
     */
    @DELETE
    @Path("/delete/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    /**
     * Métido responsável por autenticar o usuário no sistema, gerar um token e
     * retornar para o cliente
     *
     * @param user
     * @param password
     * @return
     */
    @POST
    @Path("/authorize/{user}/{password}")
    @Produces(MediaType.APPLICATION_XML)
    public Response geAuthorizeResource(@PathParam("user") String user, @PathParam("password") String password) {
        TokenJwt token = new TokenJwt(Utils.gerarChave());

        if (user != null && password != null && user.equals("djessyca") && password.equals("123")) {
            Date dataDeExpiracao = Utils.definirDatadeExpiracao(10L);
            String jwt = token.gerarToken("djessyca", dataDeExpiracao);
            return Response.ok(jwt).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    /**
     * Método utiliza a HeaderParam para receber os parâmetros (teste realizado com o Postman)
     * @param authHeader
     * @return
     */
    @POST
    @Path("/dataHeaderParam")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getDataHeaderParam(@HeaderParam("authorization") String authHeader) {
        Key key = Utils.gerarChave();

        if (authHeader != null) {
            try {
                //Gerando a Claims
                Jws<Claims> claims = Jwts.parser().setSigningKey(key).parseClaimsJws(authHeader);
                //verifica se a pessoa que consta no subject no momento da criação do token
                // é a mesma que consta na comparação
                if (!claims.getBody().getSubject().equals("Pedro")) {
                   return Response.status(401).entity("Usuario não autorizado.").build();
                }
                //retorna status de usuário autorizado
                return Response.ok("Usuário autorizado.").build();
            } catch (Exception e) {
                return Response.status(403).entity(e.getMessage()).build();
            }
        } else {
            return Response.status(403).entity("Nenhum cabeçalho de autenticação presente.").build();
        }

    }
}
