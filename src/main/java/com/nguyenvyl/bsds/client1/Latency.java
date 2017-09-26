/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nguyenvyl.bsds.client1;

/**
 *
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
