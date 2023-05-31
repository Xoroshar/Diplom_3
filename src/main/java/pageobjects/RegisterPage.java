package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class RegisterPage {
    private final WebDriver driver;
    private final By nameInput = By.xpath(".//fieldset[@class='Auth_fieldset__1QzWN mb-6'][1]//input");
    private final By emailInput = By.xpath(".//fieldset[@class='Auth_fieldset__1QzWN mb-6'][2]//input");
    private final By passwordInput = By.xpath(".//fieldset[@class='Auth_fieldset__1QzWN mb-6'][3]//input");
    private final By registrationButton = By.xpath("//button[text()='Зарегистрироваться']");
    private final By notSuitablePasswordError = By.xpath(".//p[text()='Некорректный пароль']");
    private final By loginButton = By.xpath("//a[text()='Войти']");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
    }

    public void pressRegistrationButton() {
        driver.findElement(registrationButton).click();
    }

    public void inputName(String name) {
        driver.findElement(passwordInput).sendKeys(Keys.chord(Keys.CONTROL, "a"));
        driver.findElement(passwordInput).sendKeys(Keys.DELETE);
        driver.findElement(nameInput).sendKeys(name);
    }

    public void inputEmail(String email) {
        driver.findElement(passwordInput).sendKeys(Keys.chord(Keys.CONTROL, "a"));
        driver.findElement(passwordInput).sendKeys(Keys.DELETE);
        driver.findElement(emailInput).sendKeys(email);
    }

    public void inputPassword(String password) {
        driver.findElement(passwordInput).sendKeys(Keys.chord(Keys.CONTROL, "a"));
        driver.findElement(passwordInput).sendKeys(Keys.DELETE);
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void inputAllFieldsAndPressRegistrationButton(String name, String email, String password) {
        inputName(name);
        inputEmail(email);
        inputPassword(password);
        pressRegistrationButton();
    }

    public boolean checkMessageAboutNotSuitablePasswordAppear() {
        return driver.findElement(notSuitablePasswordError).isDisplayed();
    }

    public void pressOnLoginButton() {
        driver.findElement(loginButton).click();
    }

}
