import java.util.ArrayList;
import java.util.List;

public class Player {

	private List<Card> cards = new ArrayList<Card>();
	private String name;
	private Label worstCard;
	
	public Player(String name) {
		this.name = name;
	}
	
	public List<Card> getCards(){
		return cards;
	}
	public Card getCard(int loc) {
		return cards.get(loc);
	}
	public void addCard(Card card) {
		cards.add(card);
	}
	public void removeCard(int num) {
		cards.remove(num);
	}
	public String getName() {
		return name;
	}
	
	public String getHand() {
		String list = "";
		for(int i = 0; i < cards.size(); i++) {
			list += "\t"+ (i+1) + ". " + cards.get(i) + "\n";
		}
		return list;
	}
	
	
	public int getMatch() {
		int highest = 1;
		int lowest = 5;
		for(Card c : cards) {
			int times = labelCount(c.getLabel());
			if(times > highest) highest = times;
			if(times < lowest) {
				lowest = times;
				worstCard = c.getLabel();
			}
		}
		return highest;
	}
	

	public int labelCount(Label l) {
		int count = 0;
		for(Card c : cards) {
			if(c.getLabel() == l)
				count++;
		}
		return count;
	}
	
	public Label getWorst() {
		return worstCard;
	}
}
