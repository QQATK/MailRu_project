package automation.pageObjects.mail;

import automation.steps.Base;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class GeneralSettingsPageObject extends Base {
    public GeneralSettingsPageObject(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = ".//p[.='По умолчанию']/../..//button[@data-test-id='edit']")
    WebElement editDefaultSignButton;

    @FindBy(xpath = ".//div[contains(@class,'Signature')]//button[@data-test-id='save']")
    WebElement saveEditSignButton;

    @FindBy(xpath = ".//input[@data-test-id='name_input']")
    WebElement editSignNameInput;

    @FindBy(xpath = ".//div[@role='textbox']//div")
    WebElement editSignTextInput;


    /**
     * Отредактировать подпись по умолчанию
     *
     * @param signName новый получатель в подписи
     * @param signText новый текст подписи
     */
    @Step
    public GeneralSettingsPageObject editDefaultSign(
            final String signName,
            final String signText) {

        // Нажать на кнопку редактирования подписи по-умолчанию.
        click(editDefaultSignButton);

        // Заполнить поле "Имя отправителя" при редактировании подписи.
        waitUntilElementVisible(editSignNameInput);
        editSignNameInput.clear();
        editSignNameInput.sendKeys(signName);

        // Заполнить поле "Подпись" при редактировании подписи.
        waitUntilElementVisible(editSignTextInput);
        editSignTextInput.clear();
        editSignTextInput.sendKeys(signText);

        // Нажать кнопку "Сохранить" для сохранения измененной подписи
        waitUntilElementVisible(saveEditSignButton);
        click(saveEditSignButton);

        return this;
    }

}
