package fr.imie.yase.crawler;

import fr.imie.yase.database.dao.KeywordsDAO;
import fr.imie.yase.dto.Keywords;

import java.sql.SQLException;
import java.util.Map;

/**
 * Created by arnaud on 20/07/16.
 */
public class CrawlerDBManager {
    private static CrawlerDBManager _instance;

    public static CrawlerDBManager getInstance(){
        if(_instance == null){
            _instance = new CrawlerDBManager();
        }

        return _instance;
    }
}
