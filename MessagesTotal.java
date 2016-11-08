package miniTwitter;

public class MessagesTotal implements Procedure {

	@Override
	public int accept(Visitor visitor) {
		// TODO Auto-generated method stub
		return visitor.visit(this);
	}

}
