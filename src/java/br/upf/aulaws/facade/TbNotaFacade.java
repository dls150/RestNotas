/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.upf.aulaws.facade;

import br.upf.aulaws.entity.TbNota;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author 150105
 */
@Stateless
public class TbNotaFacade extends AbstractFacade<TbNota> {

    @PersistenceContext(unitName = "RestNotasPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TbNotaFacade() {
        super(TbNota.class);
    }
    
    /**
     *
     * @param partTitulo
     * @return
     */
    public List<TbNota> findByPartTitulo(String partTitulo){
        List<TbNota> lista = new ArrayList<>();
        try{
            Query query = getEntityManager().createNamedQuery("TbNota.findByPartTitulo");
            query.setParameter(1, partTitulo);
            lista = query.getResultList();
        } catch (Exception e) {
            System.err.println("Error: " + e); 
        }
        
        return lista;        
    }
}
