
package ru.netology.delivery.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {
    public String generateDate(long addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }


    @BeforeEach
    void setUpTests() {

        open("http://localhost:7777/");
    }

    @Test
    public void positiveTest() {
        String meetingDate = generateDate(3, "dd.MM.yyyy");

        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Ковалевская Яна");
        $("[name='phone']").setValue("+79896340085");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id='notification']").shouldHave(text("Встреча успешно забронирована на " + meetingDate), Duration.ofSeconds(15));

    }

    @Test
    public void cityNegativeNotCapitalTest() {
        String meetingDate = generateDate(3, "dd.MM.yyyy");

        $("[data-test-id='city'] input").setValue("Новочеркасск");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Ковалевская Яна");
        $("[name='phone']").setValue("+79896340085");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();

        $(withText("Доставка в выбранный город недоступна")).shouldBe(visible, Duration.ofSeconds(15));

    }

    @Test
    public void cityDontExistNegativeTest() {
        String meetingDate = generateDate(3, "dd.MM.yyyy");

        $("[data-test-id='city'] input").setValue("Аdcfgvhbjn");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Ковалевская Яна");
        $("[name='phone']").setValue("+79896340085");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Доставка в выбранный город недоступна")).shouldBe(visible, Duration.ofSeconds(15));

    }

    @Test
    public void cityEpmtyNegativeTest() {
        String meetingDate = generateDate(3, "dd.MM.yyyy");

        $("[data-test-id='city'] input").setValue("");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Ковалевская Яна");
        $("[name='phone']").setValue("+79896340085");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Поле обязательно для заполнения")).shouldBe(visible, Duration.ofSeconds(15));


    }

    @Test
    public void dateEarlierNowNegativeTest() {
        String meetingDate = generateDate(-5, "dd.MM.yyyy");

        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Ковалевская Яна");
        $("[name='phone']").setValue("+79896340085");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Заказ на выбранную дату невозможен")).shouldBe(visible, Duration.ofSeconds(15));


    }

    @Test
    public void dateEarlierNegativeTest() {
        String meetingDate = generateDate(1, "dd.MM.yyyy");

        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Ковалевская Яна");
        $("[name='phone']").setValue("+79896340085");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Заказ на выбранную дату невозможен")).shouldBe(visible, Duration.ofSeconds(15));


    }

    @Test
    public void nameHyphenTest() {
        String meetingDate = generateDate(3, "dd.MM.yyyy");

        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Ковалевская Анна-Мария");
        $("[name='phone']").setValue("+79896340085");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id='notification']").shouldHave(text("Встреча успешно забронирована на " + meetingDate), Duration.ofSeconds(15));

    }

    @Test
    public void nameSymbolNegativeTest() {
        String meetingDate = generateDate(3, "dd.MM.yyyy");

        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Ковалевская Яна!");
        $("[name='phone']").setValue("+79896340085");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Имя и Фамилия указаные неверно")).shouldBe(visible, Duration.ofSeconds(15));

    }

    @Test
    public void nameSpeсialTest() {
        String meetingDate = generateDate(3, "dd.MM.yyyy");

        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Ковалевская Алёна");
        $("[name='phone']").setValue("+79896340085");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id='notification']").shouldHave(text("Встреча успешно забронирована на " + meetingDate), Duration.ofSeconds(15));

    }

    @Test
    public void numberShortNegativeTest() {
        String meetingDate = generateDate(3, "dd.MM.yyyy");

        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Ковалевская Яна");
        $("[name='phone']").setValue("+79896340");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Телефон указан неверно.")).shouldBe(visible, Duration.ofSeconds(1));


    }

    @Test
    public void numberLongNegativeTest() {
        String meetingDate = generateDate(3, "dd.MM.yyyy");

        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Ковалевская Яна");
        $("[name='phone']").setValue("+798963404952444");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Телефон указан неверно.")).shouldBe(visible, Duration.ofSeconds(15));


    }

    @Test
    public void number8_NegativeTest() {
        String meetingDate = generateDate(3, "dd.MM.yyyy");

        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Ковалевская Яна");
        $("[name='phone']").setValue("89896340085");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Телефон указан неверно.")).shouldBe(visible, Duration.ofSeconds(15));


    }

    @Test
    public void numberDontPluse_NegativeTest() {
        String meetingDate = generateDate(3, "dd.MM.yyyy");

        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Ковалевская Яна");
        $("[name='phone']").setValue("79896340085");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Телефон указан неверно.")).shouldBe(visible, Duration.ofSeconds(15));


    }

    @Test
    public void negativeAgreementTest() {
        String meetingDate = generateDate(3, "dd.MM.yyyy");

        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(meetingDate);
        $("[data-test-id='name'] input").setValue("Ковалевская Яна");
        $("[name='phone']").setValue("+79896340085");
        $("[data-test-id='agreement']");
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id='notification']").shouldNotHave(text("Встреча успешно забронирована на " + meetingDate), Duration.ofSeconds(15));

    }


}
