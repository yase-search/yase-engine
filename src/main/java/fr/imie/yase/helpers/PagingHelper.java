package fr.imie.yase.helpers;

public class PagingHelper {
    
    private static final Integer INTERVAL = 10;
    
    private static final Integer LENGTH_PAGING = 5; 
    
    private static final Integer UN = 1;
    
    private static final Integer ZERO = 0;

    private Integer currentPageNumber;
    
    private Integer numberResult;
    
    private String parameterSearch;
    
    public PagingHelper(Integer currentPageNumber, Integer numberResult, String parameterSearch) {
        this.currentPageNumber = currentPageNumber;
        this.numberResult = numberResult;
        this.parameterSearch = parameterSearch;
    }
    
    /**
     * Permet de générer le block HTML de pagination
     * @return String HTML
     */
    public String generatePagingHTML() {
        StringBuilder builder = new StringBuilder();
        if (numberResult > INTERVAL) {
            Integer numberPaging = numberResult / INTERVAL;
            Integer startPaging = getStartPaging(numberPaging);
            Integer endPaging = getEndPaging(numberPaging, startPaging);
            // On génère le bouton "Précédent" si possible
            if (!UN.equals(currentPageNumber)) {
                builder.append("<a href='" + constructLinkPaging(currentPageNumber - 1) + "'><li>Prec</li></a>");
            }
            for (int i = startPaging; i <= endPaging; i++) {
                builder.append(generateIterationPaging(i));
            }
            // On génère le bouton "Suivant" si possible
            if (!currentPageNumber.equals(endPaging)) {
                builder.append("<a href='" + constructLinkPaging(currentPageNumber + 1) + "'><li>Suiv</li></a>");
            }
        }
        return builder.toString();
    }

    /**
     * Permet de récupérer le numéro de la première page de la pagination
     * @param numberPaging Integer - Nombre de page
     * @return Integer - numéro de la première page
     */
    public Integer getStartPaging(Integer numberPaging) {
        Integer startPaging = 1;
        // Si le nombre de page est > à la taille de la pagination et que la pagination courrante vaut au minimum 3
        if (numberPaging >= LENGTH_PAGING && currentPageNumber >= 3) {
            startPaging = currentPageNumber - 2;
            Integer diffCurrentAndMax = numberPaging - currentPageNumber;
            Integer newStartPaging = startPaging - diffCurrentAndMax;
            
            // On s'assure qu'on arrive pas en fin de pagination. Si c'est le cas on recule le début de la pagination.
            if (diffCurrentAndMax < 2 && newStartPaging >= 1) {
                // Si la différence entre la page courante et la page max, alors on recule le debut de la pagination
                if (ZERO.equals(diffCurrentAndMax)) {
                    newStartPaging = startPaging - 2;
                }
                startPaging = newStartPaging;
            }
        }
        return startPaging;
    }
    
    /**
     * Permet de récupérer le numéro de la dernière page de la pagination
     * @param numberPaging Integer - Nombre de page
     * @param startPaging Integer - numéro de la première page
     * @return Integer - numéro de la dernière page
     */
    public Integer getEndPaging(Integer numberPaging, Integer startPaging) {
        Integer endPaging = numberPaging;
        // Si le compteur de la dernière pagination ne vaut pas la dernière page possible
        if (!startPaging.equals(numberPaging - LENGTH_PAGING)) {
            if (numberPaging <= LENGTH_PAGING) {
                endPaging = numberPaging;
            } else {
                endPaging = startPaging + LENGTH_PAGING - 1;
            }
        }
        return endPaging;
    }
    
    /**
     * Permet de générer une itération dans la pagination
     * @param numberPage Integer - Numéro de la page
     * @return String - HTML d'une <li>
     */
    private String generateIterationPaging(Integer numberPage) {
        StringBuilder lineHTML = new StringBuilder();
        String classCSS = "";
        if (numberPage.equals(currentPageNumber)) {
            classCSS = "enable";
        }
        lineHTML.append("<a href='");
        lineHTML.append(constructLinkPaging(numberPage));
        lineHTML.append("'>");
        lineHTML.append("<li class='");
        lineHTML.append(classCSS);
        lineHTML.append("'>");
        lineHTML.append(numberPage);
        lineHTML.append("</li></a>");
        return lineHTML.toString();
    }
    
    /**
     * Permet de générer le lien d'un bouton de la pagination
     * @param numberPage Integer - Numéro de la page
     * @return String - link
     */
    private String constructLinkPaging(Integer numberPage) {
        StringBuilder link = new StringBuilder();
        link.append("?paging=");
        link.append(numberPage);
        link.append("&search=");
        link.append(parameterSearch);
        return link.toString();
    }
    
}
