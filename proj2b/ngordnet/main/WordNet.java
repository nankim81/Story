package ngordnet.main;

import java.sql.Array;
import java.util.*;

import edu.princeton.cs.algs4.In;

public class WordNet {
    private HashMap<List<String>, Integer> wordsToWordID;
    private HashMap<Integer, List<Integer>> hypoRelationship;
    private HashMap<Integer, List<String>> wordIDToWords;

    public WordNet(String synsetFileName, String hyponymFileName) {
        /**How do I handle multiple words for one wordID? Using single array or single map didn't work out.
         * My third approach is multiple maps.
         * Possible approaches that I am considering now: one map for wordID and word, one map for hyponyms, and try to
         * build graph by combining data in two maps.
         */
//        graph = new Graph();
        In synsetFile = new In(synsetFileName);
        In hyponymFile = new In(hyponymFileName);
        wordsToWordID = new HashMap<>();
        hypoRelationship = new HashMap<>();
        wordIDToWords = new HashMap<>();
        while (synsetFile.hasNextLine()) {
            String[] line = synsetFile.readLine().split(",");
            Integer firstSynsetItem = Integer.parseInt(line[0]);
            String[] secondSynsetItem = line[1].split(" ");
            List myList = Arrays.asList(secondSynsetItem);
            wordsToWordID.put(myList, firstSynsetItem);
            wordIDToWords.put(firstSynsetItem, myList);
        }
        while (hyponymFile.hasNextLine()) {
            String[] line = hyponymFile.readLine().split(",");
            Integer wordID = Integer.parseInt(line[0]);
            List<Integer> hypos;
            if (hypoRelationship.containsKey(wordID)) {
                hypos = hypoRelationship.get(wordID);
            } else {
                hypos = new Stack<>();
            }
            for (int i = 1; i < line.length; i++) {
                hypos.add(Integer.parseInt(line[i]));
            }
            hypoRelationship.put(wordID, hypos);
        }
    }

    public Set<String> getHyponyms(String word) {
        List<Integer> integerList = new ArrayList<>();
        for (List<String> list : wordsToWordID.keySet()) {
            if (list.contains(word)) {
                integerList.add((wordsToWordID.get(list)));
            }
        }
        return getChildren(integerList);
    }

    private Set<String> getChildren(List<Integer> stack) {
        //Tree traversal
        Set<String> children = new TreeSet<>();
        Stack<List<Integer>> traversalStack = new Stack<>();
        traversalStack.push(stack);
        while (!traversalStack.isEmpty()) {
            List<Integer> current = traversalStack.pop();
            for (Integer integer : current) {
                List<String> words = wordIDToWords.get(integer);
                for (String word : words) {
                    if (!children.contains(word)) {
                        children.add(word);
                    }
                }
                List<Integer> temp = hypoRelationship.get(integer);
                if (temp == null) {
                } else {
                    traversalStack.push(temp);
                }
            }
        }
        return children;
    }
}
