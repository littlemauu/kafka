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
import java.util.List;
import java.util.ArrayList;
import org.hibernate.SQLQuery;
import org.hibernate.cfg.Configuration;


/**
 *
 * @author ludus
 */
public class TemaTagDAO extends AbstractDAO<TemaTag> {
    
    
     public boolean existeTemaTag(String tematag) {
        boolean resp = false;
        Transaction transaccion = null;
        Session session = sessionFactory.openSession();
        
        try {
            transaccion = session.beginTransaction(); 
            String consulta = "from TemaTag as t where t.tag = '" + tematag + "'";
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
     
     public List<Tema> obtener(String tag){
        SessionFactory factory; 
        factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        String sql = "FROM TemaTag as tt where tt.tag.valor='" + tag + "'";
        Query query = session.createSQLQuery(sql);
        query=session.createQuery(sql);
        List<TemaTag> tematags= (List<TemaTag>) query.list();
        ArrayList<Tema> temas = new ArrayList<Tema>();
        for(TemaTag tt : tematags){
            Tema t = tt.getTema();
            temas.add(t);
        }
        if (temas!= null && !temas.isEmpty()) {
            return temas;
        }else{
            return null;
        }
         
     }
    
    
}
