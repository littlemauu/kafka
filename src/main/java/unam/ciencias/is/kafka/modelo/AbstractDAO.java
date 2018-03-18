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
 * @param <T>
 */
public class AbstractDAO<T> {
    protected SessionFactory sessionFactory;
    
    public AbstractDAO() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }
    
    public void insert(T elemento) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        
        try {
           transaction = session.beginTransaction();
           session.persist(elemento);
           transaction.commit();
        }
        catch (Exception e) {
            
           if (transaction != null){ 
               transaction.rollback();
           }
           
           e.printStackTrace(); 
        }
        finally {
           session.close();
        }
        
    }
    
    public T select(int id) {
        T resultado = null;
        Transaction transaccion = null;
        Session session = sessionFactory.openSession();
        String nombreClase = resultado.getClass().getSimpleName();
        String cadenaId = "id" + nombreClase;
        
        try {
            transaccion = session.beginTransaction();
            
            String hql = "from " + nombreClase + " as x where x." +
                         cadenaId + " = " + id;
            Query query = session.createQuery(hql);
            resultado = (T)query.uniqueResult();
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
    
    public void update(T elemento) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        
        try {
           transaction = session.beginTransaction();
           session.update(elemento);
           transaction.commit();
        }
        catch (Exception e) {
            
           if (transaction != null){ 
               transaction.rollback();
           }
           
           e.printStackTrace(); 
        }
        finally {
           session.close();
        }
        
    }
    
    public void delete(T elemento) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        
        try {
           transaction = session.beginTransaction();
           session.delete(elemento);
           transaction.commit();
        }
        catch (Exception e) {
            
           if (transaction != null){ 
               transaction.rollback();
           }
           
           e.printStackTrace(); 
        }
        finally {
           session.close();
        }
        
    }
    
}
