package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class MyListsPageObject extends MainPageObject {

    public static final String
        FOLDER_BY_NAME_TPL = "//*[@text='{FOLDER_NAME}']",
        ARTICLE_BY_TITLE_TPL = "//*[@text='{TITTLE}']";

    private static String getFolderByName(String name_of_folder)
    {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }

    private static String getSavedArticleXpathByTittle(String article_tittle)
    {
        return ARTICLE_BY_TITLE_TPL.replace("{TITTLE}", article_tittle);
    }

    public MyListsPageObject(AppiumDriver driver)
    {
        super(driver);
    }

    public void waitForFolderToAppear(String name_of_folder)
    {
        String folder_name_xpath = getFolderByName(name_of_folder);
        this.waitForElementPresent(
                By.xpath(folder_name_xpath),
                "Cannot find folder by name " + name_of_folder,
                5
        );
    }

    public void openFolderByName(String name_of_folder)
    {
        String folder_name_xpath = getFolderByName(name_of_folder);
        this.waitForElementAndClick(
                By.xpath(folder_name_xpath),
                "Cannot find folder by name " + name_of_folder,
                5
        );
    }

    public void waitForArticleToAppearByTittle(String article_tittle)
    {
        String article_xpath = getSavedArticleXpathByTittle(article_tittle);
        this.waitForElementPresent(
                By.xpath(article_xpath),
                "Cannot find saved article by tittle " + article_tittle,
                15
        );
    }

    public void waitForArticleToDisappearByTittle(String article_tittle)
    {
        String article_xpath = getSavedArticleXpathByTittle(article_tittle);
        this.waitForElementNotPresent(
                By.xpath(article_xpath),
                "Saved article still present with tittle " + article_tittle,
                15
        );
    }

    public void swipeByArticleToDelete(String article_tittle)
    {
        this.waitForArticleToAppearByTittle(article_tittle);
        String article_xpath = getSavedArticleXpathByTittle(article_tittle);
        this.swipeElementToLeft(
                By.xpath(article_xpath),
                "Cannot find saved article " + article_tittle
        );

        this.waitForArticleToDisappearByTittle(article_tittle);
    }

}
