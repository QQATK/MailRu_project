package automation.pageObjects.mail;

import automation.steps.Base;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MailViewPageObject extends Base {
    public MailViewPageObject(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = ".//h2[@class='thread-subject']")
    WebElement mailSubject;

    @FindBy(xpath = ".//div[@class='letter-body__body-content']//div[.='Hello world!']")
    WebElement mailText;


    /**
     * Проверяем, что тема письма соответствует ожидаемому.
     * @param expectedSubject ожидаемая тема письма.
     */
    public MailViewPageObject assertMailSubject(String expectedSubject){
        waitUntilElementVisible(mailSubject);
        Assert.assertEquals(
                mailSubject.getText(),
                expectedSubject);
        return this;
    }

    /**
     * Проверяем, что текст письма соответствует ожидаемому.
     * @param expectedText ожидаемый текст письма.
     */
    public MailViewPageObject assertMailText(String expectedText){
        waitUntilElementVisible(mailText);
        Assert.assertEquals(
                mailText.getText(),
                expectedText);
        return this;
    }

}
