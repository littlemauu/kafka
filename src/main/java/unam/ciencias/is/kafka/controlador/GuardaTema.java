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
import unam.ciencias.is.kafka.modelo.Tema;
import unam.ciencias.is.kafka.modelo.TemaDAO;
import unam.ciencias.is.kafka.modelo.Usuario;
import unam.ciencias.is.kafka.modelo.UsuarioDAO;
import unam.ciencias.is.kafka.modelo.Tag;
import unam.ciencias.is.kafka.modelo.TagDAO;
import unam.ciencias.is.kafka.modelo.TemaTag;
import unam.ciencias.is.kafka.modelo.TemaTagDAO;

/**
 *
 * @author Ossiel
 */
@ManagedBean
@ViewScoped
public class GuardaTema {
    private int idTema;
    private int idUsuario;
    private Usuario usuario;
    private String nombreTema;
    private String descripcion;
    private String tags;
     
    public int getIdTema(){
        return this.idTema;
    }
    public void setIdTema(int id){
        this.idTema=id;
    }
    public int getIdUsuario(){
        return this.idUsuario;
    }
    public void setIdUsuario(int id){
        this.idUsuario=id;
    }
    public Usuario getUsuario(){
        return this.usuario;
    }
    public void setUsuario(Usuario user){
        this.usuario=user;
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
    public String guardaTema(){
        String dir="";
        UsuarioDAO user = new UsuarioDAO();
        usuario = user.select(idUsuario);
        Tema tema = new Tema(usuario, nombreTema, descripcion);
        TemaDAO temaDAO = new TemaDAO();
        temaDAO.insert(tema);
        dir ="PaginaPrincipalIH";
        String[] tagsList = tags.split(",");
        String tag;
        Tag t;TagDAO tdao = new TagDAO();
        TemaTag tt; TemaTagDAO ttdao = new TemaTagDAO();
        int idtag;
        for (int i=0;i<tagsList.length;i++){
            tag=tagsList[i];
            if(!tdao.existeTag(tag)){
                t = new Tag(tag);
                tdao.insert(t);
            }
            idtag = tdao.returnId(tag);
            t = tdao.select(idtag);
            tt = new TemaTag(t,tema);
            ttdao.insert(tt);
        }
        return dir;
    }
}
