/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cloudservices.brokerage.policy.crawling_services.crawler4jservice.crawler_logic.repository;

/**
 *
 * @author Arash Khodadadi http://www.arashkhodadadi.com/  
 */
public class URLRepositoryService {
    
    private static URLRepository repository;
    
    private URLRepositoryService() {
        repository=new URLRepository();
    }

    public static URLRepositoryService getInstance() {
        return URLRepositoryServiceHolder.INSTANCE;
    }

    public URLRepository getRepository() {
        return repository;
    }
    
    private static class URLRepositoryServiceHolder {
        private static final URLRepositoryService INSTANCE = new URLRepositoryService();
    }
 }
