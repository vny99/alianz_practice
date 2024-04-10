package com.alianz.practice.alianz_practice.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

@Service
public class QueensService {

    private static final String QUEEN = "Q";
    private static final String EMPTY = ".";

    private static LinkedHashSet<Integer> queenColoumns = new LinkedHashSet<>();
    private static LinkedHashSet<Integer> posDiagIndex = new LinkedHashSet<>();
    private static LinkedHashSet<Integer> negDiagIndex = new LinkedHashSet<>();

    public List<String> arrangeQueens(int n) {
        List<String> result = new ArrayList<>();
        getPossibleArrangements(n, 0, 0, result);
        return result;
    }

    private void getPossibleArrangements(int n, int col, int row,
            List<String> result) {

        if (row == n && queenColoumns.size() == n) {
            addCombination(result, n);

        } else if (col < n && row < n && !(row > 0 && queenColoumns.size() == 0)) {
            int posDiag = row + col;
            int negDiag = row - col;

            if (!queenColoumns.contains(col) && !posDiagIndex.contains(posDiag) && !negDiagIndex.contains(negDiag)) {
                queenColoumns.add(col);
                posDiagIndex.add(posDiag);
                negDiagIndex.add(negDiag);
                getPossibleArrangements(n, 0, row + 1, result);
                queenColoumns.remove(col);
                posDiagIndex.remove(posDiag);
                negDiagIndex.remove(negDiag);
            }
            getPossibleArrangements(n, col + 1, row, result);
        }

    }

    private void addCombination(List<String> result, int n) {
        List<Integer> queensIndex = new ArrayList<>(queenColoumns);
        result.add(IntStream.range(0, n)
                        .mapToObj(i -> IntStream.range(0, n)
                                                .mapToObj(j -> j == queensIndex.get(i) ? QUEEN : EMPTY)
                                                .collect(Collectors.joining(" ")))
                        .collect(Collectors.joining(" ")));
                        System.out.println(result.get(result.size()-1));
                        System.out.println();
    }

}
