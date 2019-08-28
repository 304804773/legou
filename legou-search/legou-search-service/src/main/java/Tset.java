import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pojo.Person;
import pojo.User;
import pojo.UserService;

public class Tset {
	public static void main(String[] args) throws IOException {
		ApplicationContext context=new ClassPathXmlApplicationContext("spring/applicationContext-test.xml");
		 Person person = (Person) context.getBean("person");
		 System.out.println(person.getUser().getName());

		
	
	}
}
