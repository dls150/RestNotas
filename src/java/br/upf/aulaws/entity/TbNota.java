/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.upf.aulaws.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 150105
 */
@NamedNativeQueries({
    //Querie responsável por buscar utilizando parte do título
    @NamedNativeQuery(name = "TbNota.findByPartTitulo",
            query = "SELECT * FROM tb_nota AS nota WHERE nota.titulo ~* ?",
            resultClass = TbNota.class)
})

@Entity
@Table(name = "tb_nota")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbNota.findAll", query = "SELECT t FROM TbNota t")
    , @NamedQuery(name = "TbNota.findByIdNote", query = "SELECT t FROM TbNota t WHERE t.idNote = :idNote")
    , @NamedQuery(name = "TbNota.findByTitulo", query = "SELECT t FROM TbNota t WHERE t.titulo = :titulo")
    , @NamedQuery(name = "TbNota.findByDescricao", query = "SELECT t FROM TbNota t WHERE t.descricao = :descricao")})
public class TbNota implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_note")
    private Integer idNote;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "titulo")
    private String titulo;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "descricao")
    private String descricao;

    public TbNota() {
    }

    public TbNota(Integer idNote) {
        this.idNote = idNote;
    }

    public TbNota(Integer idNote, String titulo, String descricao) {
        this.idNote = idNote;
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public Integer getIdNote() {
        return idNote;
    }

    public void setIdNote(Integer idNote) {
        this.idNote = idNote;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNote != null ? idNote.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbNota)) {
            return false;
        }
        TbNota other = (TbNota) object;
        if ((this.idNote == null && other.idNote != null) || (this.idNote != null && !this.idNote.equals(other.idNote))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.upf.aulaws.entity.TbNota[ idNote=" + idNote + " ]";
    }

}
