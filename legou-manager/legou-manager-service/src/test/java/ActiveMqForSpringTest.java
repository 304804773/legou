//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.List;
//import java.util.Map;
//
//import javax.jms.JMSException;
//import javax.jms.Message;
//import javax.jms.Queue;
//import javax.jms.Session;
//
//import org.apache.activemq.ActiveMQConnectionFactory;
//import org.junit.Test;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jms.core.JmsTemplate;
//import org.springframework.jms.core.MessageCreator;
//
//import com.legou.pojo.TbContent;
//
//public class ActiveMqForSpringTest {
//	// @Test
//	public void sendQueue() {
//		// 加载配置文件，创建spring容器，容器自动解析放在容器中的bean
//		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
//				"classpath:spring/applicationContext-mq.xml");
//		// spring容器按照类型创建bean并放到容器中
//		JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);
//		// 创建点对点
//		Queue queue = (Queue) applicationContext.getBean("activeMQQueue");
//		// 使用模板发送把用匿名内部类创建的消息发送到点对点中
//		jmsTemplate.send(queue, new MessageCreator() {
//			@Override
//			public Message createMessage(Session session) throws JMSException {
//				// 会话创建文本消息
//				return session.createTextMessage("wawawa");
//			}
//		});
//
//	}
//
//	// @Test
//	public void getItem() {
//		// 加载配置文件，创建spring容器，容器自动解析放在容器中的bean
//		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
//				"classpath:spring/applicationContext-dao.xml");
//		JdbcTemplate jdbcTemplate = (JdbcTemplate) applicationContext.getBean("jdbcTemplate");
////		String sql="select title from tb_content where id=40";
////		String s = jdbcTemplate.queryForObject(sql, String.class);
//		String sql = "select * from tb_content";
//		List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(sql);
//		System.out.println(queryForList.get(0));
//
//	}
//
//	// @Test
//	public void reverse() {
//		int[] arr = new int[] { 34, 5, 22, -98, 6, -76, 0, -3 };
//		int[] reverse = arr;
//		for (int i = 0; i < arr.length / 2; i++) {
//			int temp = arr[i];
//			reverse[i] = arr[arr.length - i - 1];
//			reverse[arr.length - i - 1] = temp;
//		}
//		for (int i : reverse) {
//			System.out.print(i + "  ");
//		}
//	}
//
//	// @Test
//	public void reverse2() {
//		int[] arr = new int[] { 34, 5, 22, -98, 6, -76, 0, -3 };
//		List<Integer> reverse = new ArrayList<>();
//		for (int i : arr) {
//			reverse.add(i);
//		}
//		Collections.reverse(reverse);
//		for (Integer integer : reverse) {
//			System.out.print(integer + "  ");
//		}
//	}
//
//	@Test
//	public void search() {
//		int[] arr = new int[] { 34, 5, 22, -98, 6, -76, 0, -3 };
//		int temp = 23;
//		for(int i=0;i<arr.length;i++) {
//			if (arr[arr.length-1]!=temp) {
//				if(arr[i]==temp) {
//					System.out.println(i);
//					break;
//				}else if(i==arr.length-1) {
//					System.out.println("没找到");
//				}
//			}else {
//				System.out.println(arr.length-1);
//			}
//			
//		}
//	}
//}
