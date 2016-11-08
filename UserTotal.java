package miniTwitter;

public class UserTotal implements Procedure {
//represents one of the "element" interface of the Visitor Design Pattern
	
	@Override
	public int accept(Visitor visitor) {
		// TODO Auto-generated method stub
		return visitor.visit(this);
	}

}
