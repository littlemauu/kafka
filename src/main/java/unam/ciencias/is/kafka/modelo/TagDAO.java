/*
 * The Bugtles, 2018.
 * -Aguilar Espíndola Ossiel.
 * -Barajas Tenorio Efraín.
 * -García Ramos Mauricio E.
 * -García Ruíz Paulo.
 * -Ledesma Granados Roberto A.
 */
package unam.ciencias.is.kafka.modelo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.Query;

/**
 *
 * @author ludus
 */
public class TagDAO extends AbstractDAO<Tag> {
    
    public boolean existeTag(String tag) {
        boolean resp = false;
        Transaction transaccion = null;
        Session session = sessionFactory.openSession();
        
        try {
            transaccion = session.beginTransaction(); 
            String consulta = "from Tag as t where t.valor = '" + tag + "'";
            Query query = session.createQuery(consulta);
            resp = query.uniqueResult() != null;
            transaccion.commit();
        }
        catch (Exception e) {
            if (transaccion != null) {
                transaccion.rollback();
            }
            e.printStackTrace(); 
        }
        finally {session.close();}
        return resp;
    }
    public int returnId(String valor) {
        int resp = -1;
        Transaction transaccion = null;
        Session session = sessionFactory.openSession();
        
        try {
            transaccion = session.beginTransaction(); 
            String consulta = "from Tag as t where t.valor = '" + valor + "'";
            Query query = session.createQuery(consulta);
            Tag tag = (Tag)query.uniqueResult();
            resp = tag.getIdTag();
            transaccion.commit();
        }
        catch (Exception e) {
            if (transaccion != null) {
                transaccion.rollback();
            }
            e.printStackTrace(); 
        }
        finally {session.close();}
        return resp;
    }
}
