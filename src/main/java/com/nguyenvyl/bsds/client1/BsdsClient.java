
package com.nguyenvyl.bsds.client1;

import java.util.concurrent.Executor;

/**
 *
 * @author nguyenvyl
 */
public class BsdsClient {
    
//    private static final String REST_URL = "http://bsdsquickstart-env.kjs8qjfm48.us-west-2.elasticbeanstalk.com/";
    private static final String DEFAULT_REST_URL = "http://52.40.160.138/";
    private static final String DEFAULT_PATH = "webapi/myresource";
    private static final String DEFAULT_BODY = "hello";
    private static final Integer DEFAULT_NUM_THREADS = 10;
    private static final Integer DEFAULT_NUM_REQUESTS = 10;
    /**
     * @param args the command line arguments 
     * For now, let's assume that the first number is # of threads, second is # of requests per thread
     */
    public static void main(String[] args) {
//        String getResponse = RestClient.getFromServer(REST_URL, PATH);
//        String postResponse = RestClient.postToServer(REST_URL, PATH, "hello");
//        System.out.println(getResponse);
//        System.out.println(postResponse);
        Integer numThreads = Integer.parseInt(args[0]);
        Integer numCalls = Integer.parseInt(args[1]);
        RestExecutor restExecutor = new RestExecutor(DEFAULT_REST_URL, DEFAULT_PATH, DEFAULT_BODY, DEFAULT_NUM_THREADS, DEFAULT_NUM_REQUESTS);
        restExecutor.executeAllThreads();
        StatsGenerator stats = new StatsGenerator(restExecutor);
        stats.generate();
    }
    


    
}
