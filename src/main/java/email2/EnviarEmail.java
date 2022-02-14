package email2;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.SimpleEmail;

public class EnviarEmail {
	
	public static void main(String[] args) {
		
		String meuEmail = "projetoteste45@gmail.com";
		String minhaSenha = "Cris160219";
		
		SimpleEmail email = new SimpleEmail();
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator(meuEmail, minhaSenha));
		email.setSSLOnConnect(true);
		
		try{
			email.setFrom(meuEmail);
			email.setSubject("Envio de email para programacao WEB");
			email.setMsg("Deu CERTO !!!!");
			email.addTo(meuEmail);
			email.send();
			System.out.println("Email foi enviado com Sucesso!");
			
		}catch(Exception e) {
			e.printStackTrace();
		};
	}

}
