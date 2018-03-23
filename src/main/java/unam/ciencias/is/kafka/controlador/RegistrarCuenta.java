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
    private String mensaje;
    private FacesContext facesContext;
    private HttpSession httpSession;
    //private String rol;
    //private String estado;
    
    public RegistrarCuenta() {
        facesContext = FacesContext.getCurrentInstance();
        httpSession = (HttpSession) facesContext.getExternalContext().
                                    getSession(true);
        mensaje = ((String) httpSession.getAttribute("error")) +
                   " Ingrese sus datos a continuación:";
        
        if (mensaje == null)
            mensaje = "Ingrese sus datos a continuación:";
    }
    
    public String registrarCuenta() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        String correoTmp;
        
        // Flujos alternativos
        if (correo == null || !correo.substring(correo.indexOf('@')).
                               equals("@ciencias.unam.mx")) {
            httpSession.setAttribute("error",
                                     "La dirección de correo electrónico " +
                                     "debe ser @ciencias.unam.mx.");
            return "RegistrarCuentaIH?faces-redirect=true";
        }
        
        correoTmp = correo.substring(0,correo.indexOf('@'));
        correo = correoTmp;
        
        if (contrasena == null || verifContrasena == null ||
                 !contrasena.equals(verifContrasena)) {
            httpSession.setAttribute("error",
                                     "La contraseña y su verificación " +
                                     "no coinciden.");
            return "RegistrarCuentaIH?faces-redirect=true";
        }
        else if (contrasena.length() < 8) {
            httpSession.setAttribute("error",
                                     "La contraseña debe constar de al menos " +
                                     "ocho caracteres.");
            return "RegistrarCuentaIH?faces-redirect=true";
        }
        else if (usuarioDAO.existeUsuarioConCorreo(correo)) {
            httpSession.setAttribute("error",
                                     "La dirección de correo electrónico \"" +
                                     correo + "\" ya está registrada. " +
                                     "Proporcione una dirección distinta.");
            return "RegistrarCuentaIH?faces-redirect=true";
        }
        else if (usuarioDAO.existeUsuarioConNombre(nombre)) {
            httpSession.setAttribute("error",
                                     "El nombre de usuario \"" + nombre +
                                     "\" ya está registrado. " +
                                     "Elija un nombre de usuario distinto.");
            return "RegistrarCuentaIH?faces-redirect=true";
        }
        
        // Flujo normal
        Usuario usuario = new Usuario();
        usuario.setCorreo(correo);
        usuario.setNombre(nombre);
        usuario.setContrasena(contrasena);
        
        if (imagen == null || imagen.length() == 0)
            imagen = "imagenes/usuarios/#default.jpg";
        
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
                new FacesMessage("Éxito",nombreArchivo + " se ha subido.");
        FacesContext.getCurrentInstance().addMessage(null, avisoDeExito);
        System.out.println("$$$$$$$$$" + nombreArchivo + "$$$$$$$$$");
        try {
            System.out.println("$$$$$$$$$ 0 $$$$$$$$$" + archivo.getSize());
            System.out.println("$$$$$$$$$ 1 $$$$$$$$$" + archivo.getContents() == null);
            //InputStream flujo = new ByteArrayInputStream(archivo.getContents());
            System.out.println("$$$$$$$$$ 2 $$$$$$$$$");
            //BufferedImage img = ImageIO.read(flujo); //ImageIO.read(archivo.getInputstream());
            System.out.println("$$$$$$$$$ 3 $$$$$$$$$");
            //BufferedImage imgEscalada = escalarImagen(img,300,300);
            System.out.println("$$$$$$$$$ GUARDAREMOS LA IMAGEN $$$$$$$$$");
            // Generamos un nombre único añadiendo como prefijo el timestamp
            // actual al nombre del archivo subido
            guardarImagen(Long.toHexString((new Date()).getTime()) +
                          nombreArchivo,archivo.getInputstream());
            System.out.println("$$$$$$$$$ YA LA GUARDAMOS $$$$$$$$$");
        }
        catch (IOException e) {
            System.out.println("$$$$$$$$$ ERROR 1 $$$$$$$$$");
            e.printStackTrace();
            FacesMessage avisoDeError =
                    new FacesMessage("Subida infructuosa",
                            "No fue posible subir " + nombreArchivo + ".");
            FacesContext.getCurrentInstance().addMessage(null,avisoDeError);
        }
        
    }
    
    public static BufferedImage escalarImagen(BufferedImage img,
                                              int ancho,int alto) {
        BufferedImage imgEscalada = null;
        
        if (img != null) {
            imgEscalada = new BufferedImage(ancho,alto,img.getType());
            Graphics2D graphics2D = imgEscalada.createGraphics();
            graphics2D.drawImage(img,0,0,ancho,alto,null);
            graphics2D.dispose();
        }
        return imgEscalada;
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
            /*
            ImageIO.write(img,
                          nombreArchivo.substring(nombreArchivo.
                                                  lastIndexOf('.') + 1),
                          salida);
             */
            //imagen = "imagenes/usuarios/" + nombreArchivo;
            //salida.flush();
            //salida.close();
            System.out.println("##### Imagen guardada #####");
        }
        catch (IOException e) {
            System.out.println("##### ERROR 2 #####");
            System.out.println(e.getMessage());
        }

    }
    
    /*public void guardarImagen(String nombreArchivo,byte[] img) {
   
        try {
            ServletContext servletContext =
                    (ServletContext) FacesContext.getCurrentInstance().
                                     getExternalContext().getContext();
            String ruta = (servletContext.getRealPath("/")) +
                                 "/imagenes/usuarios/";
            OutputStream salida =
                    new FileOutputStream(new File(ruta + nombreArchivo));
            FileOutputStream fos = new FileOutputStream(new File(ruta + nombreArchivo));
            fos.write(img);
            fos.close();
            
            ImageIO.write(img,
                          nombreArchivo.substring(nombreArchivo.
                                                  lastIndexOf('.') + 1),
                          salida);

            imagen = "imagenes/usuarios/" + nombreArchivo;
            //salida.flush();
            //salida.close();
            System.out.println("#####Imagen guardada#####");
        }
        catch (IOException e) {
            System.out.println("#####ERROR#####");
            System.out.println(e.getMessage());
        }
        
    }*/

    /*public void guardarImagen(String nombreArchivo,BufferedImage entrada) {
   
        try {
            ServletContext servletContext =
                    (ServletContext) FacesContext.getCurrentInstance().
                                     getExternalContext().getContext();
            String destination = (servletContext.getRealPath("/")) +
                                 "/imagenes/usuarios/";
            OutputStream salida =
                    new FileOutputStream(new File(destination + nombreArchivo));
            ImageIO.write(entrada,
                          nombreArchivo.substring(nombreArchivo.
                                                  lastIndexOf('.') + 1),
                          salida);     
            imagen = "imagenes/usuarios/" + nombreArchivo;
            salida.flush();
            salida.close();
            System.out.println("Imagen guardada");
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
        
    }*/

    public String getVerifContrasena() {
        return verifContrasena;
    }

    public void setVerifContrasena(String verifContrasena) {
        this.verifContrasena = verifContrasena;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
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
