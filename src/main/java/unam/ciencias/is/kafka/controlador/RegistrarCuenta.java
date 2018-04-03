/*
 * The Bugtles, 2018.
 * -Aguilar Espíndola Ossiel.
 * -Barajas Tenorio Efraín.
 * -García Ramos Mauricio E.
 * -García Ruíz Paulo.
 * -Ledesma Granados Roberto A.
 */
package unam.ciencias.is.kafka.controlador;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import javax.servlet.ServletContext;
import unam.ciencias.is.kafka.modelo.Usuario;
import unam.ciencias.is.kafka.modelo.UsuarioDAO;

/**
 *
 * @author ludus
 */
@ManagedBean
@ViewScoped
public class RegistrarCuenta {
    private String correo;
    private String nombre;
    private String contrasena;
    private String verifContrasena;
    private String imagen;
    //private String rol;
    //private String estado;
    
    public RegistrarCuenta() {
    }
    
    public String registrarCuenta() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        String correoTmp;
        
        correoTmp = correo.substring(0,correo.indexOf('@'));
        correo = correoTmp;
        
        if (usuarioDAO.existeUsuarioConCorreo(correo)) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                     "La dirección de correo electrónico \"" +
                                     correo + "@ciencias.unam.mx\" ya está " +
                                     "registrada. Proporcione una " +
                                     "dirección distinta.",
                                     null));
            return "";
        }
        else if (usuarioDAO.existeUsuarioConNombre(nombre)) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                     "El nombre de usuario \"" + nombre +
                                     "\" ya está registrado." +
                                     " Elija un nombre de usuario distinto.",
                                     null));
            return "";
        }
        
        // Flujo normal
        Usuario usuario = new Usuario();
        usuario.setCorreo(correo);
        usuario.setNombre(nombre);
        usuario.setContrasena(contrasena);
        
        if (imagen == null || imagen.length() == 0)
            imagen = "imagenes/usuarios/default.png";
        
        usuario.setImagen(imagen);
        usuario.setRol("USR");
        usuario.setEstado("INACTIVO");
        usuarioDAO.insert(usuario);
        Email.enviarEmail(correo + "@ciencias.unam.mx",
                          "Finalice su registro en Kafka",
                          generarTextoDeEmail(usuario));
        return "PaginaPrincipalIH?faces-redirect=true";
    }
    
    public String generarTextoDeEmail(Usuario usuario) {
        String pagina =
                "http://localhost:8080/kafka/?actn=" + usuario.getNombre() +
                "&acth=" + usuario.hashCode();
        String texto =
                "Para activar su cuenta en Kafka, por favor, " +
                "haga clic en el siguiente enlace: " + pagina;
        return texto;
    }
    
    public void subirImagen(FileUploadEvent evento) {
        UploadedFile archivo = evento.getFile();
        String nombreArchivo = archivo.getFileName();
        FacesMessage avisoDeExito =
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                                 nombreArchivo + " se ha subido.",
                                 null);
        
        try {
            // Generamos un nombre único añadiendo como prefijo el timestamp
            // actual al nombre del archivo subido
            guardarImagen(Long.toHexString((new Date()).getTime()) +
                          nombreArchivo,archivo.getInputstream());
            FacesContext.getCurrentInstance().addMessage(null, avisoDeExito);
        }
        catch (IOException e) {
            FacesMessage avisoDeError =
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            "No fue posible subir " + nombreArchivo + ".",null);
            FacesContext.getCurrentInstance().addMessage(null,avisoDeError);
        }
        
    }
    
    public void guardarImagen(String nombreArchivo,InputStream entrada) {
   
        try {
            ServletContext servletContext
                    = (ServletContext) FacesContext.getCurrentInstance().
                            getExternalContext().getContext();
            String ruta = (servletContext.getRealPath("/"))
                    + "imagenes/usuarios/";
            System.out.println(ruta + nombreArchivo);
            OutputStream salida
                    = new FileOutputStream(new File(ruta + nombreArchivo));
            int bytesEscritos = 0;
            byte[] bytes = new byte[1024];

            while ((bytesEscritos = entrada.read(bytes)) != -1) {
                System.out.println(bytesEscritos);
                salida.write(bytes,0,bytesEscritos);
            }

            imagen = "imagenes/usuarios/" + nombreArchivo;
            entrada.close();
            salida.flush();
            salida.close();
        }
        catch (IOException e) {
            FacesMessage avisoDeError =
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            "No fue posible escribir " + nombreArchivo + ".",
                            null);
            FacesContext.getCurrentInstance().addMessage(null,avisoDeError);
        }

    }

    public String getVerifContrasena() {
        return verifContrasena;
    }

    public void setVerifContrasena(String verifContrasena) {
        this.verifContrasena = verifContrasena;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    
}
