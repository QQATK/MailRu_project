package automation.pageObjects.mail;

import automation.steps.Base;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.*;
import java.time.Duration;

public class BoxPageObject extends Base {
    public BoxPageObject(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//a[@href='/compose/']")
    WebElement createMessageButton;

    @FindBy(xpath = ".//div[contains(@class, 'contacts')]//input")
    WebElement mailContact;

    @FindBy(xpath = ".//div[contains(@class, 'subject')]//input")
    WebElement mailSubject;

    @FindBy(xpath = ".//div[@role='textbox']/div[1]")
    WebElement firstMailEditorLine;

    @FindBy(xpath = ".//button[@data-test-id='send']")
    WebElement sendNewMailButton;

    @FindBy(xpath = ".//a[@data-folder-link-id='0']")
    WebElement inboxMailLink;

    @FindBy(xpath = ".//a[@href='/tomyself/?'][1]")
    WebElement myselfMailLink;

    @FindBy(xpath = ".//a[contains(@class, 'letter-list-item')][1]")
    WebElement lastRecievedMailRow;

    @FindBy(xpath = ".//a[contains(@class, 'letter-list-item')][1]//div[@class='llc__content']//div[@class='llc__item llc__item_title']//span[contains(@class,'llc__subject llc__subject_unread')]//div//span")
    WebElement lastRecievedMailSubject;

    @FindBy(xpath = ".//div[@data-widget='signature']//div[@data-signature-widget='content']//div")
    WebElement newMailSignText;

    @FindBy(xpath = ".//div[@data-signature-widget='content']//div")
    WebElement viewMailSignText;

    @FindBy(xpath = ".//div[@class='letter__author']//span")
    WebElement viewMailSignContact;


    /**
     * Закрыть окошко с сообщением об успешной отправке письма
     */
    public BoxPageObject closeSuccessMailFrame() {
        new Actions(driver)
                .sendKeys(Keys.ESCAPE)
                .perform();
        return this;
    }

    /**
     * Нажать на кнопку "Письма себе" для перехода к письмам, отправленным самому себе
     */
    public BoxPageObject goToMyselfMail() {
        click(myselfMailLink);
        return this;
    }

    /**
     * Проверяем соответствие темы последнего отправленного самому себе письма и переданной строке
     *
     * @param expectedSubject ожидаемая тема письма
     */
    public BoxPageObject assertLastRecievedMailSubject(String expectedSubject) {
        waitUntilUrlToBe("https://e.mail.ru/tomyself/");
        waitUntilElementVisible(lastRecievedMailSubject);
        Assert.assertEquals(lastRecievedMailSubject.getText(), expectedSubject);
        return this;
    }

    //TODO: заменить Thread.sleep на что то более подходящее. Ожидание нужно из-за того, что письмо может не сразу прогрузиться в ящике
    /**
     * Подождать 2 секунды и открыть на просмотр последнее полученное сообщение
     */
    public BoxPageObject openLastRecievedMailToVeiw() throws InterruptedException {
        Thread.sleep(2000);
        click(lastRecievedMailRow);
        return this;
    }


    /**
     * Проверяем подпись.
     *
     * @param expectedText ожидаемый текст подписи.
     */
    public BoxPageObject assertNewMailSign(final String expectedText) {
        Assert.assertEquals(newMailSignText.getText(), expectedText);
        return this;
    }


    /**
     * Проверяем подпись.
     *
     * @param expectedText Ожидаемый текст подписи. Находится внизу текста письма.
     * @param expectContact Ожидаемое имя отправителя. Отображается рядом с иконкой отправителя.
     */
    public BoxPageObject assertViewMailSign(final String expectContact,
                                            final String expectedText) {
        Assert.assertEquals(viewMailSignText.getText(), expectedText);
        Assert.assertEquals(viewMailSignContact.getText(), expectContact);
        return this;
    }


    /**
     * Нажать на кнопку создания нового письма.
     */
    @Step
    public BoxPageObject createNewMail() {
        // Нажать на кнопку "Написать письмо" для открытия формы создания письма
        click(createMessageButton);

        // Проверить, открылась ли форма создания нового письма
        String xpath = ".//div[contains(@class, 'editor_container')]";
        Assert.assertTrue(
                waitUntilElementVisible(xpath)
        );

        return this;
    }

    /**
     * Заполнить поля Кому, Тема, Текст письма. Шаг 5.1.
     *
     * @param addressee Адресат письма, поле "Кому"
     * @param subject   Тема письма
     * @param mailText  Текст письма
     */
    @Step
    public BoxPageObject fillMailData(final String addressee,
                                      final String subject,
                                      final String mailText) {

        // Заполнить поло "Кому" в новом письме
        waitUntilElementVisible(mailContact);
        mailContact.sendKeys(addressee);

        // Заполнить поле "Тема" в новом письме
        waitUntilElementVisible(mailSubject);
        mailSubject.sendKeys(subject);

        // Заполнить текст нового письма
        waitUntilElementVisible(firstMailEditorLine);
        firstMailEditorLine.sendKeys(mailText);

        return this;
    }


    /**
     * Нажать на кнопку создания нового письма.
     */
    @Step
    public BoxPageObject sendMail() {

        // Нажать на кнопку "Отправить" при создании письма
        click(sendNewMailButton);

        // Проверяем, что после отправки на форме отобразилось сообщение об успешной отправке
        String xpath = ".//a[@class='layer__link']";
        Assert.assertTrue(
                waitUntilElementVisible(xpath)
        );
        return this;
    }


}
