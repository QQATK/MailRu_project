package automation.pageObjects.mail;

import automation.steps.Base;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


/**
 * Форма просмотра письма
 */
public class MailViewPageObject extends Base {
    public MailViewPageObject(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = ".//h2[@class='thread-subject']")
    WebElement mailSubject;

    @FindBy(xpath = ".//div[@data-signature-widget='content']//div")
    WebElement viewMailSignText;

    @FindBy(xpath = ".//div[@class='letter__author']//span")
    WebElement viewMailSignContact;


    ///// =======================================
    /////       АТОМАРНЫЕ МЕТОДЫ
    ///// =======================================

    /**
     * Проверяем, что тема письма соответствует ожидаемому.
     *
     * @param expectedSubject ожидаемая тема письма.
     */
    @Step("Проверяем, что тема письма соответствует ожидаемому. Ожидаем = {expectedSubject}")
    public MailViewPageObject assertMailSubject(String expectedSubject) {
        waitUntilElementVisible(mailSubject);
        Assert.assertEquals(
                mailSubject.getText(),
                expectedSubject);
        return this;
    }

    /**
     * Проверяем, что текст письма соответствует ожидаемому.
     *
     * @param expectedText ожидаемый текст письма.
     */
    @Step("Проверяем, что текст письма соответствует ожидаемому. Ожидаем = {expectedText}")
    public MailViewPageObject assertMailText(String expectedText) {

        String xpath = ".//div[@class='letter-body__body-content']//div[.='" + expectedText + "']";

        waitUntilElementVisible(xpath);
        WebElement mailText = getDriver().findElement(By.xpath(xpath));

        Assert.assertEquals(
                mailText.getText(),
                expectedText);
        return this;
    }

    /**
     * Проверяем отправителя подписи при просмотре письма
     *
     * @param expectContact Ожидаемое имя отправителя. Отображается рядом с иконкой отправителя.
     */
    @Step("Проверяем отправителя подписи. Ожидаем = {expectContact}")
    public MailViewPageObject assertViewMailSignContact(final String expectContact) {
        Assert.assertEquals(viewMailSignContact.getText(), expectContact);
        return this;
    }

    /**
     * Проверяем текст подписи при просмотре письма
     *
     * @param expectedText Ожидаемый текст подписи. Отображается рядом с иконкой отправителя.
     */
    @Step("Проверяем отправителя подписи. Ожидаем = {expectedText}")
    public MailViewPageObject assertViewMailSignText(final String expectedText) {
        Assert.assertEquals(viewMailSignText.getText(), expectedText);
        return this;
    }

}
