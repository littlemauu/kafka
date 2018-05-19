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
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import unam.ciencias.is.kafka.modelo.Tema;
import unam.ciencias.is.kafka.modelo.TemaDAO;
import unam.ciencias.is.kafka.modelo.Tag;
import unam.ciencias.is.kafka.modelo.TagDAO;
import unam.ciencias.is.kafka.modelo.TemaTag;
import unam.ciencias.is.kafka.modelo.TemaTagDAO;
import unam.ciencias.is.kafka.modelo.RespondeDAO;
import java.util.Map;

/**
 *
 * @author Ossiel
 */
@ManagedBean //(name = "modificarTema", eager = true)
@ViewScoped
public class ModificarTema{
    //-----------------------------------------------------------------//
    private String idTema = "1";
    private Tema tema;
    private String nombreT;
    private String descripcionT;
    //-----------------------------------------------------------------//
    public String getIdTema(){
        return this.idTema;
    }
    public void setIdTema(String id){
        this.idTema=id;
    }
    //------------------------------------------//
    public Tema getTema(){
        return tema;
    }
    public void setTema(Tema t){
        this.tema=t;
    }
    //------------------------------------------//
    public String getNombreT(){
        return this.nombreT;
    }
    public void setNombreT(String nombre){
        this.nombreT=nombre;
    }
    //------------------------------------------//
    public String getDescripcionT(){
        return this.descripcionT;
    }
    public void setDescripcionT(String descripcionT){
        this.descripcionT=descripcionT;
    }
    //-----------------------------------------------------------------//
    /**
     * Crea la lista de tags nuevos que tendra el tema dado el nuevo nombre
     * @return lista de tags del tema nuevo
     */
    private String[] newtagsList(){
        String[] namelist = nombreT.split(" "); int nll = namelist.length;
        return namelist;
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
     * Edita un tema propio especifico con los nuevos datos dados
     * @return 
     */
    public String editTema(){
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
        idTema=params.get("tID");
        int id=Integer.parseInt(idTema);
        edit(id);
        return "TemaAbiertoIH?faces-redirect=true&idt=" + idTema;
    }
    /**
     * Regresa el ID del tema que fue seleccionado para editar
     * @return Id del tema por editar
     */
    public String tID(){
        HttpServletRequest request
                = (HttpServletRequest) FacesContext.getCurrentInstance().
                        getExternalContext().getRequest();
        String idText = request.getParameter("tid");
        
        return idText;
    }
    /**
     * Cambia los parametros Nombre y Descripcion del tema con el ID dado
     * @param id Identificador del tema por modificar.
     */
    public void edit(int id){
        TemaDAO temaDAO = new TemaDAO();
        try{
            Tema tema=temaDAO.select(id);
            tema.setNombreTema(this.nombreT);
            tema.setDescripcion(this.descripcionT);
            temaDAO.update(tema);
            creaTags(tema);
        }catch(NullPointerException e){
            System.err.println(id+"/");
        }
    }
    /**
     * Cfrea los nuevos Tags dados por el nuevo nombre
     * @param tema Tema al que se le añadiran Tags
     */
    public void creaTags(Tema tema){
        
        String[] tl=this.newtagsList();
        String tag; int idTag;
        TagDAO tDAO=new TagDAO(); Tag t;
        TemaTagDAO ttDAO = new TemaTagDAO(); TemaTag tt;
        for (String tl1 : tl) {
            tag = this.fTag(tl1);
            if(tag.length()>3){
                if(!tDAO.existeTag(tag)){
                    t=new Tag(tag);
                    tDAO.insert(t);
                }
                idTag=tDAO.returnId(tag);
                t=tDAO.select(idTag);
                tt=new TemaTag(t,tema);
                ttDAO.insert(tt);
            }
        }
    }
}
