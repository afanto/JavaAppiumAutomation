package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class iOSArticlePageObject extends ArticlePageObject {

    static {
        TITTLE = "id:Java (programming language)";
        SUBTITTLE_BY_SUBSTRING_TPL = "id:{SUBSTRING}";
        FOOTER_ELEMENT = "id:View article in browser";
        OPTIONS_ADD_TO_READING_LIST_BUTTON = "id:Save for later";
        CLOSE_ARTICLE_BUTTON = "id:Back";
    }

    public iOSArticlePageObject(RemoteWebDriver driver)
    {
        super(driver);
    }
}
