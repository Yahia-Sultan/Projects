import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Driver {

	public static void main(String[] args) {
		Pile deck = new Pile();
		Pile discard = new Pile();
		
		for(Label l : Label.values()) {
			for(Suit s : Suit.values()) 
				deck.add(new Card(l, s));
		}
		Collections.shuffle(deck);
		
		
		Scanner in = new Scanner(System.in);
		
		List<Player> players = new ArrayList<Player>();
		players.add(new Player("Human"));
		int bots = validInt(in, 1, 3, "How many bots (1 - 3)? ");
		
		for(int i = 0; i < bots; i++) {
			Player bot = new Player("Bot " + (i+1));
			players.add(bot);
		}
		Collections.shuffle(players);
		for(int i = 0; i < 4; i++) {
			for(Player p : players) {
				p.addCard(deck.pop());
			}
		}
		Player winner = null;
		
		while(winner == null) {
			for(Player p : players) {
				if(p.getName() == "Human") {
					System.out.println("Your cards are:");
					System.out.println(p.getHand());
					
					if(discard.size() == 0) {
						System.out.println("The discard pile is currently empty -- you must draw a card");
						System.out.println("You drew the " + deck.peek());
						p.addCard(deck.pop());
					}else if(deck.size() == 0) {
						System.out.println("The deck is empty -- you must draw the " + discard.peek());
						p.addCard(discard.pop());
					}
					else {
						System.out.println("The top card in the discard pile is the " + discard.peek());
						int pick = validInt(in, 1, 2, "Do you want to pick up the " + discard.peek() + " (1) or draw a card (2)? ");
						if(pick == 2) {
							System.out.println("You drew the " +deck.peek());
							p.addCard(deck.pop());
						}else {
							p.addCard(discard.pop());
						}
					}
					
					System.out.println("Now your cards are:");
					System.out.println(p.getHand());
					int dis = validInt(in, 1, 5, "Which one do you want to discard? ");
					discard.add(p.getCard(dis-1));
					p.removeCard(dis-1);
					
				}else { //Bot actions
					if(discard.size() == 0) {
						p.addCard(deck.pop());
						System.out.println("(" + p.getName() + ") I will draw a new card.");
					}else if(deck.size() == 0) {
						System.out.println("(" + p.getName() + ") I will pick up the " + discard.peek());
						p.addCard(discard.pop());
					}
					else {
						int disPull = 1 + p.labelCount(discard.peek().getLabel()); //discard check, gets the amount of matching labels the bot has to the label of the top discard card.
						if(disPull >= p.getMatch()) {
							System.out.println("(" + p.getName() + ") I will pick up the " + discard.peek());
							p.addCard(discard.pop());
						}else {
							p.addCard(deck.pop());
							System.out.println("(" + p.getName() + ") I will draw a new card.");
						}
					}
					p.getMatch();
					for(int i = 0; i < p.getCards().size(); i++) {
						if(p.getCard(i).getLabel() == p.getWorst()) {
							System.out.println("(" + p.getName() + ") I will discard the " + p.getCard(i));
							discard.add(p.getCard(i));
							p.removeCard(i);
						}
					}
				}
				
				if(p.getMatch() == 4) {
					winner = p;
					if(p.getName() == "Human") System.out.println("You won!");
					else System.out.println(p.getName() + " won!");
					break;
				}
			}
		}
		
		
		
		in.close();
	}
	
	public static int validInt(Scanner in, int min, int max, String question) {
		

		int val = min-1;
		while(val < min || val > max) {
			System.out.print(question);
			try {
				val = in.nextInt();
				if(val > max || val < min) {
					System.out.println(val + " is not a valid number!");
					val = min-1;
				}
			}catch(InputMismatchException e) {
				System.out.println("Invalid value!");
				val = min-1;
			}
			in.nextLine();
		}
		
		return val;
	}
	
}
