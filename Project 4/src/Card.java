
public class Card {

	private Label label;
	private Suit suit;
	
	public Card(Label label, Suit suit) {
		this.label = label;
		this.suit = suit;
	}
	
	public Label getLabel() {
		return label;
	}
	public void setLabel(Label s) {
		label = s;
	}
	
	public Suit getSuit() {
		return suit;
	}
	public void setSuit(Suit s) {
		suit = s;
	}
	
	public String toString() {
		return label + " of " + suit; 
	}
}
