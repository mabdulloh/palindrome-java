package org.example;

import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
public class Main {
    public static void main(String[] args) {
        String filePath = "src/main/resources/output.txt";
        var palindromes = PalindromeChecker.findPalindromes(filePath);

        if (palindromes.isEmpty()) {
            log.info("No palindrome phrases found in the file.");
        } else {
            log.info("Palindrome phrases found:");
            for (String palindrome : palindromes) {
                log.info(palindrome);
            }
        }
    }
}