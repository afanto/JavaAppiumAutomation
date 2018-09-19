package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {

    @Test
    public void testSaveFirstArticleToMyList()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTittleElement();
        String article_title = ArticlePageObject.getArticleTittle();
        String name_of_folder = "Learning programming";
        ArticlePageObject.addArticleToMyList(name_of_folder);
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = new NavigationUI(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = new MyListsPageObject(driver);
        MyListsPageObject.waitForFolderToAppear(name_of_folder);
        MyListsPageObject.openFolderByName(name_of_folder);
        MyListsPageObject.waitForArticleToAppearByTittle(article_title);
        MyListsPageObject.swipeByArticleToDelete(article_title);
    }

    @Test
    public void testSaveTwoArticlesAndDeleteOne()
    {
        //add first article
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTittleElement();
        String first_article_title = ArticlePageObject.getArticleTittle();
        String name_of_folder = "Learning programming";
        ArticlePageObject.addArticleToMyList(name_of_folder);
        ArticlePageObject.closeArticle();

        //add second article
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Appium");
        SearchPageObject.clickByArticleWithSubstring("Appium");
        ArticlePageObject.waitForTittleElement();
        String second_article_title = ArticlePageObject.getArticleTittle();
        ArticlePageObject.addArticleToExistingList(name_of_folder);
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = new NavigationUI(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = new MyListsPageObject(driver);
        MyListsPageObject.waitForFolderToAppear(name_of_folder);
        MyListsPageObject.openFolderByName(name_of_folder);

        MyListsPageObject.waitForArticleToAppearByTittle(first_article_title);
        MyListsPageObject.waitForArticleToAppearByTittle(second_article_title);
        MyListsPageObject.swipeByArticleToDelete(first_article_title);
        MyListsPageObject.waitForArticleToDisappearByTittle(first_article_title);
        MyListsPageObject.waitForArticleToAppearByTittle(second_article_title);
        SearchPageObject.clickByArticleWithSubstring(second_article_title);
        ArticlePageObject.waitForTittleElement();
        String opened_article_tittle = ArticlePageObject.getArticleTittle();

        assertEquals(
                "We see unexpected tittle!",
                second_article_title,
                opened_article_tittle
        );
    }
}
