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
import unam.ciencias.is.kafka.modelo.TemaDAO;

/**
 *
 * @author Ossiel
 */
@ManagedBean
@ViewScoped
public class datosTema {
    private int idTema;
    private String descripcion;
    private String nombre;
    
    public datosTema(){
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
     * Muestra el nombre original del Tema
     * @return Nombre del Tema
     */
    public String dameNombre(){
        TemaDAO temaDAO = new TemaDAO();
        int idT = Integer.parseInt(this.tID());
        return temaDAO.select(idT).getNombreTema();
    }
    /**
     * Muestra la descripcionT Original del tema
     * @return Descripcion
     */
    public String dameDescr(){
        TemaDAO temaDAO = new TemaDAO();
        int idT = Integer.parseInt(this.tID());
        return temaDAO.select(idT).getDescripcion();
    }
}
