package com.sixt.platform.interview;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class CompletableFutureSyncTest {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Executor executor = Executors.newFixedThreadPool(1);
        Set<String> links = GoogleSearcher.getDataFromGoogle("reactjs example");
        List<CompletableFuture<Set<String>>> pageContentFutures = links.stream().map(link -> {
            System.out.println("creating task for link:" + link);

            CompletableFuture<Set<String>> future = CompletableFuture.supplyAsync(new Supplier<Set<String>>() {
                @Override
                public Set<String> get() {

                    Set<String> scripts = PageInspector.getJavascripts("https://"+link);


                    for(String script : scripts){
                        System.out.println("---"+script);
                    }

                    return scripts;

                }
            }, executor);
            return future;
        }).collect(Collectors.toList());

        CompletableFuture<Void> allFutures = CompletableFuture.allOf(
                pageContentFutures.toArray(new CompletableFuture[pageContentFutures.size()])
        );


        CompletableFuture<List<Set<String>>> allPageContentsFuture = allFutures.thenApply(v -> {
            return pageContentFutures.stream()
                    .map(pageContentFuture -> pageContentFuture.join())
                    .collect(Collectors.toList());
        });
        CompletableFuture<Map<String, Long>> countFuture = allPageContentsFuture.thenApply(pageContents -> {
            return pageContents.stream().flatMap(Set::stream).collect(groupingBy(Function.identity(), counting()));
        });

        System.out.println(" result:" +  countFuture.get());

       // allPageContentsFuture.get();
    }
}

