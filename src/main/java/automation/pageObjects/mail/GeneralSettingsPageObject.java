package automation.pageObjects.mail;

import automation.steps.Base;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class GeneralSettingsPageObject extends Base {
    public GeneralSettingsPageObject(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = ".//p[.='По умолчанию']/../..//button[@data-test-id='edit']")
    WebElement editDefaultSignButton;

    @FindBy(xpath = ".//button[@data-test-id='save']")
    WebElement saveEditSignButton;

    @FindBy(xpath = ".//input[@data-test-id='name_input']")
    WebElement editSignNameInput;

    @FindBy(xpath = ".//div[@role='textbox']//div")
    WebElement editSignTextInput;

    /**
     * Нажать на кнопку редактирования подписи по-умолчанию.
     */
    public GeneralSettingsPageObject clickToEditDefaultSignButton(){
        click(editDefaultSignButton);
        return this;
    }

    /**
     * Заполнить поле "Имя отправителя" при редактировании подписи.
     * @param name Имя отправителя
     */
    public GeneralSettingsPageObject setSignName(String name){
        waitUntilElementVisible(editSignNameInput);
        editSignNameInput.sendKeys(name);
        return this;
    }

    /**
     * Заполнить поле "Подпись" при редактировании подписи.
     * @param text Имя отправителя
     */
    public GeneralSettingsPageObject setSignText(String text){
        waitUntilElementVisible(editSignTextInput);
        editSignTextInput.sendKeys(text);
        return this;
    }

    /**
     * Нажать кнопку "Сохранить" для сохранения измененной подписи
     */
    public GeneralSettingsPageObject clickSaveEditSignButton(){
        waitUntilElementVisible(saveEditSignButton);
        click(saveEditSignButton);
        return this;
    }

}
