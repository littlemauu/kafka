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
import javax.faces.bean.SessionScoped;
import unam.ciencias.is.kafka.modelo.TemaTagDAO;
import unam.ciencias.is.kafka.modelo.Tema;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;


@ManagedBean
@SessionScoped
public class Buscar {
    
     private String valor;
     private FacesContext faceContext;
     private HttpSession sesion;
     private List<Tema> temas;
     
     
      public Buscar(){
        faceContext = FacesContext.getCurrentInstance();
        sesion=(HttpSession)faceContext.getExternalContext().getSession(true);
        temas=(List<Tema>) sesion.getAttribute("temas");
        if(temas!=null)
        for(Tema t : temas){
            System.out.println(t.getNombreTema());
        }
    }
     
     public String getValor(){
         return valor;
     }
     public void setValor(String valor){
         this.valor=valor;
     }

    public List<Tema> getTemas() {
        return temas;
    }

    public void setTemas(List<Tema> temas) {
        this.temas = temas;
    }
     
     
     
     public String busca (String valor){
         TemaTagDAO tt = new TemaTagDAO();
         this.temas = tt.obtener(valor);
         if(temas.size()==0){
             return "SinResultadosIH?faces-redirect=true";
         }
         for(Tema t: temas){
             System.out.println(t.getNombreTema());
         }
         sesion.setAttribute("temas", temas);

         return "ResultadosIH?faces-redirect=true";

     }
    
     
     public String preambulo(String descripcion) {
        return descripcion.substring(0,Integer.min(31,descripcion.length())).
               replace('\n',' ') + "...";
    }
    
}
