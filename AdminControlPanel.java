package miniTwitter;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

// Implements the Visitor Design Pattern by using visitor interface 
// when any of the bottom four buttons are clicked on. It also represents the SingleTon
// Design Pattern, by stating the constructor to be private, and getting an instance of the
// class in the Drivers class.

public class AdminControlPanel extends JFrame implements Visitor {

	private static AdminControlPanel instance;
	
	private JPanel contentPane;
	
	private static DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
	private static JTree myTree =  new JTree(root);
    private static DefaultTreeModel model = (DefaultTreeModel) myTree.getModel();
	private JTextArea txtrUserId;
	private JTextArea txtrGroupId;
	private static List<User> usersList = new ArrayList<User>();
	private static List<String> userIdsList = new ArrayList<String>();
	private List<Group> groupsList = new ArrayList<Group>();
	

	/**
	 * Launch the application.
	 */
	protected void run(){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminControlPanel frame = new AdminControlPanel();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	private AdminControlPanel() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 551, 302);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(4, 4, 4, 4));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		myTree.setBounds(11, 12, 167, 241);		
		myTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		contentPane.add(myTree);
		
		txtrUserId = new JTextArea();
		txtrUserId.setText("User Id");
		txtrUserId.setBounds(187, 12, 230, 30);
		contentPane.add(txtrUserId);
		
		JButton btnAddUser = new JButton("Add User");
		btnAddUser.addActionListener(new AddUserButtonListener());
		btnAddUser.setBounds(426, 13, 101, 24);
		contentPane.add(btnAddUser);
		
		txtrGroupId = new JTextArea();
		txtrGroupId.setText("Group Id");
		txtrGroupId.setBounds(187, 52, 230, 30);
		contentPane.add(txtrGroupId);
		
		JButton btnAddGroup = new JButton("Add Group");
		btnAddGroup.addActionListener(new AddGroupButtonListener());
		btnAddGroup.setBounds(426, 53, 101, 24);
		contentPane.add(btnAddGroup);
		
		JButton btnOpenUserView = new JButton("Open User View");
		btnOpenUserView.addActionListener(new OpenUserViewButtonListener());
		btnOpenUserView.setBounds(272, 92, 155, 24);
		contentPane.add(btnOpenUserView);
		
		JButton btnShowUserTotal = new JButton("Show User Total");
		btnShowUserTotal.addActionListener(new ShowUserTotalViewButtonListener());
		btnShowUserTotal.setBounds(187, 181, 160, 24);
		contentPane.add(btnShowUserTotal);
		
		JButton btnShowGroupTotal = new JButton("Show Group Total");
		btnShowGroupTotal.addActionListener(new ShowGroupTotalViewButtonListener());
		btnShowGroupTotal.setBounds(370, 182, 146, 25);
		contentPane.add(btnShowGroupTotal);
		
		JButton btnShowMessagesTotal = new JButton("Show Messages Total");
		btnShowMessagesTotal.addActionListener(new ShowMessagesTotalViewButtonListener());
		btnShowMessagesTotal.setBounds(187, 215, 160, 24);
		contentPane.add(btnShowMessagesTotal);
		
		JButton btnShowPositive = new JButton("Show Positive %");
		btnShowPositive.addActionListener(new ShowPositiveButtonListener());
		btnShowPositive.setBounds(369, 215, 145, 24);
		contentPane.add(btnShowPositive);
	}
	
    
	private class ShowUserTotalViewButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			UserTotal userTotal = new UserTotal();
			int total = visit(userTotal);
			JOptionPane.showMessageDialog(null, "Total users = " + total , "",0, null);
			
		}
		
	}
	
	public static AdminControlPanel getInstance(){
		if (instance == null) {
			instance = new AdminControlPanel();
			return instance;
		}
		else return instance;
	}
	

	private class ShowGroupTotalViewButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			GroupTotal groupTotal = new GroupTotal();
			int total = visit(groupTotal);		
			JOptionPane.showMessageDialog(null, "Total groups = " + total , "",0, null);
		}
		
	}
	

	private class ShowMessagesTotalViewButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			MessagesTotal messagesTotal = new MessagesTotal();
			int total = visit(messagesTotal);
			JOptionPane.showMessageDialog(null, "Total messages = " + total , "",0, null);
		}
		
	}
	

	private class ShowPositiveButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			PositivePercentage positiveTotal = new PositivePercentage();
			int total = visit(positiveTotal);
			JOptionPane.showMessageDialog(null, "Total positive messages = " + total , "",0, null);
			
		}
		
	}
	

	private class AddUserButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) { 
			

			if(!txtrUserId.getText().isEmpty()) {
				  
				  String userId = txtrUserId.getText();
		
			      User user = new User(userId);
				  usersList.add(user);

			      DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) myTree.getLastSelectedPathComponent();
		          
		          if (userIdsList.contains(userId)) {
		        	  JOptionPane.showMessageDialog(null, "Sorry that user already exists" , "",0, null);
		          }
		          else {
		        
		        	  if (myTree.isSelectionEmpty()) 
		        	  {
			        	  userIdsList.add(userId);
			        	  addUser(userId, root);
			        	  model.reload();
		        	  } else {
		        		  if (selectedNode.getChildCount() > 0) {
		        			  addUser(userId, selectedNode);
		        			  model.reload();
		        			  if(!userIdsList.contains(userId)) {
		        				  usersList.add(new User(userId));
		        				  userIdsList.add(userId);
		        				  addUser(userId, root);
		        				  model.reload();
		        				  
		        			  }
		        		  } else {
		        			  addUser(userId, root);
		        			  model.reload();
		        		  }
		        	  }
		          }
		        	  
				      
			  }
		}
		
	}
	

	private class AddGroupButtonListener implements ActionListener {

		@Override
			public void actionPerformed(ActionEvent e) {
			
			    Boolean doesNotExist = true;
			    String groupName = txtrGroupId.getText();
			    for (int i = 0; i < groupsList.size(); i++) {
			    	if(groupsList.get(i).getId().equals(groupName)){
			    		  doesNotExist = false;
				    	  JOptionPane.showMessageDialog(null, "Sorry, group already exists" , "",0, null);
				    }
			    }
			    if(doesNotExist) {
			    	if (myTree.isSelectionEmpty()) {
				    	DefaultTreeModel model = (DefaultTreeModel) myTree.getModel();
				    	addGroup(groupName, root);
				    	model.reload();
			    	}
			    	else {
			    		 DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) myTree.getLastSelectedPathComponent();
			    		 if(selectedNode.getChildCount() > 0) {
				    		 addGroup(groupName, selectedNode);
				    		 model.reload();
			    		 }
			    		 else{
			    			 addGroup(groupName, root);
			    			 model.reload();
			    		 }
			    	}
			        groupsList.add(new Group(groupName));
			    }
			}
	}
		

	private static DefaultMutableTreeNode addUser(String userName, DefaultMutableTreeNode parentNode){
		
	    DefaultMutableTreeNode newUser = new DefaultMutableTreeNode(userName);
	    newUser.setUserObject(userName);
		parentNode.add(newUser);

		   return newUser;
	}
	

	private class OpenUserViewButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

      	  if (!myTree.isSelectionEmpty()) 
      	  {
	          DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) myTree.getLastSelectedPathComponent();
	          String username = (String) selectedNode.getUserObject();
	          
	          User thisUser = new User(username); 
	          UserViewFrame userViewFrame = new UserViewFrame(thisUser);
	          userViewFrame.setVisible(true);
      		  
      	  }
			
		}
		
	}

    private DefaultMutableTreeNode addGroup(String groupName, DefaultMutableTreeNode parentNode){
	   
	   DefaultMutableTreeNode newUser = new DefaultMutableTreeNode(groupName);
	   DefaultMutableTreeNode defaultUser = new DefaultMutableTreeNode();
	   defaultUser.setUserObject("(Default User)");
	   newUser.setUserObject(groupName + (" [Group]"));
	   parentNode.add(newUser);
	   newUser.add(defaultUser);
	   
	   return newUser;
	}
	
	public static ArrayList<User> getUsersList() {
		return (ArrayList<User>) usersList;
	}
	public static ArrayList<String> getUserIdsList() {
		return (ArrayList<String>) userIdsList;
	}
	
	
	public static void addToUsersList(User u) {
		addUser(u.getId(), root);
		usersList.add(u);
		model.reload();
	}


	public int visit(UserTotal total) {
		
		return root.getLeafCount();
		
	}

	
	public int visit(MessagesTotal totalMessages) {
		User newUser = null;
		int messagesTotal = 0;
		String newUserName;
		
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) root;
		if (root.getChildCount() <= 0) {
			return 0;
		}
		
		for (int i = 0; i < root.getChildCount(); i++) {
			node = (DefaultMutableTreeNode) root.getChildAt(i);
			if(node.getChildCount() == 0) {
				newUserName = (String) node.getUserObject();
				for(int j = 0; j < usersList.size(); j++) {
					if (newUserName.equals(usersList.get(i).getId())) {
						newUser = usersList.get(i);
					}
				}
				messagesTotal = messagesTotal + newUser.getMessages().size();
			}
		}
		return messagesTotal;
	}

	public int visit(GroupTotal totalGroups) {
		
		int groupTotal = 0;
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) root;
		if (root.getChildCount() <= 0) {
			return 0;
		}
		
		for (int i = 0; i < root.getChildCount(); i++) {
			node = (DefaultMutableTreeNode) root.getChildAt(i);
			if (node.getChildCount() > 0) 
				groupTotal++;
		}
		return groupTotal;
	}

	
	public int visit(PositivePercentage positive) {
		
		String[] positiveWords = {"yay", "great", "good", "ecstatic", "happy", "joy", "wonderful", "pleased"
				, "rich", "delighted", "amazing", "satisfied"};
		ArrayList<String> positiveWordsList = new ArrayList<String>();
		for (int i = 0; i < positiveWords.length; i++) {
			positiveWordsList.add(positiveWords[i]);
		}
		
		String[] temp;
		String username = "";
		User newUser;
		int count = 0;

		DefaultMutableTreeNode node = (DefaultMutableTreeNode) root;
		if (root.getChildCount() <= 0) {
			return 0;
		}
		
		for(int i = 0; i <root.getChildCount(); i++) {
			node = (DefaultMutableTreeNode) root.getChildAt(i);
			username = (String) node.getUserObject();
			newUser = new User(username);
			for(int j = 0; j < newUser.getMessages().size(); j++) {
				temp = newUser.getMessages().get(j).split(" ");
				for(int k = 0; k < temp.length; k++) {
					if(positiveWordsList.contains(temp[i])) {
						count++;
						break;
					}
				}
			}
		}
		return count;
	}
	
}