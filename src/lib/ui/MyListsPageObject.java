package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class MyListsPageObject extends MainPageObject {

    protected static String
        FOLDER_BY_NAME_TPL,
        ARTICLE_BY_TITLE_TPL,
        ARTICLE_BY_SUBTITLE_TPL;

    /*TEMPLATE METHODS*/

    private static String getFolderByName(String name_of_folder)
    {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }

    private static String getSavedArticleXpathByTittle(String article_tittle)
    {
        return ARTICLE_BY_TITLE_TPL.replace("{TITTLE}", article_tittle);
    }

    private static String getSavedArticleXpathBySubtittle(String article_subtittle)
    {
        if (Platform.getInstance().isAndroid()) {
            return ARTICLE_BY_SUBTITLE_TPL.replace("{SUBTITTLE}", article_subtittle.toLowerCase());
        } else {
            return ARTICLE_BY_SUBTITLE_TPL.replace("{SUBTITTLE}", article_subtittle);
        }

    }
    /*TEMPLATE METHODS*/

    public MyListsPageObject(RemoteWebDriver driver)
    {
        super(driver);
    }

    public void waitForFolderToAppear(String name_of_folder)
    {
        String folder_name_xpath = getFolderByName(name_of_folder);
        this.waitForElementPresent(
                folder_name_xpath,
                "Cannot find folder by name " + name_of_folder,
                5
        );
    }

    public void openFolderByName(String name_of_folder)
    {
        String folder_name_xpath = getFolderByName(name_of_folder);
        this.waitForElementAndClick(
                folder_name_xpath,
                "Cannot find folder by name " + name_of_folder,
                5
        );
    }

    public void waitForArticleToAppearByTittle(String article_tittle)
    {
        String article_xpath = getSavedArticleXpathByTittle(article_tittle);
        this.waitForElementPresent(
                article_xpath,
                "Cannot find saved article by tittle " + article_tittle,
                15
        );
    }

    public void waitForArticleToAppearBySubtitle(String article_subtittle)
    {
        String article_xpath = getSavedArticleXpathBySubtittle(article_subtittle);
        this.waitForElementPresent(
                article_xpath,
                "Cannot find saved article by subtittle " + article_subtittle,
                15
        );
    }

    public void waitForArticleToDisappearByTittle(String article_tittle)
    {
        String article_xpath = getSavedArticleXpathByTittle(article_tittle);
        this.waitForElementNotPresent(
                article_xpath,
                "Saved article still present with tittle " + article_tittle,
                15
        );
    }

    public void waitForArticleToDisappearBySubtittle(String article_subtittle)
    {
        String article_xpath = getSavedArticleXpathBySubtittle(article_subtittle);
        this.waitForElementNotPresent(
                article_xpath,
                "Saved article still present with subtittle " + article_subtittle,
                15
        );
    }

    public void swipeByArticleToDelete(String article_tittle)
    {
        this.waitForArticleToAppearByTittle(article_tittle);
        String article_xpath = getSavedArticleXpathByTittle(article_tittle);
        this.swipeElementToLeft(
                article_xpath,
                "Cannot find saved article " + article_tittle
        );

            if (Platform.getInstance().isIOS()) {
                this.clickElementToTheRightUpperCorner(article_xpath, "Cannot find and click article delete button");
            }

        this.waitForArticleToDisappearByTittle(article_tittle);
    }

    public void swipeByArticleToDeleteWithSubtittle(String article_subtittle)
    {
        this.waitForArticleToAppearBySubtitle(article_subtittle);
        String article_xpath = getSavedArticleXpathBySubtittle(article_subtittle);
        this.swipeElementToLeft(
                article_xpath,
                "Cannot find saved article " + article_subtittle
        );

        if (Platform.getInstance().isIOS()) {
            this.clickElementToTheRightUpperCorner(article_xpath, "Cannot find and click article delete button");
        }

        this.waitForArticleToDisappearByTittle(article_subtittle);
    }


}
