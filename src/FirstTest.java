import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;

import java.util.List;

public class FirstTest extends CoreTestCase {

    private MainPageObject MainPageObject;

    protected void setUp() throws Exception
    {
        super.setUp();

        MainPageObject = new MainPageObject(driver);
    }

    @Test
    public void testSearch()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
    }


    @Test
    public void testCancelSearch()
    {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelSeach();
        SearchPageObject.waitForCancelButtonToDissappear();
    }


    @Test
    public void testCompareArticleTittle()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);

        String article_title = ArticlePageObject.getArticleTittle();

        Assert.assertEquals(
                "We see unexpected tittle!",
                "Java (programming language)",
                article_title
        );
    }

    @Test
    public void testSearchAndCancel()
    {

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Test automation",
                "Cannot find search input",
                5
        );

        MainPageObject.waitForElementPresent(
                By.xpath("//*[contains(@text, 'Test automation')]"),
                "Cannot find 'Test automation' article",
                5
        );

        MainPageObject.waitForElementPresent(
                By.xpath("//*[contains(@text, 'Test automation management tools')]"),
                "Cannot find 'Test automation management tools' article",
                5
        );

        MainPageObject.waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field",
                5
        );

        MainPageObject.waitForElementNotPresent(
                By.xpath("//*[contains(@text, 'Test automation')]"),
                "Cannot find 'Test automation' article",
                5
        );

        MainPageObject.waitForElementNotPresent(
                By.xpath("//*[contains(@text, 'Test automation management tools')]"),
                "Cannot find 'Test automation management tools' article",
                5
        );
    }

    @Test
    public void testSearchResultsAreRelevant()
    {
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );

        List<WebElement> listOfElements = driver.findElements(By.id("org.wikipedia:id/page_list_item_title"));

        for (int i =0; i < listOfElements.size(); i++)
        {
            WebElement element = listOfElements.get(i);
            Assert.assertTrue(
                    "Text 'Java' not found in search result",
                    element.getAttribute("text").contains("Java"));
        }
    }

    @Test
    public void testSwipeArticle()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Appium");
        SearchPageObject.clickByArticleWithSubstring("Appium");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);

        ArticlePageObject.waitForTittleElement();
        ArticlePageObject.swipeToFooter();
    }

    @Test
    public void testSaveFirstArticleToMyList()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitForTittleElement();
        String article_title = ArticlePageObject.getArticleTittle();
        String name_of_folder = "Learning programming";

        ArticlePageObject.addArticleToMyList(name_of_folder);
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = new NavigationUI(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = new MyListsPageObject(driver);
        MyListsPageObject.waitForArticleToAppearByTittle(name_of_folder);
        MyListsPageObject.openFolderByName(name_of_folder);
        MyListsPageObject.swipeByArticleToDelete(article_title);


    }

    @Test
    public void testAmountOfNotEmptySearch()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        String searchLine = "Linkin park discography";
        SearchPageObject.typeSearchLine(searchLine);
        int amount_of_search_results = SearchPageObject.getAmountOfFoundArticles();

        Assert.assertTrue(
                "Found to few results",
                amount_of_search_results > 0
        );
    }

    @Test
    public void testAmountOfEmptySearch()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        String searchLine = "zxcvasdfqwer";
        SearchPageObject.typeSearchLine(searchLine);
        SearchPageObject.waitForEmptyResultsLabel();
        SearchPageObject.assertThereIsNoResultOfSearch();


    }

    @Test
    public void testChangeScreenOrientationOnSearchResults()
    {
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String searchLine = "Java";

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                searchLine,
                "Cannot find search input",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' searching by " + searchLine,
                15
        );

        String tittleBeforeRotation = MainPageObject.waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find tittle of article",
                15
        );

        driver.rotate(ScreenOrientation.LANDSCAPE);

        String tittleAfterRotation = MainPageObject.waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find tittle of article",
                15
        );

        Assert.assertEquals(
                "Article tittle have been changed after screen rotation",
                tittleBeforeRotation,
                tittleAfterRotation
        );

        driver.rotate(ScreenOrientation.PORTRAIT);

        String tittleAfterSecondRotation = MainPageObject.waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find tittle of article",
                15
        );

        Assert.assertEquals(
                "Article tittle have been changed after second screen rotation",
                tittleBeforeRotation,
                tittleAfterSecondRotation
        );
    }

    @Test
    public void testCheckSearchArticleInBackground()
    {
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );

        MainPageObject.waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language",
                5
        );

        driver.runAppInBackground(2);

        MainPageObject.waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' article after returning from background",
                5
        );
    }

    @Test
    public void testSaveTwoArticlesAndDeleteOne()
    {
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language",
                5
        );

        MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article tittle",
                15
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find 'More options' button",
                5
        );

        MainPageObject.waitForElementPresent(
                By.xpath("//*[@text='Change language']"),
                "Cannot find button to 'Add to reading list'",
                5
        );

        MainPageObject.waitForElementPresent(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find button to 'Add to reading list'",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find button to 'Add to reading list'",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'GOT IT' button",
                5
        );

        MainPageObject.waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find 'Name of the list' field",
                5
        );

        String nameOfFolder = "Learning programming";

        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                nameOfFolder,
                "Cannot put text into 'Name of the list' field",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot find 'OK' button",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot find 'X' button",
                5
        );

        //add second article
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Appium",
                "Cannot find search input",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Appium']"),
                "Cannot find 'Appium' article",
                5
        );

        MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article tittle",
                15
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find 'More options' button",
                5
        );

        MainPageObject.waitForElementPresent(
                By.xpath("//*[@text='Change language']"),
                "Cannot find button to 'Add to reading list'",
                5
        );

        MainPageObject.waitForElementPresent(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find button to 'Add to reading list'",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find button to 'Add to reading list'",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='" + nameOfFolder + "']"),
                "Cannot find " + nameOfFolder + " list",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot find 'X' button",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find 'My lists' button",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='" + nameOfFolder + "']"),
                "Cannot find " + nameOfFolder + " list",
                5
        );

        MainPageObject.waitForElementPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find 'Java (programming language)' article in My reading list"
        );

        MainPageObject.waitForElementPresent(
                By.xpath("//*[@text='Appium']"),
                "Cannot find 'Appium' article in My reading list"
        );

        MainPageObject.swipeElementToLeft(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find 'Java (programming language)' article in My reading list"
        );

        MainPageObject.waitForElementNotPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot delete saved article",
                5
        );

        MainPageObject.waitForElementPresent(
                By.xpath("//*[@text='Appium']"),
                "Cannot find 'Appium' article in My reading list"
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Appium']"),
                "Cannot find 'Appium' article in My reading list",
                5
        );

        MainPageObject.waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/view_page_title_text']"),
                "Cannot find article tittle",
                5
        );

        WebElement tittleElement = driver.findElement(
                By.xpath("//*[@resource-id='org.wikipedia:id/view_page_title_text']")
        );

        String articleTittle = tittleElement.getAttribute("text");


        Assert.assertEquals(
                "We see unexpected tittle!",
                "Appium",
                articleTittle
        );
    }

    @Test
    public void testAssertTittle()
    {
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language",
                5
        );

        MainPageObject.assertElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Element not found"
        );
    }
}

