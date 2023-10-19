
public class WordFreq {
	
	private String word; //key
	private int freq; // number of times its shown

	public WordFreq(String word, int freq) {
		this.word = word; // the string inside the WordFreq
		this.freq = freq; // // the frequency of the word
	} // constructor
	
	public String key() {
		
		return word;
	} // a getter for the word inside the object
	public int getFreq() {
		return freq;
	} // a getter fot the frequency of the word
	public void setFreq(int newfreq) {
		freq = newfreq;
	} // a setter for the frequency of the word
	
	public String toString() {
		return "Word= \""+word+"\" with frequency: "+freq;
	} // a toString to print the object
}
