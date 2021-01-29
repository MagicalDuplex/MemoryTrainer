package com.example.memorytrainer;

import java.util.Random;

public class GameBuilder {
    /**
     * Get Solution Digits
     *
     * @param digits Decimal number
     * @param size Table size
     * @return Solution Digits
     */
    public static int[][] getSolutionDigits(int[] digits, int size){
        int[][] solutionDigits = new int[size][size];
        int counter = 0;
        if(digits.length >= size * size) {
            for (int i = 0; i < size; i++){
                for (int j = 0; j < size; j++) {
                    solutionDigits[i][j] = digits[counter];
                    counter++;
                }
            }

            return solutionDigits;
        }

        return null;
    }

    /**
     * Get Solution Letters
     *
     * @param letters Letters
     * @param size Table size
     * @return Solution Letters
     */
    public static char[][] getSolutionLetters(char[] letters, int size){
        char[][] solutionLetters = new char[size][size];
        int counter = 0;
        if(letters.length >= size * size) {
            for (int i = 0; i < size; i++){
                for (int j = 0; j < size; j++) {
                    solutionLetters[i][j] = letters[counter];
                    counter++;
                }
            }

            return solutionLetters;
        }

        return null;
    }

    public static int[][] getGameDigits(int[][] digits) {
        int[][] gameDigits = new int[digits.length][digits.length];

        for (int i = 0; i < digits.length; i++) {
            for (int j = 0; j < digits.length; j++) {
                gameDigits[i][j] = digits[i][j];
            }
        }

        for (int i = 0; i < 1000; i++) {
            int min = 1;
            int max = 3;
            int diff = max - min;
            Random random = new Random();
            int rand = random.nextInt(diff + 1) + min;

            switch (rand) {
                case 1:
                    gameDigits = swapRandomElementsDigits(gameDigits);
                    gameDigits = transposingDigits(gameDigits);
                    gameDigits = swapRowsDigits(gameDigits);
                    gameDigits = swapColumnsDigits(gameDigits);
                    break;
                case 2:
                    gameDigits = swapRandomElementsDigits(gameDigits);
                    gameDigits = swapRowsDigits(gameDigits);
                    gameDigits = transposingDigits(gameDigits);
                    gameDigits = swapColumnsDigits(gameDigits);
                    break;
                case 3:
                    gameDigits = swapRandomElementsDigits(gameDigits);
                    gameDigits = swapColumnsDigits(gameDigits);
                    gameDigits = transposingDigits(gameDigits);
                    gameDigits = swapRowsDigits(gameDigits);
                    break;
            }
        }

        return gameDigits;
    }

    public static int[][] transposingDigits(int[][] digits) {

        for (int col = 0; col < digits.length; col++) {
            for (int row = col + 1; row < digits.length; row++) {
                int temp = digits[col][row];

                digits[col][row] = digits[row][col];
                digits[row][col] = temp;
            }
        }

        return digits;
    }

    public static int[][] swapRowsDigits(int[][] digits) {
        int row1 = getRandomNumber(digits.length);
        int row2 = getRandomNumber(digits.length);

        for (int col = 0; col < digits.length; col++) {
            int temp = digits[col][row1];

            digits[col][row1] = digits[col][row2];
            digits[col][row2] = temp;
        }

        return digits;
    }

    public static int[][] swapColumnsDigits(int[][] digits) {
        int col1 = getRandomNumber(digits.length);
        int col2 = getRandomNumber(digits.length);

        for (int row = 0; row < digits.length; row++) {
            int temp = digits[col1][row];

            digits[col1][row] = digits[col2][row];
            digits[col2][row] = temp;
        }

        return digits;
    }

    public static int[][] swapRandomElementsDigits(int[][] digits) {
        int col1 = getRandomNumber(digits.length);
        int row1 = getRandomNumber(digits.length);

        int row2 = getRandomNumber(digits.length);
        int col2 = getRandomNumber(digits.length);

        int temp = digits[col1][row1];
        digits[col1][row1] = digits[col2][row2];
        digits[col2][row2] = temp;


        return digits;
    }

    public static int getRandomNumber(int size) {
        Random random = new Random();
        return random.nextInt(size);
    }


    public static char[][] getGameLetters(char[][] letters) {
        char[][] gameLetters = new char[letters.length][letters.length];

        for (int i = 0; i < letters.length; i++) {
            for (int j = 0; j < letters.length; j++) {
                gameLetters[i][j] = letters[i][j];
            }
        }

        for (int i = 0; i < 1000; i++) {
            int min = 1;
            int max = 3;
            int diff = max - min;
            Random random = new Random();
            int rand = random.nextInt(diff + 1) + min;

            switch (rand) {
                case 1:
                    gameLetters = swapRandomElementsLetters(gameLetters);
                    gameLetters = transposingLetters(gameLetters);
                    gameLetters = swapRowsLetters(gameLetters);
                    gameLetters = swapColumnsLetters(gameLetters);
                    break;
                case 2:
                    gameLetters = swapRandomElementsLetters(gameLetters);
                    gameLetters = swapRowsLetters(gameLetters);
                    gameLetters = transposingLetters(gameLetters);
                    gameLetters = swapColumnsLetters(gameLetters);
                    break;
                case 3:
                    gameLetters = swapRandomElementsLetters(gameLetters);
                    gameLetters = swapColumnsLetters(gameLetters);
                    gameLetters = transposingLetters(gameLetters);
                    gameLetters = swapRowsLetters(gameLetters);
                    break;
            }
        }

        return gameLetters;
    }

    public static char[][] transposingLetters(char[][] letters) {

        for (int col = 0; col < letters.length; col++) {
            for (int row = col + 1; row < letters.length; row++) {
                char temp = letters[col][row];

                letters[col][row] = letters[row][col];
                letters[row][col] = temp;
            }
        }

        return letters;
    }

    public static char[][] swapRowsLetters(char[][] letters) {
        int row1 = getRandomNumber(letters.length);
        int row2 = getRandomNumber(letters.length);

        for (int col = 0; col < letters.length; col++) {
            char temp = letters[col][row1];

            letters[col][row1] = letters[col][row2];
            letters[col][row2] = temp;
        }

        return letters;
    }

    public static char[][] swapColumnsLetters(char[][] letters) {
        int col1 = getRandomNumber(letters.length);
        int col2 = getRandomNumber(letters.length);

        for (int row = 0; row < letters.length; row++) {
            char temp = letters[col1][row];

            letters[col1][row] = letters[col2][row];
            letters[col2][row] = temp;
        }

        return letters;
    }

    public static char[][] swapRandomElementsLetters(char[][] letters) {
        int col1 = getRandomNumber(letters.length);
        int row1 = getRandomNumber(letters.length);

        int row2 = getRandomNumber(letters.length);
        int col2 = getRandomNumber(letters.length);

        char temp = letters[col1][row1];
        letters[col1][row1] = letters[col2][row2];
        letters[col2][row2] = temp;


        return letters;
    }
}
