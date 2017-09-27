
package com.nguyenvyl.bsds.client1;

/**
 * A wrapper object to allow us to keep track of GET and POST latencies separately.
 * @author nguyenvyl
 */
public class Latency {
    private Long getLatency;
    private Long postLatency;

    public Latency() {
    }
    
    
    public Latency(Long getLatency, Long postLatency) {
        this.getLatency = getLatency;
        this.postLatency = postLatency;
    }

    public Long getGetLatency() {
        return getLatency;
    }

    public void setGetLatency(Long getLatency) {
        this.getLatency = getLatency;
    }

    public Long getPostLatency() {
        return postLatency;
    }

    public void setPostLatency(Long postLatency) {
        this.postLatency = postLatency;
    }

    @Override
    public String toString() {
        return "Latency{" + "getLatency=" + getLatency + ", postLatency=" + postLatency + '}';
    }
    
    
    
}
