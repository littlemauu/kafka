/*
 * The Bugtles, 2018.
 * -Aguilar Espíndola Ossiel.
 * -Barajas Tenorio Efraín.
 * -García Ramos Mauricio E.
 * -García Ruíz Paulo.
 * -Ledesma Granados Roberto A.
 */
package unam.ciencias.is.kafka.controlador;

import java.io.File;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.apache.commons.lang.RandomStringUtils;
import unam.ciencias.is.kafka.modelo.Usuario;
import unam.ciencias.is.kafka.modelo.UsuarioDAO;

/**
 *
 * @author ludus
 */
@ManagedBean
@ViewScoped
public class VerPerfilDeUsuario {
    private Usuario usuario;
    
    public String eliminarUsuario() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuario = usuarioDAO.select(45);
        
        if ("INACTIVO".equals(usuario.getEstado()) ||
            "ADM".equals(usuario.getRol())) {
            FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Imposible eliminar al usuario.",
                                null));
            return "";
            //return "VerPerfilDeUsuarioIH?faces-redirect=true&idu=28";
        }
        
        usuario.setNombre("[Eliminado " + usuario.getIdUsuario() + "]");
        usuario.setContrasena(RandomStringUtils.randomAlphabetic(16));
        usuario.setEstado("INACTIVO");
        String imgAnt = usuario.getImagen();
        usuario.setImagen("imagenes/usuarios/eliminado.png");
        usuarioDAO.update(usuario);
        
        if (imgAnt != null && !imgAnt.equals("imagenes/usuarios/default.png") &&
                !imgAnt.equals("imagenes/usuarios/eliminado.png")) {
            
            try {
                ServletContext servletContext
                    = (ServletContext) FacesContext.getCurrentInstance().
                            getExternalContext().getContext();
                String ruta = (servletContext.getRealPath("/")) + imgAnt;
                File archivo = new File(ruta);
                
                if (archivo.delete())
                    System.out.println("%%% ELIMINADO %%%");
                else
                    System.out.println("%%% IMPOSIBLE: <" + archivo.getName() +
                            "> %%% " + archivo.getCanonicalPath());
                
            }
            catch (Exception e) {
                System.out.println("%%% ERROR AL BORRAR: <" + imgAnt + "> %%%");
                e.printStackTrace();
            }
            
        }
        
        FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "El usuario fue eliminado exitosamente.",
                                null));
        return "";
        //return "VerPerfilDeUsuarioIH?faces-redirect=true&idu=28";
    }
    
}
