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
import unam.ciencias.is.kafka.modelo.Tema;
import unam.ciencias.is.kafka.modelo.TemaDAO;

/**
 *
 * @author rober
 */
@ManagedBean 
@ViewScoped
public class EliminarTema {
    
    public String eliminaTema(int id){
        TemaDAO temaDAO = new TemaDAO();
        Tema tema=temaDAO.select(id);
        temaDAO.delete(tema);
        
        return "TemaEliminadoIH";    
        
    }
    
}
