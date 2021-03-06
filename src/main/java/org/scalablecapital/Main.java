package org.scalablecapital;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class Main {

    public static final int EXECUTORS_COUNT = 100;
    public static final int NUMBER_OF_SEARCH_PAGES = 200;
    public static final int TIMEOUT = 50000;

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        if(args.length < 1) throw new IllegalArgumentException("the search term must be passed as first argument!");
        final String searchKey = args[0];
        Executor executor = Executors.newFixedThreadPool(EXECUTORS_COUNT);
        CompletableFuture<Set<String>> seatchResultFuture = GoogleSearchHelper.searchGoogle(searchKey, NUMBER_OF_SEARCH_PAGES, TIMEOUT);
        Set<String> pageLinks = seatchResultFuture.get();

        List<CompletableFuture<Set<String>>> pageContentFutures = pageLinks.stream().map(link -> {
            //System.out.println("creating task for link:" + link);

            CompletableFuture<Set<String>> future = CompletableFuture.supplyAsync(new Supplier<Set<String>>() {
                @Override
                public Set<String> get() {
                    Set<String> scripts = PageInspector.getJavascripts(link, TIMEOUT);
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

        countFuture.get().entrySet().stream()
                .sorted((t1, t2) -> Long.compare(t2.getValue(), t1.getValue()))
                .limit(5)
                .forEach(item ->{
                    System.out.println(item );
                });
        ((ExecutorService) executor).shutdown();

    }
}

