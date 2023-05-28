package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {
    private final WebDriver driver;
    private final String color = "color";
    private final String boxShadowColor = "box-shadow";

    private final By personalAccount = By.xpath(".//p[text()='Личный Кабинет']");
    private final By loginButton = By.xpath(".//button[text()='Войти в аккаунт']");
    private final By orderButton = By.xpath("//button[text()='Оформить заказ']");
    private final By burgerTitle = By.xpath("//h1[text()='Соберите бургер']");
    private final By bulki = By.xpath("//span[text()='Булки']");
    private final By bulkiTab = By.xpath("//div[@class='tab_tab__1SPyG tab_tab_type_current__2BEPc pt-4 pr-10 pb-4 pl-10 noselect']");
    private final By souse = By.xpath("//span[text()='Соусы']");
    private final By souseTab = By.xpath("//div[@class='tab_tab__1SPyG tab_tab_type_current__2BEPc pt-4 pr-10 pb-4 pl-10 noselect']");
    private final By nachinka = By.xpath("//span[text()='Начинки']");
    private final By nachinkaTab = By.xpath("//div[@class='tab_tab__1SPyG tab_tab_type_current__2BEPc pt-4 pr-10 pb-4 pl-10 noselect']");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void pressOnPersonalAccount() {
        driver.findElement(personalAccount).click();
    }

    public void pressOnLoginButton() {
        driver.findElement(loginButton).click();
    }

    public void Login(String item) {
        if (item.equals(Definitions.MAIN_LOGIN_BUTTON)) {
            pressOnLoginButton();
        } else {
            pressOnPersonalAccount();
        }
    }

    public void waitOrderButtonToAppear() {
        new WebDriverWait(driver, 5).until(driver -> (driver.findElement(orderButton).getText() != null
                && !driver.findElement(orderButton).getText().isEmpty()));
    }
    public void waitFontToChange() throws InterruptedException {
        Thread.sleep(1500);
    }

    public boolean checkOrderButtonAppeared() {
        return driver.findElement(orderButton).isDisplayed();
    }

    public boolean checkBurgerTitleAppeared() {
        return driver.findElement(burgerTitle).isDisplayed();
    }

    public void clickOnSouse() {
        driver.findElement(souse).click();
    }

    public void clickOnNachinka() {
        driver.findElement(nachinka).click();
    }

    public void clickOnBulka() {
        driver.findElement(bulki).click();
    }
    public String getSouseColor() {
        return driver.findElement(souse).getCssValue(color).toString();
    }

    public String getNachinkaColor() {
        return driver.findElement(nachinka).getCssValue(color).toString();
    }

    public String getBulkiColor() {
       return driver.findElement(bulki).getCssValue(color).toString();
    }

    public String getBoxShadowBulki() {
        return driver.findElement(bulkiTab).getCssValue(boxShadowColor).toString();
    }
    public String getBoxShadowSouse() {
        return driver.findElement(souseTab).getCssValue(boxShadowColor).toString();
    }

    public String getBoxShadowNachinka() {
        return driver.findElement(nachinkaTab).getCssValue(boxShadowColor).toString();
    }
}
