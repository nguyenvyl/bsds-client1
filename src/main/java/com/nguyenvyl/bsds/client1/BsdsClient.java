package com.nguyenvyl.bsds.client1;

import org.apache.commons.cli.*;

/**
 *
 * @author nguyenvyl
 */
public class BsdsClient {

//    private static final String REST_URL = "http://bsdsquickstart-env.kjs8qjfm48.us-west-2.elasticbeanstalk.com/";
    private static final String DEFAULT_PATH = "webapi/myresource";
    private static final String DEFAULT_BODY = "hello";
    private static final String DEFAULT_PORT_NUMBER = "8080";
    private static final String DEFAULT_IP_ADDRESS = "52.40.160.138";
    private static final Integer DEFAULT_NUM_THREADS = 10;
    private static final Integer DEFAULT_NUM_REQUESTS = 10;

    /**
     * @param args the command line arguments For now, let's assume that the
     * first number is # of threads, second is # of requests per thread
     */
    public static void main(String[] args) {
        Options options = new Options();

        Option numThreads = new Option("t", "numThreads", true, "number of threads");
        numThreads.setRequired(false);
        options.addOption(numThreads);

        Option numRequests = new Option("r", "numRequests", true, "number of requests per thread");
        numRequests.setRequired(false);
        options.addOption(numRequests);

        Option ipAddress = new Option("i", "ipAddress", true, "IP address");
        ipAddress.setRequired(false);
        options.addOption(ipAddress);

        Option portNumber = new Option("p", "portNumber", true, "portNumber");
        portNumber.setRequired(false);
        options.addOption(portNumber);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);

            System.exit(1);
            return;
        }

        Integer threads = cmd.getOptionValue("numThreads") != null
                ? Integer.parseInt(cmd.getOptionValue("numThreads")) : DEFAULT_NUM_THREADS;
        Integer requests = cmd.getOptionValue("numRequests") != null
                ? Integer.parseInt(cmd.getOptionValue("numRequests")) : DEFAULT_NUM_REQUESTS;
        String ip = cmd.getOptionValue("ipAddress") != null
                ? cmd.getOptionValue("ipAddress") : DEFAULT_IP_ADDRESS;
        String port = cmd.getOptionValue("portNumber") != null
                ? cmd.getOptionValue("portNumber") : ":" + DEFAULT_PORT_NUMBER;
        String path = "/";
        
        // If no IP address is provided: default to my EC2 instance, which, for some
        // reason, complains when you provide the port number. Might be an EB issue.
        if (ip.equals(DEFAULT_IP_ADDRESS) && port.equals(DEFAULT_PORT_NUMBER)) {
            path = DEFAULT_PATH;
            port = "";
        } else {
            port = ":" + port;
        }
        String URL = buildURL(ip, port);

        RestExecutor restExecutor = new RestExecutor(URL, path, DEFAULT_BODY, threads, requests);
        restExecutor.executeAllThreads();
        StatsGenerator stats = new StatsGenerator(restExecutor);
        stats.generateAllStats();
    }

    // Constructs a URL from the IP address and port number. 
    private static String buildURL(String ip, String port) {
        return "http://" + ip + port;
    }

}
