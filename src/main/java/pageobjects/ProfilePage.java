package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProfilePage {
    private final WebDriver driver;
    private final By profileText = By.xpath("//a[text()='Профиль']");
    private final By constructor = By.xpath("//p[text()='Конструктор']");
    private final By logo = By.className("AppHeader_header__logo__2D0X2");
    private final By exitButton = By.xpath("//button[text()='Выход']");

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean checkProfileLabelAppeared() {
        return driver.findElement(profileText).isDisplayed();
    }

    public void waitProfileLabelToAppear() {
        new WebDriverWait(driver, 5).until(driver -> (driver.findElement(profileText).getText() != null
                && !driver.findElement(profileText).getText().isEmpty()));
    }

    public void clickOnLogo() {
        driver.findElement(logo).click();
    }

    public void clickOnConstructor() {
        driver.findElement(constructor).click();
    }

    public void clickOnExitButton() {
        driver.findElement(exitButton).click();
    }
}
