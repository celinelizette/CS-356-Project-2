package miniTwitter;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;

//This User class implements the Composite Design Pattern, as well as the
//Observable Design Pattern
public class User implements Component, CustomObserver {

	private DefaultListModel listModel = new DefaultListModel();
	private String userID = "";
	private List<String> followers;
	private List<String> followings;
	private List<String> messages;
	private List<String> followingsMessages;

	public User(String id) {
		userID = id;
		followings = new ArrayList<String>();
		followers = new ArrayList<String>();
		messages = new ArrayList<String>();
		followingsMessages = new ArrayList<String>();
	}

	public List<String> getFollowings() {
		return followings;
	}

	public List<String> getFollowers() {
		return followers;
	}

	public ArrayList<String> getFollowingsMessages() {

		return (ArrayList<String>) followingsMessages;
	}

	public void addToFollowings(String string) {
		followings.add(string);
	}

	public void addToFollowers(String string) {
		followers.add(string);
	}

	public void addToMessages(String string) {
		messages.add(string);
	}

	@Override
	public void update(String string) {
		followingsMessages.add(string);
	}

	public ArrayList<String> getMessages() {
		return (ArrayList<String>) messages;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return userID;
	}


}