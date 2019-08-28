import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	public static void main(String[] args) throws IOException {
		ApplicationContext context=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-mq.xml");
		System.in.read();
	}

}
