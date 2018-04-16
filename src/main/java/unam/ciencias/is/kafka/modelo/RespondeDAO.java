/*
 * The Bugtles, 2018.
 * -Aguilar Espíndola Ossiel.
 * -Barajas Tenorio Efraín.
 * -García Ramos Mauricio E.
 * -García Ruíz Paulo.
 * -Ledesma Granados Roberto A.
 */
package unam.ciencias.is.kafka.modelo;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ludus
 */
public class RespondeDAO extends AbstractDAO<Responde> {
    
    public Responde selectLastByTemaUsuario(Tema tema,Usuario usuario) {
        Responde resultado = null;
        Transaction transaccion = null;
        Session session = sessionFactory.openSession();
        
        try {
            transaccion = session.beginTransaction();  
            String hql =
                    "from Responde as resp where " +
                    "resp.usuario.idUsuario = " + usuario.getIdUsuario() +
                    " and resp.tema.idTema = " + tema.getIdTema() +
                    " order by resp.idResponde desc";
            Query query = session.createQuery(hql);
            query.setMaxResults(1);
            resultado = (Responde) query.uniqueResult();
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
       
}
