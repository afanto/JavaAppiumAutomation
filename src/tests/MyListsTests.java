package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {

    private static final String name_of_folder = "Learning programming";
    private static final String
            login = "testappium",
            password = "Qwerty123";

    @Test
    public void testSaveFirstArticleToMyList()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTittleElement();
        String article_title = ArticlePageObject.getArticleTittle();

        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToMyList(name_of_folder);
        } else if (Platform.getInstance().isIOS()) {
            ArticlePageObject.addArticleToMySaved();
            ArticlePageObject.closeArticle();
        } else {
            ArticlePageObject.addArticleToMySaved();
            AuthorizationPageObject AuthPageObject = new AuthorizationPageObject(driver);
            AuthPageObject.clickAuthButton();
            AuthPageObject.enterLoginData(login, password);
            AuthPageObject.submitForm();

            ArticlePageObject.waitForTittleElement();

            assertEquals("We are not on the same page after login",
                    article_title,
                    ArticlePageObject.getArticleTittle());
        }

        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.openNavigation();
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = MyListsPageObjectFactory.get(driver);


        if (Platform.getInstance().isAndroid()) {
            MyListsPageObject.waitForFolderToAppear(name_of_folder);
            MyListsPageObject.openFolderByName(name_of_folder);
            MyListsPageObject.waitForArticleToAppearByTittle(article_title);
        }

        MyListsPageObject.swipeByArticleToDelete(article_title);

    }

    @Test
    public void testSaveTwoArticlesAndDeleteOne()
    {
        String first_substring = "Object-oriented programming language";
        String second_substring = "Seed of the coffee plant";


        //add first article
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring(first_substring);

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForSubtittleElement(first_substring);
        String first_article_subtittle = ArticlePageObject.getArticleSubtittle(first_substring);

        if(Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToMyList(name_of_folder);
        } else {
            ArticlePageObject.addArticleToMySaved();
            ArticlePageObject.closeArticle();
        }

        ArticlePageObject.closeArticle();

        //add second article
        SearchPageObject.initSearchInput();
        SearchPageObject.clearSearchLine();
        SearchPageObject.typeSearchLine("Coffee bean");
        SearchPageObject.clickByArticleWithSubstring(second_substring);

        ArticlePageObject.waitForSubtittleElement(second_substring);
        String second_article_subtitle = ArticlePageObject.getArticleSubtittle(second_substring);


        if(Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToExistingList(name_of_folder);
        } else {
            ArticlePageObject.addArticleToMySaved();
        }

        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = MyListsPageObjectFactory.get(driver);

        if(Platform.getInstance().isAndroid()) {
            MyListsPageObject.waitForFolderToAppear(name_of_folder);
            MyListsPageObject.openFolderByName(name_of_folder);
        }

        MyListsPageObject.waitForArticleToAppearBySubtitle(first_article_subtittle);
        MyListsPageObject.waitForArticleToAppearBySubtitle(second_article_subtitle);
        MyListsPageObject.swipeByArticleToDeleteWithSubtittle(first_article_subtittle);
        MyListsPageObject.waitForArticleToDisappearBySubtittle(first_article_subtittle);
        MyListsPageObject.waitForArticleToAppearBySubtitle(second_article_subtitle);

        if (Platform.getInstance().isAndroid()) {
            SearchPageObject.clickByArticleWithSubstring(second_article_subtitle.toLowerCase());
        } else {
            SearchPageObject.clickByArticleWithSubstring(second_article_subtitle);
        }

        ArticlePageObject.waitForSubtittleElement(second_article_subtitle);
        String opened_article_subtittle = ArticlePageObject.getArticleSubtittle(second_article_subtitle);

        assertEquals(
                "We see unexpected subtitle!",
                second_article_subtitle,
                opened_article_subtittle
        );
    }
}
