package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject {

    private static final String
        TITTLE = "org.wikipedia:id/view_page_title_text",
        FOOTER_ELEMENT = "//*[@text='View page in browser']",
        OPTIONS_BUTTON = "//android.widget.ImageView[@content-desc='More options']",
        OPTIONS_CHANGE_LANGUAGE_BUTTON = "//*[@text='Change language']",
        OPTIONS_ADD_TO_READING_LIST_BUTTON = "//*[@text='Add to reading list']",
        ADD_TO_MY_LIST_OVERLAY = "org.wikipedia:id/onboarding_button",
        MY_LIST_NAME_INPUT = "org.wikipedia:id/text_input",
        MY_LIST_OK_BUTTON = "//*[@text='OK']",
        CLOSE_ARTICLE_BUTTON = "//android.widget.ImageButton[@content-desc='Navigate up']";



    public ArticlePageObject(AppiumDriver driver)
    {
        super(driver);
    }

    public WebElement waitForTittleElement()
    {
        return this.waitForElementPresent(By.id(TITTLE), "Cannot find article tittle on page", 15);
    }

    public String getArticleTittle()
    {
        WebElement title_element = waitForTittleElement();
        return title_element.getAttribute("text");
    }

    public void swipeToFooter()
    {
        this.swipeUpToFindElement(
                By.xpath(FOOTER_ELEMENT),
                "Cannot find find the end of article",
                20
        );
    }

    public void addArticleToMyList(String name_of_folder)
    {
        this.waitForElementAndClick(
                By.xpath(OPTIONS_BUTTON),
                "Cannot find 'More options' button",
                5
        );

        this.waitForElementPresent(
                By.xpath(OPTIONS_CHANGE_LANGUAGE_BUTTON),
                "Cannot find 'Change Language' button",
                5
        );

        this.waitForElementPresent(
                By.xpath(OPTIONS_ADD_TO_READING_LIST_BUTTON),
                "Cannot find 'Add to reading list' button",
                5
        );

        this.waitForElementAndClick(
                By.xpath(OPTIONS_ADD_TO_READING_LIST_BUTTON),
                "Cannot find 'Add to reading list' button",
                5
        );

        this.waitForElementAndClick(
                By.id(ADD_TO_MY_LIST_OVERLAY),
                "Cannot find 'GOT IT' button",
                5
        );

        this.waitForElementAndClear(
                By.id(MY_LIST_NAME_INPUT),
                "Cannot find 'Name of the list' field",
                5
        );


        this.waitForElementAndSendKeys(
                By.id(MY_LIST_NAME_INPUT),
                name_of_folder,
                "Cannot put text into 'Name of the list' field",
                5
        );

        this.waitForElementAndClick(
                By.xpath(MY_LIST_OK_BUTTON),
                "Cannot find 'OK' button",
                5
        );
    }

    public void addArticleToExistingList(String name_of_folder)
    {
        this.waitForElementAndClick(
                By.xpath(OPTIONS_BUTTON),
                "Cannot find 'More options' button",
                5
        );

        this.waitForElementPresent(
                By.xpath(OPTIONS_CHANGE_LANGUAGE_BUTTON),
                "Cannot find 'Change Language' button",
                5
        );

        this.waitForElementPresent(
                By.xpath(OPTIONS_ADD_TO_READING_LIST_BUTTON),
                "Cannot find 'Add to reading list' button",
                5
        );

        this.waitForElementAndClick(
                By.xpath(OPTIONS_ADD_TO_READING_LIST_BUTTON),
                "Cannot find 'Add to reading list' button",
                5
        );

        MyListsPageObject MyListsPageObject = new MyListsPageObject(driver);
        MyListsPageObject.openFolderByName(name_of_folder);
    }

    public void closeArticle()
    {
        this.waitForElementAndClick(
                By.xpath(CLOSE_ARTICLE_BUTTON),
                "Cannot find 'X' button",
                5
        );
    }
}
