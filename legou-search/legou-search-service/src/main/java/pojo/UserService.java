package pojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cglib.core.TinyBitSet;

public class UserService {
	@Autowired
	@Qualifier("user2")
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public UserService(User user) {
		super();
		this.user = user;
	}

	public UserService() {
		super();
	}

	public void test() {
		System.out.println("set注入");
	}


}
