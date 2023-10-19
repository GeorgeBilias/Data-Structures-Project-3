import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class BST implements WordCounter {
	
	private class TreeNode{
		 WordFreq item; 
		 TreeNode left;// pointer to left subtree
		 TreeNode right; // pointer to right subtree
		 int subtreeSize; //number of nodes in subtree starting at this node
		 
	
		 public TreeNode(WordFreq item) {
			 this.item = item; 
		 } // object constructor
		 public WordFreq getData() {
			 return item;
		 } // a get for the data
		 public TreeNode getLeft() {
			 return left;
		 } // get for the left child of the TreeNode
		 public void setLeft(TreeNode left) {
			 this.left = left;
		 } // a setter for the left child of the TreeNode
		 public TreeNode getRight() {
			 return right;
		 } // get for the right child of the TreeNode
		 public void setRight(TreeNode right) {
			 this.right = right;
		 } // a setter  for the right child of the TreeNode
		 public void setTreeSize(int subtreeSize) {
			 this.subtreeSize = subtreeSize;
		 } // a setter for the subtree size of the TreeNode, to be used for updating
		 
	}
	private TreeNode head; //root of the tree
	private List stopWords; // list of stopwords
	int counter = 0; // a counter so we can get the amount of distinct words
	private void SizeInOrder(TreeNode n) {
		if (n == null) {
			return;
		}
		SizeInOrder(n.left);
		setNewSize(n);
		SizeInOrder(n.right);
	} // this is a traversal we use to update the subtree size of each TreeNode
	
	private void setNewSize(TreeNode n) {
		n.setTreeSize(updateTreeSize(n)-1);
	} // here we call for updating and setting the new subtree size of a TreeNode
	private int updateTreeSize(TreeNode n) {
		if (n == null) {
			return 0;
		}
		return updateTreeSize(n.left)+updateTreeSize(n.right)+1;
	}// this is a method which gives us the subtree size of a TreeNode
	public void insert(String w) {
		
		if(head == null) {
		  WordFreq wf = new WordFreq(w,1);
		  head = new TreeNode(wf);
		  counter++;
		  
		  return;
		} // if there is no head insert the TreeNode in head
		
		TreeNode current = head; // setting the current as head
		
		while(true) {
			
			String currentstring = current.getData().key();
			
			if (currentstring.equalsIgnoreCase(w)) { // if the current word already exists inside the tree
				int currentfreq = current.getData().getFreq();
				current.getData().setFreq(currentfreq + 1); // increase its frequency by one
				return;
			}
			
			if (currentstring.compareToIgnoreCase(w) < 0) { // if the string we want to insert is better than the one we have as current
				
				if(current.getRight() == null) { // if the right child of the TreeNode is null
					WordFreq wf = new WordFreq(w,1);
					current.setRight(new TreeNode(wf)); // insert it as current's right child
					SizeInOrder(head); // update the subtree sizes
					counter++; // new distinct word, increase by one
					return;
				}else {
					current = current.getRight(); // else go more to the right
				}
			}else {
				if(current.getLeft() == null) { // if the left child of the TreeNode is null
					WordFreq wf = new WordFreq(w,1);
					current.setLeft(new TreeNode(wf)); // insert it as current's left child
					SizeInOrder(head); // update the subtree sizes
					counter++; // new distinct word, increase by one
					return;
				}else {
					current = current.getLeft(); // else go more to the left
				}
			}
		}
			
	}
	
	public WordFreq search(String w) {
		
		TreeNode current = head; // set head as current
		
		while(true) {
			
			if(current == null) {
				return null;
			} // if the current reaches a point where its null then return null since the word doesn't exist in the tree
			
			WordFreq currentData = current.getData(); // save in currentData the data of the current
			
			String currentstring = currentData.key(); // save in currentstring the key of the current
			
			if(currentstring.equalsIgnoreCase(w)) { // if found inside the tree
				
				int currentfreq = currentData.getFreq(); // get it's frequency
				//System.out.println(current.subtreeSize);
				if (currentfreq > getMeanFrequency()) { // if its frequency is better than the Average Frequency
					remove(currentstring); // remove it from the tree
					head = insertAsRoot(head,currentData); // insert is as head
					counter++; // increase the distinct word counter by one since it was decreased by one in the removal
					SizeInOrder(head); // update the subtree sizes
					current = head;	// set the head as the current
				}
				
				return currentData; // return the data of the current
			}
			
			if(currentstring.compareToIgnoreCase(w)<0) { // if the string we are searching is better than the one we have as current
				current = current.getRight(); // set as current its right child 
			}else {
				current = current.getLeft(); // else set as current its left
			}
			
		}
	}
	private TreeNode insertAsRoot(TreeNode head, WordFreq wf) {
		
		if(head == null) {
			return new TreeNode(wf); // if the head is null instantly insert it since there are no rotations needed
		}
		
		String wkey = wf.key(); // save the key of the node we want to insert as head
		String hkey = head.getData().key(); // save the key of the current head
		
		if(wkey.compareToIgnoreCase(hkey) < 0) { // if the head's key is better than the one we want to insert as root 
			head.left = insertAsRoot(head.left,wf); // recursive method to the left
			head = rotateRight(head); // go to rotateright of head and set as head
		}else {
			head.right = insertAsRoot(head.right,wf); // else recursive method on the right
			head = rotateLeft(head); // go to rotateleft of head and set as head
		}
		
		
		return head; // return the new head
	}
	private TreeNode rotateRight(TreeNode head) {
		TreeNode w = head.left;
		head.left = w.right;
		w.right = head;
		return w;
	} // rotation right
	private TreeNode rotateLeft(TreeNode head) {
		TreeNode w = head.right;
		head.right = w.left;
		w.left = head;
		return w;
	} // rotation left
	public void remove(String w) {
		
		TreeNode current = head;
		TreeNode parent = null;
		
		while(true) {
			if (current == null) {
				return;
			}
			
			WordFreq currentData = current.getData();
			String currentstring = currentData.key();
			
			if(currentstring.equalsIgnoreCase(w)) {
				break;
			}
			parent = current;
			
			if(currentstring.compareToIgnoreCase(w)<0) {
				current = current.getRight();
			}else {
				current = current.getLeft();
			}
		}
		TreeNode replace = null;
		
		
		if (current.getLeft() == null) {
            replace = current.getRight();
            counter--;
		}else if (current.getRight() == null) {
            replace = current.getLeft();
            counter--;
		}else {
            TreeNode findCurrent = current.getRight();

            while (true) {
                if (findCurrent.getLeft() != null)
                    findCurrent = findCurrent.getLeft();
                else
                    break;
            }
            
            remove(findCurrent.getData().key());

            findCurrent.setLeft(current.getLeft());
            findCurrent.setRight(current.getRight());

            replace = findCurrent;
        }
		

        if (parent == null) {
            head = replace;
            SizeInOrder(head);
        } else {
            if (parent.getLeft() == current) {
            	parent.setLeft(replace);
            	SizeInOrder(head);
            }
                

            if (parent.getRight() == current) {
            	parent.setRight(replace);
            	SizeInOrder(head);
            }
                
        }
	} // remove from labs
	public void load(String filename) {
		
		Scanner in = null; // initializing the scanner to null
		boolean insert = true;
	    try {
		    in = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			System.out.println("Couldn't read or open the file");
		} // trying to read and open the file
		while(in.hasNext()) { // while there is next word
			String w = in.next().toLowerCase();// save the word to w to lowercase
			if (!w.matches(".*\\d.*") && w != "") { // if it doesn't have a number inside it 
				if(w.length()>1) { // if its more than one character
					while(!w.substring(0, 1).matches("\\w")) {
						if(w.length()>1) { // if its still more than one char
						w = w.substring(1,w.length());
						}else {
							break; // else leave the loop
						}
					} // while it starts with a character other than alphabet keep removing it
					while(!w.substring(w.length()-1, w.length()).matches("\\w")) {
						if (w.length()>1) { // if its still more than one char
						w = w.substring(0, w.length()-1);
						}else {
							break; // else leave the loop
						}
					}// while it ends with a character other than alphabet keep removing it
					
					}else { // else check if this one character is not alphabetical
						if (!w.matches("\\w")) {
							insert = false; // set an insert as false to be used
						}
							
					}
				if (w.matches(".*[^A-Za-z'].*")) { // if the final word has any character other than alphabet or ' 
					insert = false; // set insert as false
				}
				
				if (w != "" && inList(w) == false && insert == true) { // if its not nothing, not inside the stopwords list and insert is true
						insert(w); // insert it inside the tree
				}
			}	
			insert = true; // set insert as true for next word
		}
		
	}
	private boolean inList(String w) {
		if (stopWords == null) {
			return false;
		}// if the list is empty return false since its not in there
		Node iterator = stopWords.getHead(); // set as iterator the head of the list
		boolean state = false; // initialize the state as false
		
		while(iterator != null) {
			String iterstr = iterator.getData(); // set as iterstr the data of the iterator
			
			if (iterstr.equalsIgnoreCase(w)) { // if the word we want to insert is found in the list
				state = true; // set state as true 
				break; // and end the while loop
			}
			iterator = iterator.getNext();
		}// while there are still words to look if theyre the word we want to insert
		
		return state;
	} // this is a method that we are going to use to look if a word we want to insert is in the stoplist
	public int getTotalWords() {
		
		if (head == null) {
			return 0;
		} // if head is null return 0 since there are no words
		int total = CalcTotal(head); // else call the CalcTotal and save the sum in total
		
		return total; // return the total
	}
	private int CalcTotal(TreeNode n) {
		int sum = 0;
		int sum_left = 0;
		int sum_right = 0; // initializing the sum, sum at the left and sum at the right
		
		if (n == null) {
			return 0;
		} // if the n is null return 0
		if (n.left != null) {
			sum_left = CalcTotal(n.left);
		} // recursive method to get the sum of the left
		
		if (n.right != null) {
			sum_right = CalcTotal(n.right);
		} // recursive method to get the sum of the right
		
		sum = n.getData().getFreq() + sum_left + sum_right; // setting as sum the sum of left, right and n
		
		return sum; // return the sum
	}
	public int getDistinctWords() {
		
		return counter; // return the counter we set for the distinct words
	}
	public int getFrequency(String w) {
		
		WordFreq wf = search(w); // search for the word and set it as wf, if its not found the search sets it as null
		if (wf == null) { // if not found return 0
			return 0;
		}else { 
			return wf.getFreq(); // else return its frequency																																																
		}
	}
	public WordFreq getMaximumFrequency() {
		if (head == null) {
			return null;
		} // if there is no head there is no WordFreqs to find the max so return null
		
		WordFreq max = findMax(head); // else call a find max and set that WordFreq it returns as max
		
		return max; // and return the max
	}
	private WordFreq findMax(TreeNode n) {
		if (n == null) {
			return null;
		} // if the node we try to start our find max doesnt exist return null/ might not be needed since we always do it from head but anyway
		WordFreq max = n.getData(); // set as max the wordFreq of the first TreeNode
		
		if (n != null) {
			
			int nfreq = n.getData().getFreq(); // set as nfreq the frequency of the n
		
			WordFreq leftwf = findMax(n.left); // recursive method to look for the max on the left of the n
			WordFreq rightwf = findMax(n.right); // recursive method to look for the max on the right of the n
		
			if (leftwf != null) { // if the left of the n isnt null
			
				int leftfreq = leftwf.getFreq(); // set as leftfreq its frequency
			
				if(leftfreq > nfreq) {
				max = leftwf;
				} // compare it with the maxs frequency and if its better set is as max
			}
			if (rightwf != null) {
				int rightfreq = rightwf.getFreq();
			
				if(rightfreq > nfreq) {
				max = rightwf;
				}
			} // the same thing for the  right side of the n
		
		}
		return max; // return the max
	}
	public double getMeanFrequency() {
		
		if (head == null) {
			return 0; // if there is no head, there is no tree, therefore no mean frequency
		}
		double total = getTotalWords(); // get the total amount of words
		double words = getDistinctWords(); // get the distinct amount of words
		
		double mean = total/words; // divide them to get the average
		
		return mean; // return the average
	}
	public void addStopWord(String w) {
		if(stopWords == null) { // if there is no list 
			stopWords = new List(); // create one
		}
		stopWords.insertAtBack(w); // insert the word in the list
	}
	public void removeStopWord(String w) {
		
		Node iterator = stopWords.getHead(); // sets the head as iterator
		Node previous = null; // set the previous as null since it starts from the head
		while(iterator != null) {
			String iterstr = iterator.getData();
			if (iterstr.equalsIgnoreCase(w)) { // if found
				if(previous == null) { // if its in the head
					Node newHead = iterator.next; // saves the next as new head
					stopWords.setHead(newHead); // sets the new head as head
					return;
				}else {
					previous.next = iterator.next; // else sets as the next of the previous as the next of the current iterator
					return;
				}
				
			}else {
				previous = iterator; // else sets as previous the iterator
			}
			iterator = iterator.getNext(); // and sets the iterator as its next
		}// while loop that searches for the word
	} 
	public void printTreeAlphabetically(PrintStream stream) {
		printinOrder(head,stream);
	} // a method that calls the printInOrder to start from the head
	private void printinOrder(TreeNode n,PrintStream stream) {
		if (n == null) {
			return;
		}
		printinOrder(n.left,stream);
		stream.println(n.getData());
		printinOrder(n.right,stream);
	}// a PrintInOrder method to print the tree alphabetically
		
	private static void swap(WordFreq[] tbf, int i, int j) {
		WordFreq temp = tbf[i];
		tbf[i] = tbf[j];
		tbf[j] = temp;
	} // swap method of the quicksort

	private static int partition(WordFreq[] tbf, int low, int high) {
		
		
		WordFreq pivot = tbf[high];
		
		int i = (low - 1);

		for(int j = low; j <= high - 1; j++) {
			
			if (tbf[j].getFreq() < pivot.getFreq())
			{
				i++;
				swap(tbf, i, j);
			}
		}
		swap(tbf, i + 1, high);
		return (i + 1);
	} // partition of the quicksort

	private static void quickSort(WordFreq[] tbf, int low, int high) {
		if (low < high)
		{
			
			int pi = partition(tbf, low, high);
			quickSort(tbf, low, pi - 1);
			quickSort(tbf, pi + 1, high);
		}
	} // quicksort

	
	public void printTreeByFrequency(PrintStream stream) {
		if (head == null) {
			return;
		}
		WordFreq[] tbf = new WordFreq[head.subtreeSize+1];
		insertToArray(head,tbf);
		quickSort(tbf,0,tbf.length-1);
		for(int i = 0; i < tbf.length; i++) {
			
			stream.println(tbf[i]);
		}
		j =0;
	} // creates an array, calls insertToArray to add all data inside that array, quicksorts that array and prints all the data from inside the array
	
	int j = 0;
	private void insertToArray(TreeNode n, WordFreq[] tbf) {
		if (n == null) {
			return;
		}
		
		insertToArray(n.left, tbf);
		tbf[j++] = n.getData();
		insertToArray(n.right, tbf);
	} // recursive method to add all the data inside an array
	
}
