/*
 * The Bugtles, 2018.
 * -Aguilar Espíndola Ossiel.
 * -Barajas Tenorio Efraín.
 * -García Ramos Mauricio E.
 * -García Ruíz Paulo.
 * -Ledesma Granados Roberto A.
 */
package unam.ciencias.is.kafka.controlador;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeMessage;

/**
 * Email.enviarEmail permite enviar correos electrónicos
 * @author ludus
 */
public class Email {
/*
    public static void enviarEmail(String dest, String asunto, String texto) {
        final String nombreDeUsuario = "bugtles.kafka@gmail.com";
        final String contrasena = "Gregorio#Samsa";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
	props.put("mail.smtp.EnableSSL.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(nombreDeUsuario, contrasena);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(nombreDeUsuario));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(dest));
            message.setSubject(asunto);
            message.setText(texto);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        
    }
  */  
    public static void enviarEmail(String dest, String asunto, String texto) {
        Properties props = new Properties();
        //InternetHeaders headers = new InternetHeaders();
        //headers.addHeader("Content-type", "text/html; charset=UTF-8");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("bugtles.kafka@gmail.com", "Gregorio#Samsa");
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("bugtles.kafka@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(dest));
            message.setHeader("Content-type", "text/html; charset=UTF-8");
            message.setSubject(asunto);
            message.setText(texto);
            Transport.send(message);
        }
        catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
