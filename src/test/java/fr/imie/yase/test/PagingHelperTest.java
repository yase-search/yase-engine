package fr.imie.yase.test;

import fr.imie.yase.helpers.PagingHelper;
import junit.framework.*;

public class PagingHelperTest extends TestCase  {

    public void testPagingClassic() {
        PagingHelper pagingHelper = new PagingHelper(1, 50, null);
        Assert.assertEquals((Integer) 1, (Integer) pagingHelper.getStartPaging(5));
        Assert.assertEquals((Integer) 5, (Integer) pagingHelper.getEndPaging(5, 1));
    }
    
    public void testPagingDeb1Max3() {
        PagingHelper pagingHelper = new PagingHelper(2, 30, null);
        Assert.assertEquals((Integer) 1, (Integer) pagingHelper.getStartPaging(3));
        Assert.assertEquals((Integer) 3, (Integer) pagingHelper.getEndPaging(3, 1));
    }
    
    public void testPagingDeb11Max15() {
        PagingHelper pagingHelper = new PagingHelper(13, 150, null);
        Assert.assertEquals((Integer) 11, (Integer) pagingHelper.getStartPaging(15));
        Assert.assertEquals((Integer) 15, (Integer) pagingHelper.getEndPaging(15, 11));
    }
    
    public void testPagingDeb8Max12() {
        PagingHelper pagingHelper = new PagingHelper(11, 120, null);
        Assert.assertEquals((Integer) 8, (Integer) pagingHelper.getStartPaging(12));
        Assert.assertEquals((Integer) 12, (Integer) pagingHelper.getEndPaging(12, 8));
    }
    
    public void testPagingDeb8Max20() {
        PagingHelper pagingHelper = new PagingHelper(10, 200, null);
        Assert.assertEquals((Integer) 8, (Integer) pagingHelper.getStartPaging(20));
        Assert.assertEquals((Integer) 12, (Integer) pagingHelper.getEndPaging(20, 8));
    }
    
    public void testPagingEnd() {
        PagingHelper pagingHelper = new PagingHelper(26, 260, null);
        Assert.assertEquals((Integer) 22, (Integer) pagingHelper.getStartPaging(26));
        Assert.assertEquals((Integer) 26, (Integer) pagingHelper.getEndPaging(26, 22));
    }
    
    public void testPagingEndAndDiff3Pages() {
        PagingHelper pagingHelper = new PagingHelper(26, 265, null);
        Assert.assertEquals((Integer) 23, (Integer) pagingHelper.getStartPaging(27));
        Assert.assertEquals((Integer) 27, (Integer) pagingHelper.getEndPaging(27, 23));
    }
    
}
