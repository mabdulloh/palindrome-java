package org.example;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;

@Slf4j
@UtilityClass
public class PalindromeChecker {

    private final Pattern NON_ALPHANUMERIC_REGEX = Pattern.compile("[^a-zA-Z0-9]");

    public static List<String> findPalindromes(String fileInput){
        final List<String> palindromes = new ArrayList<>();
        try {
            var text = Files.readString(Paths.get(fileInput), StandardCharsets.UTF_8);
            var quotedSentences = extractQuotedSentences(text);
            for (var sentence : quotedSentences) {
                if (isSentencePalindrome(sentence)) {
                    palindromes.add(sentence);
                }

            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return palindromes;
    }

    public static boolean isSentencePalindrome(String sentence) {
        if (sentence == null || sentence.isEmpty()) {
            return true; // Empty or null strings are considered palindromes
        }

        // 1. Normalize the sentence:
        String cleanSentence = NON_ALPHANUMERIC_REGEX.matcher(sentence.toLowerCase(Locale.ROOT)).replaceAll("");

        // 2. Check for palindrome:
        int left = 0;
        int right = cleanSentence.length() - 1;

        while (left < right) {
            if (cleanSentence.charAt(left) != cleanSentence.charAt(right)) {
                return false; // Not a palindrome
            }
            left++;
            right--;
        }

        return true; // It's a palindrome
    }

    private static List<String> extractQuotedSentences(String text) {
        List<String> sentences = new ArrayList<>();
        StringBuilder currentSentence = new StringBuilder();
        boolean inQuotes = false;
        boolean escaped = false;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            if (escaped) {
                currentSentence.append(c);
                escaped = false;
                continue;
            }

            if (c == '\\') {
                escaped = true;
                currentSentence.append(c);
                continue;
            }

            if (c == '"') {
                if (inQuotes) {
                    sentences.add(currentSentence.toString());
                    currentSentence.setLength(0);
                    inQuotes = false;
                } else {
                    inQuotes = true;
                }
                continue;
            }

            if (inQuotes) {
                currentSentence.append(c);
            }
        }
        return sentences;
    }
}
