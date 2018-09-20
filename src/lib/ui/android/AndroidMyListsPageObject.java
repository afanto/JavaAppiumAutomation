package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListsPageObject;

public class AndroidMyListsPageObject extends MyListsPageObject
{

    static {
        FOLDER_BY_NAME_TPL = "xpath://*[@text='{FOLDER_NAME}']";
        ARTICLE_BY_TITLE_TPL = "xpath://*[@text='{TITTLE}']";
        ARTICLE_BY_SUBTITLE_TPL = "xpath://*[@text='{SUBTITTLE}']";
    }

    public AndroidMyListsPageObject (AppiumDriver driver)
    {
        super(driver);
    }
}
