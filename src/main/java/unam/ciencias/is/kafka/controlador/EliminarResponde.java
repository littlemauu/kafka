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
import unam.ciencias.is.kafka.modelo.Responde;
import unam.ciencias.is.kafka.modelo.RespondeDAO;

/**
 *
 * @author efrain
 */
public class EliminarResponde {
      public String eliminaResponde(int id){
        RespondeDAO RespondeDAO = new RespondeDAO();
        Responde responde=RespondeDAO.select(id);
        RespondeDAO.delete(responde);
        
        return "TemaAbiertoIH?faces-redirect=true&idt=" + responde.getIdResponde();
    
}
