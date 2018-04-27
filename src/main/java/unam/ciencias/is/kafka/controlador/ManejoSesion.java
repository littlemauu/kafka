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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import unam.ciencias.is.kafka.modelo.UsuarioDAO;
import unam.ciencias.is.kafka.modelo.Usuario;
/**
 *
 * @author rober
 */
@ManagedBean
@SessionScoped
public class ManejoSesion {
    
    private String correo;
    private String nombre;
    private String contrasena;
    private Usuario usuario;
    private FacesContext faceContext;
    private HttpSession sesion;
    
    public ManejoSesion(){
        faceContext = FacesContext.getCurrentInstance();
        sesion=(HttpSession)faceContext.getExternalContext().getSession(true);
    }
    
    public void setNombre(String nombre){
        this.nombre=nombre;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public void setCorreo(String correo){
        this.correo=correo;
    }
    public String getCorreo(){
        return correo;
    }
    public void setContrasena(String contrasena){
        this.contrasena=contrasena;
    }
    public String getContrasena(){
        return contrasena;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
  
    public Usuario getUsuario() {
        return usuario;
    }

    public boolean hayUsuario(){
        return this.getUsuario()!=null;
    }

    public boolean esAdmin() {
	return usuario != null && "ADM".equals(usuario.getRol());
    }
    
    public String estado(){
        
        UsuarioDAO ud = new UsuarioDAO();
        Usuario usuario = ud.valida(this.getNombre(), this.getContrasena());
        this.setUsuario(usuario);
        sesion.setAttribute("usuario", usuario);
        if(this.getUsuario()!=null){
            
            return "PaginaPrincipalIH?faces-redirect=true";
        }
        else{
            return "InicioFallidoIH?faces-redirect=true";
        }     
    }
    
    public String salir(){
        this.setUsuario(null);
        return "PaginaPrincipalIH?faces-redirect=true";
    }

    public String vuelve(){
        return "PaginaPrincipalIH?faces-redirect=true";
    }
}
