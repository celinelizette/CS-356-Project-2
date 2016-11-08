package miniTwitter;

import java.util.ArrayList;
import java.util.List;
//This class is the component part of the Composite Design Interface

public class Group implements Component {

	private String groupId = "";
	private List<Component> usersList;
	private List<Component> groupsList;

	public Group(String name) {
		groupId = name;
	    usersList = new ArrayList<Component>();
		groupsList = new ArrayList<Component>();
	}
	public void addUser(User user) {
		
		usersList.add(user);
	}
	
	public void addGroup(Group group) {
		groupsList.add(group);
	}
	
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return groupId;
	}
	


}
