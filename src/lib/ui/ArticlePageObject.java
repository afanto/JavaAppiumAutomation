package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import lib.ui.factories.MyListsPageObjectFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

abstract public class ArticlePageObject extends MainPageObject {

    protected static String
        TITTLE,
        FOOTER_ELEMENT,
        OPTIONS_BUTTON,
        OPTIONS_CHANGE_LANGUAGE_BUTTON,
        OPTIONS_ADD_TO_READING_LIST_BUTTON,
        ADD_TO_MY_LIST_OVERLAY,
        MY_LIST_NAME_INPUT,
        MY_LIST_OK_BUTTON,
        CLOSE_ARTICLE_BUTTON;



    public ArticlePageObject(AppiumDriver driver)
    {
        super(driver);
    }

    public WebElement waitForTittleElement()
    {
        return this.waitForElementPresent(TITTLE, "Cannot find article tittle on page", 15);
    }

    public String getArticleTittle()
    {
        WebElement title_element = waitForTittleElement();
        if (Platform.getInstance().isAndroid()) {
            return title_element.getAttribute("text");
        } else {
            return title_element.getAttribute("name");
        }
    }

    public void swipeToFooter()
    {
        if (Platform.getInstance().isAndroid()){
            this.swipeUpToFindElement(
                    FOOTER_ELEMENT,
                    "Cannot find find the end of article",
                    40
            );
        } else {
            this.swipeUpTillElementAppear(FOOTER_ELEMENT,
                    "Cannot find find the end of article",
                    40);
        }
    }

    public void addArticleToMyList(String name_of_folder)
    {
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannot find 'More options' button",
                5
        );

        this.waitForElementPresent(
                OPTIONS_CHANGE_LANGUAGE_BUTTON,
                "Cannot find 'Change Language' button",
                5
        );

        this.waitForElementPresent(
                OPTIONS_ADD_TO_READING_LIST_BUTTON,
                "Cannot find 'Add to reading list' button",
                5
        );

        this.waitForElementAndClick(
                OPTIONS_ADD_TO_READING_LIST_BUTTON,
                "Cannot find 'Add to reading list' button",
                5
        );

        this.waitForElementAndClick(
                ADD_TO_MY_LIST_OVERLAY,
                "Cannot find 'GOT IT' button",
                5
        );

        this.waitForElementAndClear(
                MY_LIST_NAME_INPUT,
                "Cannot find 'Name of the list' field",
                5
        );


        this.waitForElementAndSendKeys(
                MY_LIST_NAME_INPUT,
                name_of_folder,
                "Cannot put text into 'Name of the list' field",
                5
        );

        this.waitForElementAndClick(
                MY_LIST_OK_BUTTON,
                "Cannot find 'OK' button",
                5
        );
    }

    public void addArticleToExistingList(String name_of_folder)
    {
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannot find 'More options' button",
                5
        );

        this.waitForElementPresent(
                OPTIONS_CHANGE_LANGUAGE_BUTTON,
                "Cannot find 'Change Language' button",
                5
        );

        this.waitForElementPresent(
                OPTIONS_ADD_TO_READING_LIST_BUTTON,
                "Cannot find 'Add to reading list' button",
                5
        );

        this.waitForElementAndClick(
                OPTIONS_ADD_TO_READING_LIST_BUTTON,
                "Cannot find 'Add to reading list' button",
                5
        );

        MyListsPageObject MyListsPageObject = MyListsPageObjectFactory.get(driver);
        MyListsPageObject.openFolderByName(name_of_folder);
    }

    public void closeArticle()
    {
        this.waitForElementAndClick(
                CLOSE_ARTICLE_BUTTON,
                "Cannot find 'X' button",
                5
        );
    }

    public void addArticleToMySaved()
    {
        this.waitForElementAndClick(OPTIONS_ADD_TO_READING_LIST_BUTTON, "Cannot find option to add to reading list", 5);
    }
}
