/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudservices.brokerage.policy.crawling_services.crawler4jservice.crawler_logic.repository;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Arash Khodadadi http://www.arashkhodadadi.com/
 */
public class URLRepository {

    private Set<String> urls;

    public URLRepository() {
        this.urls = Collections.synchronizedSet(new HashSet<String>());
    }

    public void put(String value) {
        urls.add(value);
    }

    public Set<String> getUrls() {
        return urls;
    }
}
