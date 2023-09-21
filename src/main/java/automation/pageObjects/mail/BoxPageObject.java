package automation.pageObjects.mail;

import automation.steps.Base;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import javax.swing.*;

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

    @FindBy(xpath = ".//a[contains(@class, 'letter-list-item')][1]//span[@class='llc__subject llc__subject_unread']//span")
    WebElement lastRecievedMailSubject;

    @FindBy(xpath = ".//div[@data-widget='signature']//div[@data-signature-widget='content']//div")
    WebElement newMailSignText;



    /**
     * Нажать на кнопку "Написать письмо" для открытия формы создания письма
     */
    public BoxPageObject clickToCreateMessageButton() {
        click(createMessageButton);
        return this;
    }

    /**
     * Проверить, открылась ли форма создания нового письма
     */
    public BoxPageObject assertCreatingMessageFormIsOpen() {
        String xpath = ".//div[contains(@class, 'editor_container')]";
        Assert.assertTrue(
                waitUntilElementVisible(xpath)
        );
        return this;
    }

    /**
     * Заполнить текст нового письма
     *
     * @param txt String текст письма
     */
    public BoxPageObject setNewMailText(String txt) {
        waitUntilElementVisible(firstMailEditorLine);
        firstMailEditorLine.sendKeys(txt);
        return this;
    }

    /**
     * Заполнить поло "Кому" в новом письме
     *
     * @param contact String адрес электронной почти
     */
    public BoxPageObject setNewMailContact(String contact) {
        waitUntilElementVisible(mailContact);
        mailContact.sendKeys(contact);
        return this;
    }

    /**
     * Заполнить поле "Тема" в новом письме
     *
     * @param subject String тема письма
     */
    public BoxPageObject setNewMailSubject(String subject) {
        waitUntilElementVisible(mailSubject);
        mailSubject.sendKeys(subject);
        return this;
    }

    /**
     * Нажать на кнопку "Отправить" при создании письма
     */
    public BoxPageObject sendNewMail() {
        click(sendNewMailButton);
        return this;
    }

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
     * Проверяем, что после отправки на форме отобразилось сообщение об успешной отправке
     */
    public BoxPageObject assertSuccessSent() {
        String xpath = ".//a[@class='layer__link']";
        Assert.assertTrue(
                waitUntilElementVisible(xpath)
        );
        return this;
    }

    /**
     * Нажать на кнопку "Входящие" для перехода к полученным письмам
     */
    public BoxPageObject goToInboxMail() {
        click(inboxMailLink);
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
     * @param expectedSubject ожидаемая тема письма
     */
    public BoxPageObject assertLastRecievedMailSubject(String expectedSubject){
        waitUntilUrlToBe("https://e.mail.ru/tomyself/");
        Assert.assertEquals(lastRecievedMailSubject.getText(), expectedSubject);
        return this;
    }

    /**
     * Открываем на просмотр последнее полученное сообщение
     */
    public BoxPageObject openMailToVeiw(){
        click(lastRecievedMailRow);
        return this;
    }


    /**
     * Проверяем подпись.
     * @param expectedText ожидаемый текст подписи.
     */
    public BoxPageObject assertNewMailSign(final String expectedText){
        Assert.assertEquals(newMailSignText.getText(), expectedText);
        return this;
    }


}
