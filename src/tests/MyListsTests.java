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
    public void testSaveTwoArticlesToMyList()
        {
            String first_substring = "bject-oriented programming language";
            String second_substring = "eed of the coffee plant";

            SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

            SearchPageObject.initSearchInput();
            SearchPageObject.typeSearchLine("Java");
            SearchPageObject.clickByArticleWithSubstring(first_substring);

            ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
            ArticlePageObject.waitForTittleElement();
            String first_article_title = ArticlePageObject.getArticleTittle();

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
                ArticlePageObject.addArticleToMySaved();

                ArticlePageObject.waitForTittleElement();

                assertEquals("We are not on the same page after login",
                        first_article_title,
                        ArticlePageObject.getArticleTittle());
            }

            ArticlePageObject.closeArticle();

            //add second article
            SearchPageObject.initSearchInput();
            SearchPageObject.typeSearchLine("Coffee bean");
            SearchPageObject.clickByArticleWithSubstring(second_substring);
            ArticlePageObject.waitForTittleElement();
            String second_article_tittle = ArticlePageObject.getArticleTittle();
            String second_article_subtittle = ArticlePageObject.getArticleSubtittle(second_substring);

            if (Platform.getInstance().isAndroid()) {
                ArticlePageObject.addArticleToExistingList(name_of_folder);
            } else if (Platform.getInstance().isIOS()) {
                ArticlePageObject.addArticleToMySaved();
                ArticlePageObject.closeArticle();
            } else {
                ArticlePageObject.addArticleToMySaved();
                ArticlePageObject.waitForTittleElement();

                assertEquals("We are not on the same page after login",
                        second_article_tittle,
                        ArticlePageObject.getArticleTittle());
            }

            ArticlePageObject.closeArticle();

            //Go to MY Lists
            NavigationUI NavigationUI = NavigationUIFactory.get(driver);
            NavigationUI.openNavigation();
            NavigationUI.clickMyLists();

            MyListsPageObject MyListsPageObject = MyListsPageObjectFactory.get(driver);


            if (Platform.getInstance().isAndroid()) {
                MyListsPageObject.waitForFolderToAppear(name_of_folder);
                MyListsPageObject.openFolderByName(name_of_folder);
                MyListsPageObject.waitForArticleToAppearByTittle(first_article_title);
            }

            if (Platform.getInstance().isIOS() || Platform.getInstance().isAndroid()) {
                MyListsPageObject.waitForArticleToAppearBySubtitle(first_substring);
                MyListsPageObject.waitForArticleToAppearBySubtitle(second_substring);
                MyListsPageObject.swipeByArticleToDeleteWithSubtittle(first_substring);
                MyListsPageObject.waitForArticleToDisappearBySubtittle(first_substring);
                MyListsPageObject.waitForArticleToAppearBySubtitle(second_substring);
                SearchPageObject.clickByArticleWithSubstring(second_substring);
                ArticlePageObject.waitForSubtittleElement(second_substring);
                String opened_article_subtittle = ArticlePageObject.getArticleSubtittle(second_substring);
                assertEquals(
                    "We see unexpected subtitle!",
                        second_article_subtittle,
                        opened_article_subtittle);
            } else {
                MyListsPageObject.waitForArticleToAppearByTittle(first_article_title);
                MyListsPageObject.waitForArticleToAppearByTittle(second_article_tittle);
                MyListsPageObject.swipeByArticleToDelete(first_article_title);
                MyListsPageObject.waitForArticleToDisappearByTittle(first_article_title);
                MyListsPageObject.waitForArticleToAppearByTittle(second_article_tittle);
                MyListsPageObject.openSavedArticleByTittle(second_article_tittle);
                ArticlePageObject.waitForTittleElement();
                String opened_article_tittle = ArticlePageObject.getArticleTittle();
                assertEquals(
                        "We see unexpected title!",
                        second_article_tittle,
                        opened_article_tittle);
            }
        }
    }

