import java.util.Arrays;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringEmailMain {
	
	
	public static void main(String[] args) {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
				SpringEmailMain.class.getPackage().getName());
		
		Mailer mailer = applicationContext.getBean(Mailer.class);
		mailer.enviar(new Mensagem("Cristian Teste <projetoteste45@gmail.com", Arrays.asList("Teste teste"), "Teste Spring email", "Deu tudo certo ! \n \n ainda bem!!!!!!"));
		
		applicationContext.close();
		
		System.out.println("FIM");
	}

}
