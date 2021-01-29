package com.example.memorytrainer;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameBuilderTest {

    @Test
    public void getSolutionDigits() {
        int[][]  expected = new int[][] {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        int[] init = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[][] result = GameBuilder.getSolutionDigits(init, 3);
        assertArrayEquals(expected, result);
    }


    @Test
    public void getSolutionLetters() {
        char[][]  expected = new char[][] {
                {'f', 'd', 'a'},
                {'f', 'v', 'a'},
                {'z', 'x', 'n'}
        };
        char[] init = new char[] {'f', 'd', 'a', 'f', 'v', 'a', 'z', 'x', 'n'};
        char[][] result = GameBuilder.getSolutionLetters(init, 3);
        assertArrayEquals(expected, result);
    }

    @Test
    public void transposingDigits() {
        int[][]  expected = new int[][] {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        int[][] init = new int[][] {
                {1, 4, 7},
                {2, 5, 8},
                {3, 6, 9}
        };
        int[][] result = GameBuilder.transposingDigits(init);
        assertArrayEquals(expected, result);
    }

    @Test
    public void transposingLetters() {
        char[][]  expected = new char[][] {
                {'f', 'd', 'a'},
                {'f', 'v', 'a'},
                {'z', 'x', 'n'}
        };
        char[][]  init = new char[][] {
                {'f', 'f', 'z'},
                {'d', 'v', 'x'},
                {'a', 'a', 'n'}
        };
        char[][] result = GameBuilder.transposingLetters(init);
        assertArrayEquals(expected, result);
    }
}