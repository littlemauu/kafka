package unam.ciencias.is.kafka.modelo;
// Generated 17/03/2018 02:05:15 PM by Hibernate Tools 4.3.1



/**
 * TemaTag generated by hbm2java
 */
public class TemaTag  implements java.io.Serializable {


     private int idTemaTag;
     private Tag tag;
     private Tema tema;

    public TemaTag() {
    }

    public TemaTag(int idTemaTag, Tag tag, Tema tema) {
       this.idTemaTag = idTemaTag;
       this.tag = tag;
       this.tema = tema;
    }
   
    public int getIdTemaTag() {
        return this.idTemaTag;
    }
    
    public void setIdTemaTag(int idTemaTag) {
        this.idTemaTag = idTemaTag;
    }
    public Tag getTag() {
        return this.tag;
    }
    
    public void setTag(Tag tag) {
        this.tag = tag;
    }
    public Tema getTema() {
        return this.tema;
    }
    
    public void setTema(Tema tema) {
        this.tema = tema;
    }




}


