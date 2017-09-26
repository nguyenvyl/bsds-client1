package com.nguyenvyl.bsds.client1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author nguyenvyl
 */
public class RestExecutor {

    private final String url;
    private final String path;
    private final String body;
    private final Integer numThreads;
    private final Integer numRequests;
    private Long wallTime;
    private List<Latency> results;

    public RestExecutor(String url, String path, String body, Integer numThreads, Integer numRequests) {
        this.url = url;
        this.path = path;
        this.body = body;
        this.numThreads = numThreads;
        this.numRequests = numRequests;
        this.results = Collections.synchronizedList(new ArrayList<>());
    }

    /**
     * Executes our RestCallable class in several threads.
     */
    public void executeAllThreads() {
        try {
            Long start = System.currentTimeMillis();
            ExecutorService executorService = Executors.newFixedThreadPool(this.numThreads);
            for (int i = 0; i < this.numThreads; i++) {
                executorService.submit(new RestRunnable(url, path, body, numRequests, results));
            }
            executorService.shutdown();
            try {
                System.out.println("Awaiting termination...");
                executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
                Long millis = System.currentTimeMillis() - start;
                this.wallTime = millis;
            } catch (InterruptedException e) {
            }
        } catch (Exception ex) {
//            System.out.println(ex.getMessage());
        }
    }

    public String getUrl() {
        return url;
    }

    public String getPath() {
        return path;
    }

    public String getBody() {
        return body;
    }

    public Long getWallTime() {
        return wallTime;
    }

    public void setWallTime(Long wallTime) {
        this.wallTime = wallTime;
    }

    public List<Latency> getResults() {
        return results;
    }

    public void setResults(List<Latency> results) {
        this.results = results;
    }

    public Integer getNumThreads() {
        return numThreads;
    }

    public Integer getNumRequests() {
        return numRequests;
    }

}
