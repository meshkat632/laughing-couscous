package com.sixt.platform.interview;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class CompletableFutureMapReduce {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Executor executor = Executors.newFixedThreadPool(1);

        List<Integer> numbers = Arrays.asList(1,2,3,4,5,6,7);
        Optional<Integer> sum = numbers.stream().reduce((item1, item2) -> {
            System.out.println(item1 +": "+ item2);
            return item1 + item2;
        });
        System.out.println(sum.get());


        List <String> wordsList = Arrays.asList("hello", "bye", "ciao", "bye", "ciao");
        Map<String, Long> collect =
                wordsList.stream().collect(groupingBy(Function.identity(), counting()));
        System.out.println(collect);

    }
}
