package com.sixt.platform.interview;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CompletableFutureAsyncTest {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // Using Lambda Expression
        List<Integer> numbers = new ArrayList<>();
        for(int i = 0; i< 10; i ++){
            numbers.add(new Integer(i));
        }

        Executor executor = Executors.newFixedThreadPool(5);

        List<CompletableFuture<Void>> pageContentFutures = numbers.stream().map(item -> {
            System.out.println(item);
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                // Simulate a long-running Job
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    throw new IllegalStateException(e);
                }
                System.out.println("I'll run in a separate thread than the main thread. item:"+item);
            },executor);
            return future;
        }).collect(Collectors.toList());
        ;

        CompletableFuture<Void> allFutures = CompletableFuture.allOf(
                pageContentFutures.toArray(new CompletableFuture[pageContentFutures.size()])
        );

// Block and wait for the future to complete
        allFutures.get();
    }
}
