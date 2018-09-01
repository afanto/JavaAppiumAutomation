package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.CoreTestCase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject {

    private static final String
        TITTLE = "org.wikipedia:id/view_page_title_text",
        FOOTER_ELEMENT = "//*[@text='View page in browser']";

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
}
