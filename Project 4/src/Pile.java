import java.util.List;
import java.util.Stack;

public class Pile extends Stack<Card>{
		
	private static final long serialVersionUID = 1L;
	
	
	public Pile() {}
	public Pile(List<Card> cards) {
		for(Card c : cards) {
			this.add(c);
		}
	}
	
	
}
