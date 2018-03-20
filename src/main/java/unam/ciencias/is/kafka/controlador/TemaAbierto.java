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
import unam.ciencias.is.kafka.modelo.Tema;
import unam.ciencias.is.kafka.modelo.TemaDAO;

/**
 *
 * @author maurichini
 */

@ManagedBean
@ViewScoped
public class TemaAbierto {
    
    private String titulo;
    private Tema tema;
    
    public TemaAbierto(){
        TemaDAO temadao = new TemaDAO();
        HttpServletRequest request =
                (HttpServletRequest) FacesContext.getCurrentInstance().
                        getExternalContext().getRequest();
        String temaS = request.getParameter("idt");
        tema = temadao.select(Integer.parseInt(temaS));
        titulo = tema.getNombreTema();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Tema geTema() {
        return tema;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }
    
    
    
    
}
