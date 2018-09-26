package lib.ui.android;

import lib.ui.MyListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AndroidMyListsPageObject extends MyListsPageObject
{

    static {
        FOLDER_BY_NAME_TPL = "xpath://*[@text='{FOLDER_NAME}']";
        ARTICLE_BY_TITLE_TPL = "xpath://*[@text='{TITTLE}']";
        ARTICLE_BY_SUBTITLE_TPL = "xpath://*[contains(@text, '{SUBTITTLE}')]";
    }

    public AndroidMyListsPageObject (RemoteWebDriver driver)
    {
        super(driver);
    }
}
