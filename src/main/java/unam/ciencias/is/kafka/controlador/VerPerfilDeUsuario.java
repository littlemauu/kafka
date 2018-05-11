/*
 * The Bugtles, 2018.
 * -Aguilar Espíndola Ossiel.
 * -Barajas Tenorio Efraín.
 * -García Ramos Mauricio E.
 * -García Ruíz Paulo.
 * -Ledesma Granados Roberto A.
 */
package unam.ciencias.is.kafka.controlador;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.io.File;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import unam.ciencias.is.kafka.modelo.Tema;
import unam.ciencias.is.kafka.modelo.TemaDAO;
import unam.ciencias.is.kafka.modelo.Usuario;
import unam.ciencias.is.kafka.modelo.Responde;
import org.apache.commons.lang.RandomStringUtils;
import unam.ciencias.is.kafka.modelo.Usuario;
import unam.ciencias.is.kafka.modelo.UsuarioDAO;

/**
 *
 * @author maurichini
 */

@ManagedBean
@ViewScoped
public class VerPerfilDeUsuario {
    
    private Usuario usuario;
    private List<Tema> actividad;
    //private HashSet<Tema> activ;
    
    public VerPerfilDeUsuario(){    
        UsuarioDAO usuariodao = new UsuarioDAO();
        HttpServletRequest request
                = (HttpServletRequest) FacesContext.getCurrentInstance().
                        getExternalContext().getRequest();
        String usuarioS = request.getParameter("idu");
        int idUsuario = Integer.parseInt(usuarioS);
        usuario = usuariodao.select(idUsuario);
        //activ HashSet, ya tiene todos los temas que necesito y se convierte a lista
        HashSet<Tema> activ = new HashSet<Tema>(usuario.getTemas());
        HashSet<Responde> resps = new HashSet<Responde> (usuario.getRespondes());
        /*
         * Recorre un ciclo HashSet resp y agrega, en cada
         * iteración, el tema asociado al objetoo de tipo Responde actual
         */
        for (Responde resp : resps) {
            activ.add(resp.getTema());
        }
        ArrayList<Tema> al = new ArrayList<Tema>(activ);
        al.sort(new TemaComparator());
        actividad = (List<Tema>) al;
        
    }

    public String rutaDeImagen(Usuario usr) {
	String ruta = usr.getImagen();

	if (ruta == null)
	    ruta = "imagenes/usuarios/default.png";

	return ruta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Tema> getActividad() {
        return actividad;
    }

    public void setActividad(List<Tema> actividad) {
        this.actividad = actividad;
    }
    
}
    
    //Ordena la lista de temas
    class TemaComparator implements Comparator<Tema>{   
        @Override
        public int compare (Tema t1, Tema t2){
            return t1.getIdTema() - t2.getIdTema();
        }
    }