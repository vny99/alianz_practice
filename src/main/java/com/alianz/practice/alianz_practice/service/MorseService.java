package com.alianz.practice.alianz_practice.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class MorseService {

    private static final Map<Character, String> morseCharIndex = new TreeMap<>();

    private static final Map<String, String> morseDictonaryIndex = new TreeMap<>();

    private static final Map<String, List<String>> morseDictionaryDuplicates = new TreeMap<>();

    private static final String CODE_FILE_PATH = "C:/Users/vtata/Desktop/MorseCodes.txt";

    private static final String WORD_FILE_PATH = "C:/Users/vtata/Desktop/MorseWords.txt";

    private static final Integer INT_ONE = 1;

    private static final Integer INT_ZERO = 0;

    @PostConstruct
    public void init() {
        populateMorseIndex(CODE_FILE_PATH);
        populateMorseDictionary(WORD_FILE_PATH);
    }

    private void populateMorseDictionary(String filePath) {
        List<String> lines = readFile(filePath);
        if (lines != null) {
            morseDictonaryIndex.putAll(lines.stream()
                    .collect(Collectors.toMap(MorseService::getCode, Function.identity(), (a, b) -> {
                        if (morseDictionaryDuplicates.containsKey(a)) {
                            List<String> list = morseDictionaryDuplicates.get(a);
                            list.add(a);
                            morseDictionaryDuplicates.remove(a);
                            morseDictionaryDuplicates.put(b, list);
                        } else {
                            List<String> list = new ArrayList<>();
                            list.add(a);
                            morseDictionaryDuplicates.put(b, list);
                        }
                        return b;
                    }, TreeMap::new)));
        }
    }

    private void populateMorseIndex(String filePath) {
        List<String> lines = readFile(filePath);
        if (lines != null) {
            morseCharIndex.putAll(lines.stream().map(line -> line.split(": "))
                    .collect(Collectors.toMap(a -> a[0].charAt(INT_ZERO), a -> a[INT_ONE], (a, b) -> b, TreeMap::new)));
        }
    }

    private List<String> readFile(String filePath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            for (String line; (line = reader.readLine()) != null;) {
                lines.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static String getCode(String word) {
        return word.toUpperCase().chars().mapToObj(ch -> (char) ch)
                .map(morseCharIndex::get).reduce("", String::concat);
    }

    public void matchedStrings(List<String> words, String codeLine) {
        init();
        for (String word : words) {
            String code = getCode(word);
            if (code.equals(codeLine)) {
                System.out.println(word);
            }
        }

    }

    public Set<String> decode(String codeLine) {
        init();
        Set<String> result = new TreeSet<>();
        List<String> permutation = new ArrayList<>();
        getPossibleSentences(INT_ZERO, INT_ONE, permutation, codeLine, result);
        return result;
    }

    private void getPossibleSentences(int start, int end, List<String> permutation, String codeLine,
            Set<String> result) {
        if (start <= end && end <= codeLine.length()) {
            String codedWord = codeLine.substring(start, end);

            if (morseDictonaryIndex.containsKey(codedWord)) {
                String word = morseDictonaryIndex.get(codedWord);
                permutation.add(word);

                if (end == codeLine.length()) {
                    String sentence = permutation.stream().reduce("", String::concat);
                    String readableSentence = permutation.stream().collect(Collectors.joining(" "));
                    String codeSentence = MorseService.getCode(sentence);
                    if (codeLine.equals(codeSentence)) {
                        result.add(readableSentence);
                        checkForDuplicates(readableSentence, result);
                        permutation.remove(permutation.size() - INT_ONE);
                    }
                    return;
                }

                getPossibleSentences(end, end + INT_ONE, permutation, codeLine, result);
                permutation.remove(permutation.size() - INT_ONE);

            }
            getPossibleSentences(start, end + INT_ONE, permutation, codeLine, result);

        }

    }

    private void checkForDuplicates(String line, Set<String> result) {

        int i = 0;
        int start = 0;
        int end = 0;
        while (i < line.length()) {
            if (line.charAt(i) != ' ') {
                start = i;
                while (i < line.length() && line.charAt(i) != ' ') {
                    i++;
                }
                end = i;
                String word = line.substring(start, end);
                if (morseDictionaryDuplicates.containsKey(word)) {
                    for (String duplicate : morseDictionaryDuplicates.get(word)) {
                        String permutation = line.substring(0, start) + duplicate + line.substring(end);
                        result.add(permutation);
                    }
                }
            }
            i++;
        }
    }

}
