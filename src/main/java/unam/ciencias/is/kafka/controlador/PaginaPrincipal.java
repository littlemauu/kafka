/*
 * The Bugtles, 2018.
 * -Aguilar Espíndola Ossiel.
 * -Barajas Tenorio Efraín.
 * -García Ramos Mauricio E.
 * -García Ruíz Paulo.
 * -Ledesma Granados Roberto A.
 */
package unam.ciencias.is.kafka.controlador;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import unam.ciencias.is.kafka.modelo.Usuario;
import unam.ciencias.is.kafka.modelo.UsuarioDAO;

/**
 *
 * @author ludus
 */
@ManagedBean
@ViewScoped
public class PaginaPrincipal {
    private FacesContext context;
    private String mensaje;
    
    public PaginaPrincipal() {
        mensaje = "...";
        context = FacesContext.getCurrentInstance();
        HttpServletRequest request =
                (HttpServletRequest) FacesContext.getCurrentInstance().
                        getExternalContext().getRequest();
        String usuarioNombre = request.getParameter("actn");
        String usuarioHash = request.getParameter("acth");
        
        if(usuarioNombre != null && usuarioHash != null) {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            
            if (usuarioDAO.activarCuenta(usuarioNombre,usuarioHash)) {
                mensaje = "Su cuenta fue activada exitosamente. " +
                          "Ahora puede iniciar sesión.";
                /*context.addMessage(null,
                        new FacesMessage(
                                "Su cuenta fue activada exitosamente. " +
                                "Ahora puede iniciar sesión."));*/
            }
            else {
                mensaje = "Error: Activación de cuenta imposible.";
                /*context.addMessage(null,
                        new FacesMessage(
                                "Error: Activación de cuenta imposible."));*/
            }
            
        }

    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public FacesContext getContext() {
        return context;
    }

    public void setContext(FacesContext context) {
        this.context = context;
    }
    
}
