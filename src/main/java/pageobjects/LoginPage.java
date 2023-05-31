package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private final WebDriver driver;

    private final By registrationButton = By.xpath(".//a[text()='Зарегистрироваться']");
    private final By emailInput = By.xpath(".//input[@name='name']");
    private final By passwordInput = By.xpath(".//input[@name='Пароль']");
    private final By loginButton = By.xpath(".//button[text()='Войти']");
    private final By forgotPasswordButton = By.xpath("//a[text()='Восстановить пароль']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void pressOnRegisterButton() {
        driver.findElement(registrationButton).click();
    }

    public void pressOnLoginButton() {
        driver.findElement(loginButton).click();
    }

    public void pressOnForgotPassword() {
        driver.findElement(forgotPasswordButton).click();
    }

    public void inputEmail(String email) {
        driver.findElement(emailInput).sendKeys(email);
    }

    public void inputPassword(String password) {
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void fillEmailPasswordAndPressLoginButton(String email, String password) {
        inputEmail(email);
        inputPassword(password);
        pressOnLoginButton();
    }

    public void waitLoginButtonToAppear() {
        new WebDriverWait(driver, 5).until(driver -> (driver.findElement(loginButton).getText() != null
                && !driver.findElement(loginButton).getText().isEmpty()));
    }

    public boolean checkLoginButtonAppeared() {
        return driver.findElement(loginButton).isDisplayed();
    }

}
