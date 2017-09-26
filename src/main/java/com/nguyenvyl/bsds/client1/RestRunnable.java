package com.nguyenvyl.bsds.client1;

import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 * A class extending Callable allowing us to run it in a separate thread.
 * @author nguyenvyl
 */
public class RestRunnable implements Runnable {

    private static Client client = ClientBuilder.newClient();
    private String url;
    private String path;
    private String body;
    private Integer numRequests;
    private List<Latency> resultList;
    

    public RestRunnable(String url, String path, String body, Integer numRequests, List<Latency> resultList) {
        this.url = url;
        this.path = path;
        this.body = body;
        this.numRequests = numRequests;
        this.resultList = resultList;
    }

    /**
     * Attempts to send a GET request to an endpoint and return the latency time. 
     * @return Latency time in milliseconds.
     */
    public Long getFromServer() {
        Long start = System.currentTimeMillis();
        WebTarget webTarget = client.target(this.url).path(this.path);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.TEXT_PLAIN);
        invocationBuilder.get(String.class);
        Long millis = System.currentTimeMillis() - start;
        return millis;
    }

    /**
     * Attempts to POST to an endpoint and return the latency time.
     * @return Latency time in milliseconds. 
     */
    public Long postToServer() {
        Long start = System.currentTimeMillis();
        WebTarget webTarget = client.target(this.url).path(this.path);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.TEXT_PLAIN);
        invocationBuilder.post(Entity.entity(this.body, MediaType.TEXT_PLAIN));
        Long millis = System.currentTimeMillis() - start;
        return millis;
    }

    /**
     * Iteratively calls both endpoints, gets the latencies for each request,
     * and returns the results in a Latency object wrapper.
     */
    @Override
    public void run() {
        System.out.println("Thread started");
        for(int i = 0; i < this.numRequests; i++) {
            Latency latency = new Latency();;
            latency.setGetLatency(getFromServer());
            latency.setPostLatency(postToServer());
            this.resultList.add(latency);
        }
        System.out.println("Thread done");
    }

    public static Client getClient() {
        return client;
    }

    public static void setClient(Client client) {
        RestRunnable.client = client;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getNumRequests() {
        return numRequests;
    }

    public void setNumRequests(Integer numRequests) {
        this.numRequests = numRequests;
    }

    public List<Latency> getResultList() {
        return resultList;
    }

    public void setResultList(List<Latency> resultList) {
        this.resultList = resultList;
    }

    
    

}
