package pojo;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class JavaCollection {
	 private String name;
    private String[] userName;
    private List<User> userList;    //List集合
    private Set<User> userSets;     //Set集合
    private Map<String,User> userMap; //map集合
    public JavaCollection() {
		super();
	}
	public JavaCollection(String name, String[] userName, List<User> userList, Set<User> userSets,
			Map<String, User> userMap, Properties pp) {
		super();
		this.name = name;
		this.userName = userName;
		this.userList = userList;
		this.userSets = userSets;
		this.userMap = userMap;
		this.pp = pp;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String[] getUserName() {
		return userName;
	}
	public void setUserName(String[] userName) {
		this.userName = userName;
	}
	public List<User> getUserList() {
		return userList;
	}
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	public Set<User> getUserSets() {
		return userSets;
	}
	public void setUserSets(Set<User> userSets) {
		this.userSets = userSets;
	}
	public Map<String, User> getUserMap() {
		return userMap;
	}
	public void setUserMap(Map<String, User> userMap) {
		this.userMap = userMap;
	}
	public Properties getPp() {
		return pp;
	}
	public void setPp(Properties pp) {
		this.pp = pp;
	}
	private Properties pp;    //Properties的使用
}
