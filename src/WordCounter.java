import java.io.PrintStream;

public interface WordCounter {
	void insert(String w); // insert a new word
	WordFreq search(String w); // search for a word inside the tree and if it has better frequency than average insert it as head
	void remove(String w); // remove a word from the tree if it exists
	void load(String filename); // load a file and insert its words in the tree
	int getTotalWords(); // get the total amount of words inside the file
	int getDistinctWords(); // get the distinct amount of words inside the file
	int getFrequency(String w); // get the frequency of a specific word
	WordFreq getMaximumFrequency(); // get the maximum frequency from inside the file
	double getMeanFrequency(); // get the average frequency of the file
	void addStopWord(String w); // add a stopword to the list
	void removeStopWord(String w); // remove a stopword from the list
	void printTreeAlphabetically(PrintStream stream); // print the tree in alphabetical order
	void printTreeByFrequency(PrintStream stream); // print the tree based by the words frequencies in ascending order
}
