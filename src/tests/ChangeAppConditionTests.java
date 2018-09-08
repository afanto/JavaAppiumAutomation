package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class ChangeAppConditionTests extends CoreTestCase {

    @Test
    public void testChangeScreenOrientationOnSearchResults()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        String tittleBeforeRotation = ArticlePageObject.getArticleTittle();
        this.rotateScreenLandscape();
        String tittleAfterRotation = ArticlePageObject.getArticleTittle();

        assertEquals(
                "Article tittle have been changed after screen rotation",
                tittleBeforeRotation,
                tittleAfterRotation
        );

        this.rotateScreenPortrait();

        String tittleAfterSecondRotation = ArticlePageObject.getArticleTittle();

        assertEquals(
                "Article tittle have been changed after second screen rotation",
                tittleBeforeRotation,
                tittleAfterSecondRotation
        );
    }

    @Test
    public void testCheckSearchArticleInBackground()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
        this.backgroundApp(2);
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
    }

}
