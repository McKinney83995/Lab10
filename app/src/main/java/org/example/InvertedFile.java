package org.example;

import java.util.LinkedList;

public class InvertedFile {
    private class WordNode {

        private final String key;
        private LinkedList<Integer> indexes;
        WordNode next;
        // private int place; // holds the wordnodes place in the array-tree for easier
        // operations

        // SuppressWarnings("unchecked")
        WordNode(String word, Integer wordIndex) {
            key = word;
            indexes = new LinkedList<>();
            indexes.push(wordIndex);
        }

        public void insertIndex(Integer index) {
            indexes.push(index);
            return;
        }

        public String getWord() {
            return key;
        }

        public String getIndexes() {
            String output = "";
            for (Integer index : indexes) {
                output += index.toString();
                output += ", ";
            }

            return output;
        }

    }

    private int capacity, size; // use capacity to determine size of hash table
    private WordNode root;
    private LinkedList<WordNode>[] table; // used to locate

    @SuppressWarnings("unchecked")
    InvertedFile(long fileSizeBytes) { // constructor
        capacity = (int) Math.ceil(fileSizeBytes / 6); // number of bytes divided by 5 for avg word length
        table = new LinkedList[capacity];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void insert(String key, int value) {
        int bucket = hashify(key);

        // first -- does key already exists in hash table?
        if (table[bucket] == null) { // no, it does not
            table[bucket] = new LinkedList<>(); // create new linked list entry
        } else {
            for (int i = 0; i < table[bucket].size(); ++i) { // iterate through entries until you get to one that is
                                                             // equal to the key
                if (table[bucket].get(i).getWord().compareTo(key) == 0) {
                    table[bucket].get(i).insertIndex(value); // add index in, then were done
                    return;
                }
            }
        }

        // if it doesnt --
        WordNode newWord = new WordNode(key, value); // create new word node
        table[bucket].add(newWord); // add to hash table at end of list in bucket

        insertNew(newWord);

        ++size;
        return;
    }

    public String getAllOfThem() {
        if (size == 0) {
            return null;
        }
        String output = "Words\tIndexes\n----------------------------------------";
        output += "\n Size: ";
        output += size;

        for (WordNode current = root; current.next != null; current = current.next) {// will this skip the last one??
            output += "\n" + current.getWord() + "\t" + current.getIndexes();
        }

        return output;

    }

    private void insertNew(WordNode newWord) {

        if (size == 0) {
            root = newWord;
            root.next = null;
            return;
        } else if (root.getWord().compareTo(newWord.getWord()) > 0) {
            newWord.next = root;
            root = newWord;
            return;
        }

        WordNode current = root;
        while (current.next != null) {
            if (current.next.getWord().compareTo(newWord.getWord()) > 0) {
                newWord.next = current.next;
                current.next = newWord;
                return;
            } else
                current = current.next;
        }

        current.next = newWord;
        newWord.next = null;
        return;

    }

    private int hashify(String key) {
        return compress((key.hashCode()));
    }

    private int compress(int hashified) {
        return (int) Math.abs(hashified % capacity);
    }

}