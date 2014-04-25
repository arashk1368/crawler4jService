/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudservices.brokerage.policy.crawling_services.crawler4jservice.service;

import cloudservices.brokerage.commons.utils.file_utils.ResourceFileUtil;
import cloudservices.brokerage.commons.utils.logging.LoggerSetup;
import cloudservices.brokerage.commons.utils.properties_utils.PropertiesWriter;
import cloudservices.brokerage.policy.crawling_services.crawler4jservice.configuration.Crawler4jConfig;
import cloudservices.brokerage.policy.crawling_services.crawler4jservice.crawler_logic.CrawlerController;
import cloudservices.brokerage.policy.crawling_services.crawler4jservice.crawler_logic.repository.URLRepositoryService;
import cloudservices.brokerage.policy.crawling_services.crawler4jservice.utils.PropertiesReader;
import cloudservices.brokerage.policy.crawling_services.crawler4jservice.utils.StringGenerator;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author Arash Khodadadi http://www.arashkhodadadi.com/
 */
@WebService(serviceName = "crawler4jWS")
public class crawler4jWS {

    private final static Logger LOGGER = Logger.getLogger(crawler4jWS.class
            .getName());

    @WebMethod(operationName = "isFilterSupported")
    public boolean isFilterSupported() {
        return true;
    }

    @WebMethod(operationName = "crawl")
    public Set<String> crawl(@WebParam(name = "seeds") Set<String> seeds) throws IOException, Exception {
        URLRepositoryService.getInstance().getRepository().clear();
        String domains = "";
        for (String seed : seeds) {
            domains += seed + ",";
        }
        if (domains.length() > 0) {
            domains = domains.substring(0, domains.length() - 1);
        }
        String address = ResourceFileUtil.getResourcePath("crawler4jconfig.properties");
        PropertiesWriter.write(address, "crawlDomains", domains);
        StringGenerator sg = new StringGenerator(5);
        PropertiesWriter.write(address, "crawlIntermediateStorage",
                address.replaceAll("crawler4jconfig.properties", "crawler_data/" + sg.nextString()));

        LoggerSetup.setup(ResourceFileUtil.getResourcePath("log.txt"), ResourceFileUtil.getResourcePath("log.html"));
        LoggerSetup.log4jSetup(ResourceFileUtil.getResourcePath("log4j.properties"),
                ResourceFileUtil.getResourcePath("crawler4j.log"));

        Crawler4jConfig config = PropertiesReader.loadCrawler4jConfig(address);
        CrawlerController controller = new CrawlerController(config);
        long startTime = System.currentTimeMillis();
        LOGGER.log(Level.INFO, "Crawling Start");
        controller.start();
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        String msg = "Crawling End in " + totalTime + "ms";
        LOGGER.log(Level.INFO, msg);

        Set outgoingUrls = URLRepositoryService.getInstance().getRepository().getUrls();
        outgoingUrls.removeAll(seeds);
        return outgoingUrls;
    }

    @WebMethod(operationName = "filteredCrawl")
    public Set<String> filteredCrawl(@WebParam(name = "seeds") Set<String> seeds) throws IOException, Exception {
        URLRepositoryService.getInstance().getRepository().clear();
        String filter = ".*(\\.(css|js|bmp|gif|jpe?g|png|tiff?|mid|mp2|mp3|mp4|wav|avi|mov|mpeg|ram|m4v|pdf|rm|smil|wmv|swf|wma|zip|rar|gz))$";
        String domains = "";
        for (String seed : seeds) {
            domains += seed + ",";
        }
        if (domains.length() > 0) {
            domains = domains.substring(0, domains.length() - 1);
        }
        String address = ResourceFileUtil.getResourcePath("crawler4jconfig.properties");
        PropertiesWriter.write(address, "crawlDomains", domains);
        StringGenerator sg = new StringGenerator(5);
        PropertiesWriter.write(address, "crawlIntermediateStorage",
                address.replaceAll("crawler4jconfig.properties", "crawler_data/" + sg.nextString()));
        PropertiesWriter.write(address, "filters", filter);

        LoggerSetup.setup(ResourceFileUtil.getResourcePath("log.txt"), ResourceFileUtil.getResourcePath("log.html"));
        LoggerSetup.log4jSetup(ResourceFileUtil.getResourcePath("log4j.properties"),
                ResourceFileUtil.getResourcePath("crawler4j.log"));

        Crawler4jConfig config = PropertiesReader.loadCrawler4jConfig(address);
        CrawlerController controller = new CrawlerController(config);
        long startTime = System.currentTimeMillis();
        LOGGER.log(Level.INFO, "Crawling Start");
        controller.start();
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        String msg = "Crawling End in " + totalTime + "ms";
        LOGGER.log(Level.INFO, msg);

        Set outgoingUrls = URLRepositoryService.getInstance().getRepository().getUrls();
        outgoingUrls.removeAll(seeds);
        return outgoingUrls;
    }
}
