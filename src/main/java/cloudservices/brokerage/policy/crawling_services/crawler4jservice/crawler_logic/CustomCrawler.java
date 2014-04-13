/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudservices.brokerage.policy.crawling_services.crawler4jservice.crawler_logic;

import cloudservices.brokerage.policy.crawling_services.crawler4jservice.configuration.Crawler4jConfig;
import cloudservices.brokerage.policy.crawling_services.crawler4jservice.crawler_logic.repository.URLRepositoryService;
import cloudservices.brokerage.policy.utils.file_utils.ResourceFileUtil;
import cloudservices.brokerage.policy.utils.properties_utils.PropertiesReader;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Arash Khodadadi http://www.arashkhodadadi.com/
 */
public class CustomCrawler extends WebCrawler {

    private final static Logger LOGGER = Logger.getLogger(CustomCrawler.class
            .getName());
    private final Crawler4jConfig config;

    public CustomCrawler() throws IOException, NumberFormatException {
        super();
        String address = ResourceFileUtil.getResourcePath("crawler4jconfig.properties");
        this.config = PropertiesReader.loadCrawler4jConfig(address);
    }

    @Override
    public boolean shouldVisit(WebURL url) {
        String href = url.getURL().toLowerCase();
        String msg = "Try to Visit: " + url;
        LOGGER.log(Level.FINER, msg);
        if (config.isInDomainOnly()) {
            boolean inDomain = false;
            for (String domain : config.getCrawlDomains()) {
                inDomain = inDomain || href.startsWith(domain);
            }
            inDomain = inDomain || config.getAcceptedOutdomainPattern().matcher(href).matches();
            return !config.getFilters().matcher(href).matches() && inDomain;
        } else {
            return !config.getFilters().matcher(href).matches();
        }
    }

    /**
     * This function is called when a page is fetched and ready to be processed
     * by your program.
     */
    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        String msg = "Visiting: " + url;
        LOGGER.log(Level.FINE, msg);
        URLRepositoryService.getInstance().getRepository().put(url);
    }
}
