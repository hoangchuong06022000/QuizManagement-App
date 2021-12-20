package Client.BUS;

import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendOTP {
	private static String FROM_MAIL = "nhom51ltmquiz@gmail.com";
    private static String PASSWORD = "Nhom51ltmquiz@";
    private static String SEND_MAIL;
    
    public SendOTP() {}
    
	public SendOTP(String userName, String otp) {
        String from = FROM_MAIL;
        String pass = PASSWORD;
        SEND_MAIL = userName;
        String[] to = { SEND_MAIL }; 
        String subject = "Mã OTP của bạn là";
        String body = otp;

        sendFromGMail(from, pass, to, subject, body);
    }

    private void sendFromGMail(String from, String pass, String[] to, String subject, String body) {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }
    
    public String OTP () {
    	String otp = "";
    	String numbers = "0123456789";
    	Random random = new Random();
        char[] tmp = new char[4];
  
        for (int i = 0; i < 4; i++){
        	tmp[i] = numbers.charAt(random.nextInt(numbers.length()));
        	otp += tmp[i];
        }
        return otp;
    }
}