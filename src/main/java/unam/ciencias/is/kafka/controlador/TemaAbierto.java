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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import unam.ciencias.is.kafka.modelo.Tema;
import unam.ciencias.is.kafka.modelo.TemaDAO;
import unam.ciencias.is.kafka.modelo.Usuario;
import unam.ciencias.is.kafka.modelo.Responde;

/**
 *
 * @author maurichini
 */

@ManagedBean
@ViewScoped
public class TemaAbierto {

    private String titulo;
    private Tema tema;
    private List<Responde> respuestas;
    private Usuario autor;

    public TemaAbierto() {
        TemaDAO temadao = new TemaDAO();
        HttpServletRequest request
                = (HttpServletRequest) FacesContext.getCurrentInstance().
                        getExternalContext().getRequest();
        String temaS = request.getParameter("idt");
        int idTema = Integer.parseInt(temaS);
        tema = temadao.select(idTema);
        titulo = tema.getNombreTema();
        autor = tema.getUsuario();
        HashSet<Responde> set = new HashSet<Responde>(tema.getRespondes());
        ArrayList<Responde> al = new ArrayList<Responde>(set);
        al.sort(new RespondeComparator());
        respuestas = (List<Responde>) al;
    }

    public String rutaDeImagen(Usuario usr) {
	String ruta = usr.getImagen();

	if (ruta == null)
	    ruta = "imagenes/usuarios/default.png";

	return ruta;
    }

    public List<Responde> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<Responde> respuestas) {
        this.respuestas = respuestas;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Tema getTema() {
        return tema;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }

}

class RespondeComparator implements Comparator<Responde> {

    @Override
    public int compare(Responde r1, Responde r2) {
        return r1.getIdResponde() - r2.getIdResponde();
    }

}
