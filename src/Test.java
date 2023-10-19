import java.util.Scanner;

public class Test {

	public static void main(String[] args) {
		BST bst = new BST();
		
		Scanner in = new Scanner(System.in);
		boolean state = false;
		Scanner in2 = new Scanner(System.in);
		
		while(state == false) {
			System.out.println("------------------------------------");
			System.out.println("1. Search\n2. Remove\n3. Get Total Amount of Words\n4. Get amount of Distinct Words\n5. Get the frequency of a word\n6. Get the word with the Maximum Frequency from inside the file\n7. Get the Average Frequency from the file\n8. Add a Stop Word to the List\n9. Remove a stop word from the list\n10. Print the tree alphabetically\n11. Print the tree based on Frequency\n12. Insert a word\n13. Load a file\n14. Exit");
			System.out.println("------------------------------------");
			int option = in.nextInt();
			
			if(option == 1) {
				System.out.print("Type a word you want to search: ");
				String word = in2.next();
				WordFreq wf = bst.search(word);
				System.out.println(wf);
			}else if(option == 2) {
				System.out.print("Type a word you want to remove: ");
				String word = in2.next();
				bst.remove(word);
			}else if(option == 3) {
				int tw = bst.getTotalWords();
				System.out.println("Total amount of words: "+tw);
			}else if(option == 4) {
				int dw = bst.getDistinctWords();
				System.out.println("Distinct amount of words: "+dw);
			}else if(option == 5) {
				System.out.print("Type a word that you want to get the frequency for: ");
				String word = in2.next();
				int fr = bst.getFrequency(word);
				System.out.println("The frequency of the word is: "+fr);
			}else if(option == 6) {
				WordFreq max = bst.getMaximumFrequency();
				System.out.println(max);
			}else if(option == 7) {
				double mean = bst.getMeanFrequency();
				System.out.println(mean);
			}else if(option == 8) {
				System.out.print("Type a word you want to add as a stop word: ");
				String word = in2.next();
				bst.addStopWord(word);
			}else if(option == 9) {
				System.out.print("Type a word you want to remove from a stop word: ");
				String word = in2.next();
				bst.removeStopWord(word);
			}else if(option == 10) {
				bst.printTreeAlphabetically(System.out);
			}else if(option == 11) {
				bst.printTreeByFrequency(System.out);
			}else if(option == 12) {
				System.out.print("Type a word you want to insert: ");
				String word = in2.next();
				bst.insert(word);
			}else if(option == 13) {
				bst.load("C:\\Users\\Αλέξης\\Desktop\\3200278_3200268\\src\\filename.txt");// path
			}else if(option == 14) {
				state = true;
			}
		}
		in.close();
		in2.close();
	}

}
