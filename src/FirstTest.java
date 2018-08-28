import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.sql.Struct;
import java.util.List;

public class FirstTest {
    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","AndroidTestDevice");
        capabilities.setCapability("platformVersion","9");
        capabilities.setCapability("automationName","Appium");
        capabilities.setCapability("appPackage","org.wikipedia");
        capabilities.setCapability("appActivity",".main.MainActivity");
        capabilities.setCapability("app","/users/andreyfrantov/prog/JavaAppiumAutomation/apks/org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown()
    {
        driver.quit();
    }

    @Test
    public void firstTest()
    {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        verifyElementText(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field",
                5,
                "Search…"
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language"
        );
    }


    @Test
    public void testCancelSearch()
    {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel search",
                5
        );

        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel search",
                5
        );
    }

    @Test
    public void testCompareArticleTittle()
    {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language",
                5
        );

        WebElement tittleElement = waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article tittle",
                15
        );

        String articleTittle = tittleElement.getAttribute("text");

        Assert.assertEquals(
                "We see unexpected tittle!",
                "Java (programming language)",
                articleTittle
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field",
                5
        );
    }

    @Test
    public void testSearchAndCancel()
    {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Test automation",
                "Cannot find search input",
                5
        );

        waitForElementPresent(
                By.xpath("//*[contains(@text, 'Test automation')]"),
                "Cannot find 'Test automation' article",
                5
        );

        waitForElementPresent(
                By.xpath("//*[contains(@text, 'Test automation management tools')]"),
                "Cannot find 'Test automation management tools' article",
                5
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field",
                5
        );

        waitForElementNotPresent(
                By.xpath("//*[contains(@text, 'Test automation')]"),
                "Cannot find 'Test automation' article",
                5
        );

        waitForElementNotPresent(
                By.xpath("//*[contains(@text, 'Test automation management tools')]"),
                "Cannot find 'Test automation management tools' article",
                5
        );
    }

    @Test
    public void testSearchResultsAreRelevant()
    {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
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
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Appium",
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Appium']"),
                "Cannot find 'Appium' article in search",
                5
        );

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article tittle",
                15
        );

        swipeUpToFindElement(
                By.xpath("//*[@text='View page in browser']"),
                "Cannot find the end of the article",
                20
        );
    }

    @Test
    public void saveFirstArticleToMyList()
    {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language",
                5
        );

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article tittle",
                15
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find 'More options' button",
                5
        );

        waitForElementPresent(
                By.xpath("//*[@text='Change language']"),
                "Cannot find button to 'Add to reading list'",
                5
        );

        waitForElementPresent(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find button to 'Add to reading list'",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find button to 'Add to reading list'",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'GOT IT' button",
                5
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find 'Name of the list' field",
                5
        );

        String nameOfFolder = "Learning programming";

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                nameOfFolder,
                "Cannot put text into 'Name of the list' field",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot find 'OK' button",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot find 'X' button",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find 'My lists' button",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='" + nameOfFolder + "']"),
                "Cannot find " + nameOfFolder + " list",
                5
        );

        swipeElementToLeft(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find 'Java (programming language)' article in My reading list"
        );

        waitForElementNotPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot delete saved article",
                5
        );
    }

    @Test
    public void testAmountOfNotEmptySearch()
    {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String searchLine = "Linkin park discography";

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                searchLine,
                "Cannot find search input",
                5
        );

        String searchResultLocator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";

        waitForElementPresent(
                By.xpath(searchResultLocator),
                "Cannot find anything by the request " + searchLine,
                15
        );

        int amounOfSearchResults = getAmountOfElements(
                By.xpath(searchResultLocator)
        );

        Assert.assertTrue(
                "Found to few results",
                amounOfSearchResults > 0
        );
    }

    @Test
    public void testNoResultsFounds()
    {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String searchLine = "zxcvasdfqwer";

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                searchLine,
                "Cannot find search input",
                5
        );

        String searchResultLocator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        String emptyResultLabel = "//*[@text='No results found']";

        waitForElementPresent(
                By.xpath(emptyResultLabel),
                "Cannot find anything by the request " + searchLine,
                15
        );

        assertElementNotPresent(
                By.xpath(searchResultLocator),
                "Found some results by request by request" + searchLine
        );
    }

    @Test
    public void testChangeScreenOrientationOnSearchResults()
    {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String searchLine = "Java";

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                searchLine,
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' searching by " + searchLine,
                15
        );

        String tittleBeforeRotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find tittle of article",
                15
        );

        driver.rotate(ScreenOrientation.LANDSCAPE);

        String tittleAfterRotation = waitForElementAndGetAttribute(
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

        String tittleAfterSecondRotation = waitForElementAndGetAttribute(
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
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language",
                5
        );

        driver.runAppInBackground(2);

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' article after returning from background",
                5
        );


    }


    private WebElement waitForElementPresent(By by, String errorMessage, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private WebElement waitForElementPresent(By by, String errorMessage)
    {
        return waitForElementPresent(by, errorMessage, 5);
    }

    private  WebElement waitForElementAndClick(By by, String errorMessage, long timeOutInSeconds)
    {
        WebElement element = waitForElementPresent(by, errorMessage, timeOutInSeconds);
        element.click();
        return element;
    }

    private  WebElement waitForElementAndSendKeys(By by, String value, String errorMessage, long timeOutInSeconds)
    {
        WebElement element = waitForElementPresent(by, errorMessage, timeOutInSeconds);
        element.sendKeys(value);
        return element;
    }


    private boolean waitForElementNotPresent(By by, String errorMessage, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );

    }

    private WebElement waitForElementAndClear(By by, String errorMessage, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.clear();
        return element;
    }

    private WebElement verifyElementText(By by, String errorMessage, long timeoutInSeconds, String expectedText)
    {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);

        String elementText = element.getAttribute("text");

        Assert.assertEquals(
                "Unexpected field text",
                expectedText,
                elementText
        );
        return element;
    }

    protected  void  swipeUp(int timeOfSwipe)
    {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int start_y = (int) (size.height * 0.8);
        int end_y = (int) (size.height * 0.2);

        action
                .press(x, start_y)
                .waitAction(timeOfSwipe)
                .moveTo(x, end_y)
                .release()
                .perform();
    }

    protected void swipeElementToLeft(By by, String error_message) {
        WebElement element = waitForElementPresent(
                by,
                error_message,
                10);
        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;

        TouchAction action = new TouchAction(driver);
        action
                .press(right_x, middle_y)
                .waitAction(300)
                .moveTo(left_x, middle_y)
                .release()
                .perform();
    }

    protected void swipeUpQuick()
    {
        swipeUp(200);
    }

    protected void  swipeUpToFindElement(By by, String error_message, int maxSwipes)
    {
        int alreadySwiped = 0;

        while (driver.findElements(by).size() == 0){

            if (alreadySwiped > maxSwipes){
                waitForElementPresent(by, "Cannot find element by swiping up. \n" + error_message, 0);
                return;
            }

            swipeUpQuick();
            ++alreadySwiped;
        }
    }

    private int getAmountOfElements(By by)
    {
        List elements = driver.findElements(by);
        return elements.size();
    }

    private void assertElementNotPresent(By by, String errorMessage)
    {
        int amountOfElements = getAmountOfElements(by);
        if (amountOfElements > 0) {
            String defaultMessage = "An element '" + by.toString() + "' supposed to be not present";
            throw new AssertionError(defaultMessage + " " + errorMessage);
        }
    }

    private  String waitForElementAndGetAttribute(By by, String attribute, String errorMessage, long timeoutInSeconds)
    {
      WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
     return element.getAttribute(attribute);
    }
}

