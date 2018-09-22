package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class iOSMyListsPageObject extends MyListsPageObject
{
    static {
        ARTICLE_BY_TITLE_TPL = "xpath://XCUIElementTypeLink[contains(@name, '{TITTLE}')]";
        ARTICLE_BY_SUBTITLE_TPL = "xpath://XCUIElementTypeLink[contains(@name, '{SUBTITTLE}')]";
    }

    public iOSMyListsPageObject(RemoteWebDriver driver)
    {
        super(driver);
    }
}
