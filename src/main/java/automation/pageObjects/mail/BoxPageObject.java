package automation.pageObjects.mail;

import automation.steps.Base;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;


/**
 * Страница почтового ящика с папками и письмами
 */
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

    @FindBy(xpath = ".//a[contains(@class, 'letter-list-item')][1]//div[@class='llc__content']" +
            "//div[@class='llc__item llc__item_title']//span[contains(@class,'llc__subject')]//div//span")
    WebElement lastRecievedMailSubject;

    @FindBy(xpath = ".//div[@data-widget='signature']//div[@data-signature-widget='content']//div")
    WebElement newMailSignText;

    @FindBy(xpath = ".//span[@title='Выделить все']")
    WebElement selectAllMailButton;

    @FindBy(xpath = ".//a[contains(@class, 'letter-list-item')][1]//input")
    WebElement mailCheckbox;

    @FindBy(xpath = ".//div[@class='portal-menu-element portal-menu-element_remove portal-menu-element_expanded " +
            "portal-menu-element_not-touch']")
    WebElement deleteMailButton;


    /**
     * Закрыть окошко с сообщением об успешной отправке письма
     */
    @Step
    public BoxPageObject closeSuccessMailFrame() {
        new Actions(driver)
                .sendKeys(Keys.ESCAPE)
                .perform();
        return this;
    }


    /**
     * Нажать на кнопку "Письма себе" для перехода к письмам, отправленным самому себе
     */
    @Step
    public BoxPageObject goToMyselfMail() {
        click(myselfMailLink);
        return this;
    }


    /**
     * Проверяем соответствие темы последнего отправленного самому себе письма и переданной строке
     *
     * @param expectedSubject ожидаемая тема письма
     */
    @Step
    public BoxPageObject assertLastRecievedMailSubject(String expectedSubject) {
        waitUntilUrlToBe("https://e.mail.ru/tomyself/");
        getDriver().navigate().refresh();
        waitUntilElementVisible(lastRecievedMailSubject);
        Assert.assertEquals(lastRecievedMailSubject.getText(), expectedSubject);
        return this;
    }


    /**
     * Открыть на просмотр последнее полученное сообщение
     */
    @Step
    public BoxPageObject openLastRecievedMailToVeiw() {
        click(lastRecievedMailRow);
        return this;
    }


    /**
     * Проверяем подпись.
     *
     * @param expectedText ожидаемый текст подписи.
     */
    @Step
    public BoxPageObject assertNewMailSign(final String expectedText) {
        Assert.assertEquals(newMailSignText.getText(), expectedText);
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
        setText(mailContact, addressee);

        // Заполнить поле "Тема" в новом письме
        setText(mailSubject, subject);

        // Заполнить текст нового письма
        setText(firstMailEditorLine, mailText);

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


    /**
     * Получить WebElement письма в списке писем папки по его полю "Тема".
     *
     * @param subject Тема письма.
     * @return WebElement, представляющий письмо в списке папки. Вернет null если элемент не был найден.
     */
    @Step
    public WebElement findMailInBoxBySubject(String subject) {
        // элемент, общий для письма в папке
        String xpath = ".//a[contains(@class, 'letter-list-item')]//div[@class='llc__content']" +
                "//div[@class='llc__item llc__item_title']//span[contains(@class,'llc__subject')]" +
                "//div//span[text()='" + subject + "']/ancestor::a";
        try {
            // найти элемент письма и вернуть его
            waitUntilElementVisible(xpath);
            return getDriver().findElement(By.xpath(xpath));
        } catch (NoSuchElementException e) {
            // если элемент не найден - вернуть null
            return null;
        }
    }


    /**
     * Удалить письмо из папки по его полю "Тема".
     *
     * @param subject Тема письма.
     */
    @Step
    public BoxPageObject removeMailFromBox(String subject) {

        // Найти строку с письмом которое хотим удалить
        WebElement mailToRemove = findMailInBoxBySubject(subject);

        // навести на нее курсор
        new Actions(getDriver()).moveToElement(mailToRemove).perform();

        // найти чекбокс письма
        WebElement mailCheckbox = mailToRemove.findElement(
                By.xpath(".//button[@class='ll-av ll-av_centered ll-av_size_common']"));

        // отметить письмо
        click(mailCheckbox);

        // нажать "Удалить"
        click(deleteMailButton);

        return this;
    }

    /**
     * Проверяем, что письма больше нет в папке.
     *
     * @param subject Тема письма.
     */
    @Step
    public BoxPageObject assertMailIsNotInBox(String subject) {
        Assert.assertNull("Письмо с темой '" + subject + "' было найдено в папке.",
                findMailInBoxBySubject(subject));
        return this;
    }

}
