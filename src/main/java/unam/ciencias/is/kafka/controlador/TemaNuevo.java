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
import unam.ciencias.is.kafka.modelo.Tema;
import unam.ciencias.is.kafka.modelo.TemaDAO;
import unam.ciencias.is.kafka.modelo.TemaTagDAO;
import unam.ciencias.is.kafka.modelo.TemaTag;
import unam.ciencias.is.kafka.modelo.Usuario;
import unam.ciencias.is.kafka.modelo.UsuarioDAO;

/**
 * Este no se usará
 * @author Volkodlak
 */
@ManagedBean
@ViewScoped

public class TemaNuevo {/*
    private TemaTag tt;
    private Tag tag;
    private int idTema;
    private String nombreTema;
    private String descripcion;
    private String tags;
    private Usuario usuario;
    private int idUsuario;
    
    public TemaTag getTemaTag(){
        return this.tt;
    }
    public void setTemaTag(TemaTag tt){
        this.tt=tt;
    }
    
    public Tag getTag(){
        return this.tag;
    }
    public void setTag(Tag tag){
        this.tag=tag;
    }
    
    public int getIdTema(){
        return this.idTema;
    }
    public void setIdTema(int id){
        this.idTema=id;
    }
    
    public Usuario getUsuario(){
        return this.usuario;
    }
    public void setUsuario(Usuario user){
        this.usuario = user;
    }
    
    public int getIdUsuario(){
        return this.idUsuario;
    }
    public void setIdUsuario(int id){
        this.idUsuario = id;
    }
    
    public String getNombreTema(){
        return this.nombreTema;
    }
    public void setNombreTema(String nombre){
        this.nombreTema=nombre;
    }
    
    public String getDescripcion(){
        return this.descripcion;
    }
    public void setDescripcion(String descripcion){
        this.descripcion=descripcion;
    }
    
    public String getTags(){
        return this.tags;
    }
    public void setTags(String tags){
        this.tags=tags;
    }
    
    public void temaNuevo(){
        String valorTag;
        int idtag, idtema;
        UsuarioDAO user = new UsuarioDAO();
        TagDAO tagDAO = new TagDAO();
        TemaTagDAO ttDAO = new TemaTagDAO();
        TemaDAO temaDAO = new TemaDAO();
        
        usuario = user.select(idUsuario);
        Tema tema = new Tema(usuario,nombreTema,descripcion);//Crea el Nuevo Tema
        temaDAO.insert(tema);//Guarda el nuevo Tema
        String[] tagsList = tags.split(",");
        try {
        for(int i=0;i<tagsList.length;i++){//Crea las Tags y lod TemaTag
            valorTag = tagsList[i];
            if(tagDAO.noEstaTag(valorTag)){//Verifica si el Tag no existe
                tag = new Tag(valorTag);//Crea un nuevo Tag
                tagDAO.insert(tag);//Guarda el nuevo Tag
            }
            idtag = tagDAO.returnId(valorTag);
            tag = tagDAO.select(idtag);
            tt = new TemaTag(tag,tema);//Crea un TemaTag nuevo
            ttDAO.insert(tt);//Guarda el TemaTag Nuevo
        }//end For
        }catch (Exception e) {e.printStackTrace();}
    }//end temaNuevo()*/
}
