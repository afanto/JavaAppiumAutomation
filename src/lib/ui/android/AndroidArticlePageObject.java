package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.ArticlePageObject;

public class AndroidArticlePageObject extends ArticlePageObject  {

     static {
         TITTLE = "id:org.wikipedia:id/view_page_title_text";
                 FOOTER_ELEMENT = "xpath://*[@text='View page in browser']";
                 OPTIONS_BUTTON = "xpath://android.widget.ImageView[@content-desc='More options']";
                 OPTIONS_CHANGE_LANGUAGE_BUTTON = "//*[@text='Change language']";
                 OPTIONS_ADD_TO_READING_LIST_BUTTON = "//*[@text='Add to reading list']";
                 ADD_TO_MY_LIST_OVERLAY = "id:org.wikipedia:id/onboarding_button";
                 MY_LIST_NAME_INPUT = "id:org.wikipedia:id/text_input";
                 MY_LIST_OK_BUTTON = "xpath://*[@text='OK']";
                 CLOSE_ARTICLE_BUTTON = "xpath://android.widget.ImageButton[@content-desc='Navigate up']";
     }

     public AndroidArticlePageObject(AppiumDriver driver)
     {
         super(driver);
     }
}
