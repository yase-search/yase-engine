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

    private Map<String, Integer> keywords;

    public static CrawlerDBManager getInstance(){
        if(_instance == null){
            _instance = new CrawlerDBManager();
        }

        return _instance;
    }

    public CrawlerDBManager(){
        this.populateWords();
        System.out.println("Number of words in database: " + this.keywords.size());
    }

    private void populateWords(){
        KeywordsDAO keywordsDAO = new KeywordsDAO();
        try {
            this.keywords = keywordsDAO.findAllKeywords();
        } catch (SQLException e){
            System.out.println("Couldn't get all words from database");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public Map<String, Integer> getKeywords(){
        return this.keywords;
    }

    private Keywords createKeyword(String keyword){
        KeywordsDAO keywordsDAO = new KeywordsDAO();
        Keywords entity = new Keywords();
        entity.setValue(keyword);

        try {
            Keywords created = keywordsDAO.create(entity);

            if(created.getId() != null) {
                this.keywords.put(created.getValue(), created.getId());
            }

            return created;
        } catch (SQLException e){
            // If insertion fails, return the entity which has no ID
            return entity;
        }
    }

    public Keywords keywordExists(String keyword){
        Integer keywordId = this.getKeywords().get(keyword);
        if(keywordId == null) {
            return this.createKeyword(keyword);
        } else{
            // TODO: order of arguments will be changed
            return new Keywords(keyword, false, keywordId);
        }
    }
}
