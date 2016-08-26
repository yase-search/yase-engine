package fr.imie.yase;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by arnaud on 25/08/16.
 */
public class YaseServletContextListener implements ServletContextListener{
    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
    }

    //Run this before web application is started
    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        String crawl = System.getenv("CRAWL_ACTIVE");

        if (crawl != null && crawl.equals("TRUE")) {
            System.out.println("Starting thread");
            Application app = new Application();
            new Thread(app).start();
        }
    }
}
