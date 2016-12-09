package sentinal;

import java.util.LinkedList;

public class PhraseHash implements PhraseHashInterface {

    // -----------------------------------------------------------
    // Fields
    // -----------------------------------------------------------
    
    private final static int BUCKET_COUNT = 1000;
    private int size, longest;
    private LinkedList<String>[] buckets;
    
    
    // -----------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------
    
    @SuppressWarnings("unchecked") // Don't worry about this >_>
    PhraseHash () {
        size = 0;
        longest = 0;
        buckets = new LinkedList[BUCKET_COUNT];
    }
    
    
    // -----------------------------------------------------------
    // Public Methods
    // -----------------------------------------------------------
    
    public int size () {
        return size;
    }
    
    public boolean isEmpty () {
        return size == 0;
    }
    
    public void put (String s) {
    	int position = forneyHash(s);
		if (buckets[position] == null) {
			buckets[position] = new LinkedList<String>();
		}
		if (buckets[position].contains(s)) {
			return;
		} else {
			buckets[position].add(s);
			size++;
			longest = longest < getWordCount(s) ? getWordCount(s) : longest;
		}
    }
    
    public String get (String s) {
    	if (isEmpty()) {return null;}
    	int position = forneyHash(s);
    	//System.out.println("Current phrase: " + s);
		//System.out.println("Current Position: " + position);
		if (buckets[position] == null || !buckets[position].contains(s)) {
	    	return null;
		} else {
			return s;
		}
    }
    
    public int longestLength () {
        return longest;
    }
    
    
    // -----------------------------------------------------------
    // Helper Methods
    // -----------------------------------------------------------
    
    /*
     * 'Uniformly' distributes strings by utilizing the unique ascii values
     * of the given strings and multiplying by a large arbitrary prime value
     */
    private int forneyHash (String s) {
    	int largePrime = 129;//seems good enough
    	int asciiVal = 0;
    	for (int i = 0; i < s.length(); i++) {
    		asciiVal += s.charAt(i);
    	}
    	return (asciiVal * largePrime) % BUCKET_COUNT;
    }
    
    /*
     * returns the position of a given key in the bucket array
     * using a hash function that positions the strings based on
     * number of words and the length of the entire string sorted into
     * a neatly organized even distribution
     * throws an error on empty string input
     * NOTE: Depricated! (See above)
     */
    /*private int hash (String s) {
        int numLetters = getLetterLength(s);
        if (numLetters == 0) {throw new IllegalArgumentException("Given phrase is empty.");}
        int[] range = getRange(s);
        int median = 6;
        int intervalPos = 0;
        if (range[0] == 0) {//set the position of the word within the interval
        	intervalPos = numLetters - 2;
        } else if (range[0] == 333) {
        	intervalPos = numLetters - 5;
        } else if (range[0] == 666) {
        	intervalPos = numLetters - 9;
        }
        double result = (intervalPos / (double) median);//represents the positon of word in bell curve
        result *= ((range[1] - range[0])/2);//assigns the position using the middle as reference
        result = Math.ceil(result);
        result = result % range[1];//wrap the value if it is out-of-range
        result += range[0];//finally add by the amount of offset
        return (int)result;
    }
    
    /*
     * Returns the appropriate range for number of words in the phrase
     * throws an error is phrase is empty string
     */
    /*private int[] getRange (String s) {
    	int[] range = new int[2];
    	int numWords = getWordCount(s);
        if (numWords == 0) {
        	throw new IllegalArgumentException("Given phrase is empty.");
        } else if (numWords == 1) {
        	range[0] = 0;
        	range[1] = 332;
        } else if (numWords == 2) {
        	range[0] = 333;
        	range[1] = 665;
        } else {
        	range[0] = 666;
        	range[1] = 999;
        }
    	return range;
    }
    
    /*
     * returns the total number of letters in a phrase
     * disregarding spaces
     */
    /*private int getLetterLength (String s) {
    	String[] words = s.split(" ");
    	int result = 0;
        for (String str : words) {
        	result += str.length();
        }
    	return result;
    }
    
    /*
     * returns the number of words in the phrase
     */
    private int getWordCount (String s) {
    	return s.split(" ").length;
    }
    
    /*
     * used to increment the position of our put and get methods
     * wraps back to first point in range if it increments out of range
     */
    /*private int incrementPos (int currPos, int[] range) {
    	currPos++;
    	if (currPos > range[1]) {
    		return range[0];
    	}
    	return currPos;
    }*/
}