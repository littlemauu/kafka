/*
 * The Bugtles, 2018.
 * -Aguilar Espíndola Ossiel.
 * -Barajas Tenorio Efraín.
 * -García Ramos Mauricio E.
 * -García Ruíz Paulo.
 * -Ledesma Granados Roberto A.
 */
package unam.ciencias.is.kafka.modelo;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.Query;
import unam.ciencias.is.kafka.modelo.Usuario;

/**
 *
 * @author ludus
 */
public class TemaDAO extends AbstractDAO<Tema> {
     
    public List<Tema> listaDeTemas() {
        List<Tema> resultado = null;
        Transaction transaccion = null;
        Session session = sessionFactory.openSession();
        
        try {
            transaccion = session.beginTransaction();  
            String hql = "from Tema as t order by t.idTema desc";
            Query query = session.createQuery(hql);
            resultado = (List<Tema>) query.list();
            transaccion.commit();
        }
        catch (Exception e) {

            if (transaccion != null) {
                transaccion.rollback();
            }
            
            e.printStackTrace(); 
        }
        finally {
            session.close();
        }

        return resultado;
    }
    
    public Usuario autorTema(String idTema){
        int idTemaI =  Integer.parseInt(idTema);
        Tema tema = select (idTemaI);
        return tema.getUsuario();  
    }
}
