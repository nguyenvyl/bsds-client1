package com.nguyenvyl.bsds.client1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A class that consumes a RestExecutor object and generates statistics based
 * on the raw results stored in the object.
 * @author nguyenvyl
 */
public class StatsGenerator {

    private RestExecutor executor;
    private Long avgGetLatency;
    private Long avgPostLatency;
    private Long avgOverallLatency;
    private Long medianGetLatency;
    private Long medianPostLatency;
    private Long medianOverallLatency;
    private int failedRequests = 0;
    private List<Latency> sortedByGet;
    private List<Latency> sortedByPost;
    private List<Long> allResultsSorted;

    public StatsGenerator(RestExecutor executor) {
        this.executor = executor;
    }

    public void generateAllStats() {
        Integer numThreads = executor.getNumThreads();
        Integer numRequests = executor.getNumRequests();
        sortResults();
        calculateMeanLatencies();
        calculateMedianLatencies();

        Long getPercentile99 = this.sortedByGet
                .get(calculatePercentileIndex(this.sortedByGet.size(), 0.99)).getGetLatency();

        Long getPercentile95 = this.sortedByGet
                .get(calculatePercentileIndex(this.sortedByGet.size(), 0.95)).getGetLatency();

        Long postPercentile99 = this.sortedByPost
                .get(calculatePercentileIndex(this.sortedByPost.size(), 0.99)).getPostLatency();

        Long postPercentile95 = this.sortedByPost
                .get(calculatePercentileIndex(this.sortedByPost.size(), 0.95)).getPostLatency();

        Long overallPercentile99 = this.allResultsSorted
                .get(calculatePercentileIndex(this.allResultsSorted.size(), 0.99));

        Long overallPercentile95 = this.allResultsSorted
                .get(calculatePercentileIndex(this.allResultsSorted.size(), 0.95));

        System.out.println("Total number of attempted GET and POST requests: " + Integer.toString(numThreads * numRequests * 2));
        System.out.println("Number of failed requests: " + Integer.toString(this.failedRequests));
        System.out.println("Wall time: " + executor.getWallTime() + " milliseconds");
        System.out.println("Mean latency for GET requests: " + avgGetLatency.toString());
        System.out.println("Mean latency for POST requests: " + avgPostLatency.toString());
        System.out.println("Overall mean latency: " + avgOverallLatency.toString());
        System.out.println("Median latency for GET requests: " + medianGetLatency.toString());
        System.out.println("Median latency for POST requests: " + medianPostLatency.toString());
        System.out.println("Overall median latency: " + medianOverallLatency.toString());
        System.out.println("GET 99th percentile: " + getPercentile99.toString());
        System.out.println("GET 95th percentile: " + getPercentile95.toString());
        System.out.println("POST 99th percentile: " + postPercentile99.toString());
        System.out.println("POST 95th percentile: " + postPercentile95.toString());
        System.out.println("Overall 99th percentile: " + overallPercentile99.toString());
        System.out.println("Overall 95th percentile: " + overallPercentile95.toString());
    }

    public void calculateMeanLatencies() {
        Long sumGet = new Long(0);
        Long sumPost = new Long(0);
        Integer numCalls = this.executor.getResults().size();
        for (Latency l : executor.getResults()) {
            if (l.getGetLatency() == null) {
                failedRequests++;
            } else {
                sumGet += l.getGetLatency();
            }
            if (l.getPostLatency() == null) {
                failedRequests++;
            } else {
                sumPost += l.getPostLatency();
            }
        }
        this.avgOverallLatency = (sumGet + sumPost) / (numCalls * 2);
        this.avgGetLatency = sumGet / numCalls;
        this.avgPostLatency = sumPost / numCalls;
    }

    public void sortResults() {
        this.sortedByGet = new ArrayList<>(this.executor.getResults());
        Collections.sort(this.sortedByGet, (Latency o1, Latency o2) -> o1.getGetLatency().compareTo(o2.getGetLatency()));
        this.sortedByPost = new ArrayList<>(this.executor.getResults());
        Collections.sort(this.sortedByPost, (Latency o1, Latency o2) -> o1.getPostLatency().compareTo(o2.getPostLatency()));
        this.allResultsSorted = new ArrayList<>();
        for (Latency l : this.executor.getResults()) {
            allResultsSorted.add(l.getGetLatency());
            allResultsSorted.add(l.getPostLatency());
        }
        Collections.sort(allResultsSorted);
    }

    public void calculateMedianLatencies() {
        this.medianGetLatency = this.sortedByGet.get(this.sortedByGet.size() / 2).getGetLatency();
        this.medianPostLatency = this.sortedByPost.get(this.sortedByPost.size() / 2).getPostLatency();
        int size = this.allResultsSorted.size();
        this.medianOverallLatency = this.allResultsSorted.get(size / 2);
    }

    public int calculatePercentileIndex(int size, double percentile) {
        return (int) (percentile * size);
    }

}
