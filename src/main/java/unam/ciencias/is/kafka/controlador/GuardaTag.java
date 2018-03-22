/*
 * The Bugtles, 2018.
 * -Aguilar Espíndola Ossiel.
 * -Barajas Tenorio Efraín.
 * -García Ramos Mauricio E.
 * -García Ruíz Paulo.
 * -Ledesma Granados Roberto A.
 */
package unam.ciencias.is.kafka.controlador;

import java.util.HashSet;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import unam.ciencias.is.kafka.modelo.Tag;
import unam.ciencias.is.kafka.modelo.TagDAO;

/**
 *
 * @author Ossiel
 */
@ManagedBean
@ViewScoped
public class GuardaTag {
    private int idTag;
    private String valor;
    private Set temaTags;
    
    public int getIdTag(){
        return this.idTag;
    }
    public void setIdTag(int id){
        this.idTag=id;
    }
    public String getValor(){
        return this.valor;
    }
    public void setValor(String valor){
        this.valor = valor;
    }
    public Set gettemaTags(){
        return this.temaTags;
    }
    public void settemaTags(Set tt){
        this.temaTags = tt;
    }
    
    public void guardaTag(){
        Tag tag = new Tag(valor);
        TagDAO tagdao = new TagDAO();
        tagdao.insert(tag);
    }
}
