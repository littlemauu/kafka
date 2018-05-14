/*
 * The Bugtles, 2018.
 * -Aguilar Espíndola Ossiel.
 * -Barajas Tenorio Efraín.
 * -García Ramos Mauricio E.
 * -García Ruíz Paulo.
 * -Ledesma Granados Roberto A.
 */
package unam.ciencias.is.kafka.controlador;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.util.ArrayUtils;
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
public class TemaNuevo {
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
    /**
     * Crea la lista de tags que tendra el tema usando los tags dados y el
     * nombre del tema
     * @return lista de tags del tema nuevo
     */
    private String[] tagsList(){
        String[] namelist = nombreTema.split(" "); int nll = namelist.length;
        String[] tagslist = tags.split(",");   int tll = tagslist.length;
        String[] res = new String[nll+tll];
        for(int i=0;i<res.length;i++){
            if(i<nll){
                res[i]=namelist[i];
            }else{
                res[i]=tagslist[i-nll];
            }
        }
        return res;
    }
    /**
     * Da formato a los tags quitando puntos y comas y pasando todas las letras
     * a minusculas
     * @param tag Tag original 
     * @return Tag con el formato deseado
     */
    private String fTag(String tag){
        String res="";
        res = tag.replace(".", "").replace(",", "").replace(" ", "").toLowerCase();
        return res;
    }
    /**
     * Guarda un tema nuevo hecho por un usuario
     * @param us Usuario que crea el tema
     * @return Redirteccion a la pagina de principal.
     */
    public String guardaTema(Usuario us){
        String dir="";
        idUsuario=us.getIdUsuario();
        UsuarioDAO user = new UsuarioDAO();
        //usuario = user.select(idUsuario);
        usuario=us;
        Tema tema = new Tema(us, nombreTema, descripcion);
        TemaDAO temaDAO = new TemaDAO();
        temaDAO.insert(tema);
        dir ="PaginaPrincipalIH";
        String[] tagsList = tagsList();
        String tag;
        Tag t;TagDAO tdao = new TagDAO();
        TemaTag tt; TemaTagDAO ttdao = new TemaTagDAO();
        int idtag;
        for (String tagsList1 : tagsList) {
            tag = fTag(tagsList1);
            if(tag.length()>3){
                if(!tdao.existeTag(tag)){
                    t = new Tag(tag);
                    tdao.insert(t);
                }
                idtag = tdao.returnId(tag);
                t = tdao.select(idtag);
                tt = new TemaTag(t,tema);
                ttdao.insert(tt);
            }
        }
        return dir;
    }
}
