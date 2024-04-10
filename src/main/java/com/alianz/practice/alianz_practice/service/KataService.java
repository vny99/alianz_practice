package com.alianz.practice.alianz_practice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class KataService {

    private static final Double PRICE = 8.0;

    private static final Double DOUBLE_ZERO = 0.0;

    private static final Double DOUBLE_Hundred = 100.0;

    private static final int INT_ZERO = 0;

    private static final int INT_ONE = 1;

    private static final int INT_TWO = 2;

    private static final int INT_THREE = 3;

    private static final int INT_FOUR = 4;

    private static final int INT_FIVE = 5;

    private static Double DISCOUNT_FOR_TWO = 5.0;

    private static Double DISCOUNT_FOR_THREE = 10.0;

    private static Double DISCOUNT_FOR_FOUR = 20.0;

    private static Double DISCOUNT_FOR_FIVE = 25.0;

    public Double getTotalBookPrice(List<Integer> list) {
        Double price = Double.MAX_VALUE;

        if (list.isEmpty()) {
            return DOUBLE_ZERO;
        }

        if (list.size() == INT_ONE) {
            return PRICE;
        }
        
        Map<Integer, Integer> bookTypeMap = list.stream()
                .collect(Collectors.groupingBy(Function.identity(), TreeMap::new, Collectors.summingInt(e -> 1)));

        int limit = bookTypeMap.size();
        int lowerBound = limit - INT_ONE;

        while (limit >= lowerBound) {
            Double priceAtEachStage = calculatePrizeAtEachStage(limit, bookTypeMap);
            price = Math.min(priceAtEachStage, price);
            limit--;
        }

        return price;
    }

    private Double calculatePrizeAtEachStage(int limit, Map<Integer, Integer> bookTypeMap) {
        List<List<Integer>> setsOfBooks = new ArrayList<>();
        getCombinations(INT_ZERO, INT_ZERO, INT_ZERO, bookTypeMap, setsOfBooks, new ArrayList<>(bookTypeMap.keySet()));
        return calculatePrize(setsOfBooks);
    }

    private void getCombinations(int stageIndex, int keyIndex, int bookCount, Map<Integer, Integer> bookTypeMap,
            List<List<Integer>> setsOfBooks, List<Integer> KeyList) {
        List<Integer> setOfBooks = null;

        if (keyIndex >= KeyList.size()) {
            return;
        }

        if (stageIndex < setsOfBooks.size() && setsOfBooks.get(stageIndex) != null) {
            setOfBooks = setsOfBooks.get(stageIndex);
        } else {
            setOfBooks = new ArrayList<>();
        }

        int book = KeyList.get(keyIndex);

        if (bookTypeMap.containsKey(book)) {
            setOfBooks.add(book);
            if (stageIndex < setsOfBooks.size() && setsOfBooks.get(stageIndex) != null) {
                setsOfBooks.set(stageIndex, setOfBooks);
            } else {
                setsOfBooks.add(setOfBooks);
            }

            if (stageIndex == 0) {
                bookCount = bookTypeMap.get(book);
            }
            if (bookCount > INT_ONE) {
                getCombinations(stageIndex + INT_ONE, keyIndex, bookCount - INT_ONE, bookTypeMap, setsOfBooks, KeyList);
            } else {
                getCombinations(0, keyIndex + 1, bookCount, bookTypeMap, setsOfBooks, KeyList);
            }
        }

    }

    private Double calculatePrize(List<List<Integer>> setsOfBooks) {
        Double price = DOUBLE_ZERO;
        for (List<Integer> setOfBook : setsOfBooks) {
            price += getPrice(setOfBook.size());
        }
        return price;
    }

    private Double getPrice(int size) {
        Double price = DOUBLE_ZERO;
        if (size == INT_FIVE) {
            price = PRICE * size * ((DOUBLE_Hundred - DISCOUNT_FOR_FIVE) / DOUBLE_Hundred);
        } else if (size == INT_FOUR) {
            price = PRICE * size * ((DOUBLE_Hundred - DISCOUNT_FOR_FOUR) / DOUBLE_Hundred);
        } else if (size == INT_THREE) {
            price = PRICE * size * ((DOUBLE_Hundred - DISCOUNT_FOR_THREE) / DOUBLE_Hundred);
        } else if (size == INT_TWO) {
            price = PRICE * size * ((DOUBLE_Hundred - DISCOUNT_FOR_TWO) / DOUBLE_Hundred);
        } else if (size == INT_ONE) {
            price = PRICE;
        }
        return price;
    }

}
