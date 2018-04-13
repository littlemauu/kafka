/*
 * The Bugtles, 2018.
 * -Aguilar Espíndola Ossiel.
 * -Barajas Tenorio Efraín.
 * -García Ramos Mauricio E.
 * -García Ruíz Paulo.
 * -Ledesma Granados Roberto A.
 */
package unam.ciencias.is.kafka.controlador;

import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import unam.ciencias.is.kafka.modelo.Responde;
import unam.ciencias.is.kafka.modelo.RespondeDAO;
import unam.ciencias.is.kafka.modelo.Tema;
import unam.ciencias.is.kafka.modelo.TemaDAO;
import unam.ciencias.is.kafka.modelo.Usuario;

/**
 *
 * @author arlequin
 */
@ManagedBean
@ViewScoped
public class RespuestaNueva {
     private Tema tema;
     private Usuario usuario;
     private String respuesta;
     private Date fecha;
     
     public RespuestaNueva() {
     }
     
     public String respuestaNueva(Tema tema,Usuario usuario) {
         this.tema = tema;
         this.usuario = usuario;
         Responde resp = new Responde();
         RespondeDAO respDAO = new RespondeDAO();
         TemaDAO temaDAO = new TemaDAO();
         fecha = new Date();
         resp.setTema(tema);
         resp.setUsuario(usuario);
         resp.setRespuesta(respuesta);
         resp.setFecha(fecha);
         respDAO.insert(resp);
         resp = respDAO.selectLastByTemaUsuario(tema,usuario);
         tema.getRespondes().add(resp);
         temaDAO.update(tema);
         return "TemaAbiertoIH?faces-redirect=true&idt=" + tema.getIdTema();
     }

    public Tema getTema() {
        return tema;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
}
