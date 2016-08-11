package solitaire;

import java.io.IOException;
import java.util.Scanner;
import java.util.Random;
import java.util.NoSuchElementException;

/**
 * This class implements a simplified version of Bruce Schneier's Solitaire Encryption algorithm.
 * 
 * @author RU NB CS112
 */
public class Solitaire {
	
	/**
	 * Circular linked list that is the deck of cards for encryption
	 */
	CardNode deckRear;
	
	/**
	 * Makes a shuffled deck of cards for encryption. The deck is stored in a circular
	 * linked list, whose last node is pointed to by the field deckRear
	 */
	public void makeDeck() {
		// start with an array of 1..28 for easy shuffling
		int[] cardValues = new int[28];
		// assign values from 1 to 28
		for (int i=0; i < cardValues.length; i++) {
			cardValues[i] = i+1;
		}
		
		// shuffle the cards
		Random randgen = new Random();
 	        for (int i = 0; i < cardValues.length; i++) {
	            int other = randgen.nextInt(28);
	            int temp = cardValues[i];
	            cardValues[i] = cardValues[other];
	            cardValues[other] = temp;
	        }
	     
	    // create a circular linked list from this deck and make deckRear point to its last node
	    CardNode cn = new CardNode();
	    cn.cardValue = cardValues[0];
	    cn.next = cn;
	    deckRear = cn;
	    for (int i=1; i < cardValues.length; i++) {
	    	cn = new CardNode();
	    	cn.cardValue = cardValues[i];
	    	cn.next = deckRear.next;
	    	deckRear.next = cn;
	    	deckRear = cn;
	    }
	}
	
	/**
	 * Makes a circular linked list deck out of values read from scanner.
	 */
	public void makeDeck(Scanner scanner) 
	throws IOException {
		CardNode cn = null;
		if (scanner.hasNextInt()) {
			cn = new CardNode();
		    cn.cardValue = scanner.nextInt();
		    cn.next = cn;
		    deckRear = cn;
		}
		while (scanner.hasNextInt()) {
			cn = new CardNode();
	    	cn.cardValue = scanner.nextInt();
	    	cn.next = deckRear.next;
	    	deckRear.next = cn;
	    	deckRear = cn;
		}
	}
	
	/**
	 * Implements Step 1 - Joker A - on the deck.
	 */
	void jokerA() {
		// COMPLETE THIS METHOD
		
		CardNode JokerA;
		
		if (deckRear == null){
			return;
		}
		
		JokerA = deckRear;
		//Loop through until '27' is found
		do {
			if (JokerA.cardValue == 27){
				break;
			}
			JokerA = JokerA.next;
		}
		while (JokerA != deckRear);
		
		//switch Joker with corresponding card
		int temp = JokerA.cardValue;
		JokerA.cardValue = JokerA.next.cardValue;
		JokerA.next.cardValue = temp;
		
		System.out.println("JokerA");
		printList(deckRear);
		return;
		
	}
	
	/**
	 * Implements Step 2 - Joker B - on the deck.
	 */
	void jokerB() {
	    // COMPLETE THIS METHOD
		CardNode JokerB;
		
		if (deckRear == null){
			return;
		}
		
		JokerB = deckRear;
		int tbd = 2;
		
		do {
			if (JokerB.cardValue == 28){
				while(tbd > 0){
					int temp = JokerB.cardValue;
					JokerB.cardValue = JokerB.next.cardValue;
					JokerB.next.cardValue = temp;
					tbd--;
					if(tbd == 1){
						temp = JokerB.next.cardValue;
						JokerB.next.cardValue = JokerB.next.next.cardValue;
						JokerB.next.next.cardValue = 28;
						tbd--;
					}
					if(tbd == 0){
						break;
					}
				}
			}
			if(tbd == 0){
				break;
			}
			JokerB = JokerB.next;
		}
		while (JokerB != deckRear);
		
		System.out.println("Joker B");
		printList(deckRear);
		return;
	}
	
	/**
	 * Implements Step 3 - Triple Cut - on the deck.
	 */
	void tripleCut() {
		// COMPLETE THIS METHOD
//		CardNode last = deckRear;
//		CardNode a = deckRear;
		
		if(deckRear == null){
	    	throw new NoSuchElementException();
		}
		
		//First Joker is found here
		CardNode firstJ = deckRear;
		do {
			if (firstJ.cardValue == 27){
				break;
			}
			firstJ = firstJ.next;
		}while (firstJ != deckRear);
		
		
		//Second Joker is Found here
		CardNode secondJ = deckRear;
		
		do {
			if (secondJ.cardValue == 28){
				break;
			}
			secondJ = secondJ.next;
		}while (secondJ != deckRear);
		
		//checks to see if 28 is first or last.
		boolean twentyEightFirst = false;
		for (CardNode ptr = secondJ; ptr != deckRear; ptr = ptr.next){
			if (ptr.cardValue == 27){
				twentyEightFirst = true;
				break;
			}
		}
		//If first joker is in the front
		if (deckRear.next.cardValue == 27){
			deckRear = secondJ;
			deckRear.next = secondJ.next;
			System.out.println("TripleCut");
			printList(deckRear);
			return;
		}
				
		//If second Joker is in the front
		else if(deckRear.next.cardValue == 28){
			deckRear = firstJ;
			deckRear.next = firstJ.next;
			System.out.println("TripleCut");
			printList(deckRear);
			return;
		}
		
		//If first Joker is rear
		else if(deckRear.cardValue == 27){
			CardNode ptr = secondJ.next;
			CardNode prev = null;
			while(ptr != secondJ){
				prev = ptr;
				ptr = ptr.next;
			}
			deckRear = prev;
			System.out.println("TripleCut");
			printList(deckRear);
			return;
		}
		
		//If second Joker is rear
		else if(deckRear.cardValue == 28){
			CardNode ptr = firstJ.next;
			CardNode prev = null;
			while(ptr != firstJ){
				prev = ptr;
				ptr = ptr.next;
			}
			deckRear = prev;
			System.out.println("TripleCut");
			printList(deckRear);
			return;
		}
		
		
		else if (deckRear.next.cardValue == 27 && deckRear.cardValue == 28){
			System.out.println("TripleCut");
			printList(deckRear);
			return;
		}
		else{
			//triple cut is performed
			CardNode prev = deckRear;
			CardNode curr = deckRear.next;
			
			while(curr!=deckRear)
			{
				if(curr.cardValue == 27 || curr.cardValue == 28)
				{
					firstJ = curr;
					CardNode curr2 = curr.next;
					while(curr2!=deckRear.next)
					{
						if(curr2.cardValue == 27 || curr2.cardValue == 28)
						{
							secondJ = curr2;
							CardNode afterSecond = curr2.next;
							CardNode head = deckRear.next;
							deckRear.next = firstJ;
							secondJ.next = head;
							deckRear = prev;
							deckRear.next = afterSecond;
							System.out.println("TripleCut");
							printList(deckRear);
							return;
						}
						else
						{
							curr2 = curr2.next;
						}
					}
				}
				else
				{
					prev = prev.next;
					curr = curr.next;
				}
			}
			return;
			
		}
		
	}
	
	/**
	 * Implements Step 4 - Count Cut - on the deck.
	 */
	void countCut() {		
		// COMPLETE THIS METHOD
		int lastVal = deckRear.cardValue; 
		int count = 0;
		
		if (deckRear.cardValue == 28){
			count = 27;
		}
		else {
			count = deckRear.cardValue;
		}
		
		CardNode prev = null;
		CardNode ptr = deckRear.next;
		
		while(ptr != deckRear){
			prev = ptr;
			ptr = ptr.next;
		}
		
		prev.next = deckRear.next;
		deckRear = prev;
		
		CardNode newPrev = deckRear;
		
		for (int i=0; i<count; i++){
			newPrev = newPrev.next;
		}
		
		CardNode newLast = new CardNode();
		newLast.cardValue = lastVal;
		
		newLast.next = newPrev.next;
		newPrev.next = newLast;
		deckRear = newLast;
		
		System.out.println("CountCut");
		printList(deckRear);
		return;
		
		
	}
	
	/**
	 * Gets a key. Calls the four steps - Joker A, Joker B, Triple Cut, Count Cut, then
	 * counts down based on the value of the first card and extracts the next card value 
	 * as key. But if that value is 27 or 28, repeats the whole process (Joker A through Count Cut)
	 * on the latest (current) deck, until a value less than or equal to 26 is found, which is then returned.
	 * 
	 * @return Key between 1 and 26
	 */
	int getKey() {
		// COMPLETE THIS METHOD
		jokerA();
		jokerB();
		tripleCut();
		countCut();
		int firstVal=deckRear.next.cardValue;
		int c = -1;
		int counter = 1;
		
		if (firstVal == 28){
			firstVal = 27;
		}
		
		CardNode ptr = deckRear.next;
		while(ptr != deckRear){
			
			if(counter == firstVal){
				if(ptr.next.cardValue == 27 || ptr.next.cardValue == 28)
				{
					jokerA();
					jokerB();
					tripleCut();
					countCut();
					//printList(deckRear);
					ptr = deckRear;
					counter = 0;
					
					firstVal = deckRear.next.cardValue;

					if(firstVal == 28)
					{
						firstVal = 27;
					}
				}
				else
				{
					c = ptr.next.cardValue;
					//printList(deckRear);
					System.out.println(c);
					return c;
				}
			}
			ptr = ptr.next;
			counter++;
		}
		System.out.println(c);
	    return c;
	}
	
	/**
	 * Utility method that prints a circular linked list, given its rear pointer
	 * 
	 * @param rear Rear pointer
	 */
	private static void printList(CardNode rear) {
		if (rear == null) { 
			return;
		}
		System.out.print(rear.next.cardValue);
		CardNode ptr = rear.next;
		do {
			ptr = ptr.next;
			System.out.print("," + ptr.cardValue);
		} while (ptr != rear);
		System.out.println("\n");
	}

	/**
	 * Encrypts a message, ignores all characters except upper case letters
	 * 
	 * @param message Message to be encrypted
	 * @return Encrypted message, a sequence of upper case letters only
	 */
	public String encrypt(String message) {	
		// COMPLETE THIS METHOD
	    // THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE
		String encrypt = ""; 
		String result = "";
		
		message = message.toUpperCase();
		
		for(int i = 0; i < message.length(); i++){
			if (Character.isLetter(message.charAt(i))){
				result += message.charAt(i);
			}
		}
		
		printList(deckRear);
		for(int i = 0; i < result.length(); i++){
			char chare = result.charAt(i);
			int k = chare-'A' + 1;
			k += getKey();
			//System.out.println(k);
			if(k > 26){
				k -= 26;
			}
			chare = (char) (k +'A'-1);
			encrypt += chare;
		}
	    return encrypt;
	}
	
	/**
	 * Decrypts a message, which consists of upper case letters only
	 * 
	 * @param message Message to be decrypted
	 * @return Decrypted message, a sequence of upper case letters only
	 */
	public String decrypt(String message) {	
		// COMPLETE THIS METHOD
	    // THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE
		String decrypt = "";

		int k; 
		int x;
		int pos;
		
		for(int i = 0; i < message.length(); i++){
			char chare = message.charAt(i);
			x = chare - 'A' + 1;
			k = getKey();
			
			if( x <= k ){
				x += 26;
			}
			
			pos = x - k;
			chare = (char)(pos-1+'A');
			decrypt += chare;
		}
		
	    return decrypt;
	}
}
