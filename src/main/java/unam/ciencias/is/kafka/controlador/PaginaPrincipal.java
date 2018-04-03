    /*
 * The Bugtles, 2018.
 * -Aguilar Espíndola Ossiel.
 * -Barajas Tenorio Efraín.
 * -García Ramos Mauricio E.
 * -García Ruíz Paulo.
 * -Ledesma Granados Roberto A.
 */
package unam.ciencias.is.kafka.controlador;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import unam.ciencias.is.kafka.modelo.Tema;
import unam.ciencias.is.kafka.modelo.TemaDAO;
import unam.ciencias.is.kafka.modelo.Usuario;
import unam.ciencias.is.kafka.modelo.UsuarioDAO;

/**
 *
 * @author ludus
 */
@ManagedBean
@ViewScoped
public class PaginaPrincipal {
    private List<Tema> temas;
    
    public void activar() {
        // PRINCIPIA PARTE DE ACTIVACIÓN DE NUEVAS CUENTAS
        HttpServletRequest request =
                (HttpServletRequest) FacesContext.getCurrentInstance().
                        getExternalContext().getRequest();
        String usuarioNombre = request.getParameter("actn");
        String usuarioHash = request.getParameter("acth");
        
        if(usuarioNombre != null && usuarioHash != null) {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            
            if (usuarioDAO.activarCuenta(usuarioNombre,usuarioHash)) {
                FacesMessage avisoDeActivacion =
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                 "Felicidades, " + usuarioNombre +
                                 ". Su cuenta fue activada exitosamente. " +
                                 "Ahora puede iniciar sesión.",
                                 null);
                FacesContext.getCurrentInstance().
                        addMessage(null,avisoDeActivacion);
            }
            else {
                FacesMessage avisoDeNoActivacion =
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                 "Error: la activación de la cuenta " +
                                         usuarioNombre + " es imposible.",
                                 null);
                FacesContext.getCurrentInstance().
                        addMessage(null,avisoDeNoActivacion);
            }
            
        }
        // TERMINA PARTE DE ACTIVACIÓN DE NUEVAS CUENTAS
    }
    
    public PaginaPrincipal() {
        // PRINCIPIA PARTE DE DESPLIEGUE DE LA LISTA DE TEMAS
        TemaDAO temaDAO = new TemaDAO();
        temas = temaDAO.listaDeTemas();
        // TERMINA PARTE DE DESPLIEGUE DE LA LISTA DE TEMAS
    }
    
    public String preambulo(String descripcion) {
        return descripcion.substring(0,Integer.min(31,descripcion.length())).
               replace('\n',' ') + "...";
    }

    public List<Tema> getTemas() {
        return temas;
    }

    public void setTemas(List<Tema> temas) {
        this.temas = temas;
    }
    
    public void email() {
        Email.enviarEmail("ludus_sl@ciencias.unam.mx", "Probando enlaces",
                "Prueba de enlaces. Haz clic <a href=\"www.google.com\">aquí</a>");
    }
    
}
