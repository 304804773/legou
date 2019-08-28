package pojo;

public class Person {
	private User user;

	public Person() {
		super();
	}

	public Person(User user) {
		super();
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
