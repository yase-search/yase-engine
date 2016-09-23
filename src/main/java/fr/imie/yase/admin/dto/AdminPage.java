package fr.imie.yase.admin.dto;

import java.util.List;

import fr.imie.yase.dto.Keywords;
import fr.imie.yase.dto.Page;

public class AdminPage extends Page{

    private List<Keywords> listKeywords;

    /**
     * @return the listKeywords
     */
    public List<Keywords> getListKeywords() {
        return listKeywords;
    }

    /**
     * @param listKeywords the listKeywords to set
     */
    public void setListKeywords(List<Keywords> listKeywords) {
        this.listKeywords = listKeywords;
    }
    
}
