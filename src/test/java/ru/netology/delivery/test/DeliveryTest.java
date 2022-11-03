package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryTest {


    @BeforeEach
    public void setup() {
        open("http://localhost:9999");

    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    public void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);

        $("[data-test-id='city'] input").setValue(validUser.getCity());
        $x("//input[@placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//input[@placeholder='Дата встречи']").setValue(firstMeetingDate);
        $x("//input[@name='name']").setValue(validUser.getName());
        $x("//input[@name='phone']").setValue(validUser.getPhone());
        $("[data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $("[class='notification__content']").shouldHave(Condition.exactText("Встреча успешно запланирована на " + firstMeetingDate))
                .shouldBe(Condition.visible);
        $x("//input[@placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//span[@class='button__text']").click();
        $x("//button[contains(@class,'button')]").click();
        $x("//input[@placeholder='Дата встречи']").setValue(secondMeetingDate);
        $x("//span[@class='button__text']").click();
        $("[data-test-id=\"replan-notification\"]").shouldHave(Condition.text("Необходимо подтверждение У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $(byText("Перепланировать")).click();
        $("[data-test-id=\"success-notification\"]").shouldHave(Condition.text("Успешно! Встреча успешно запланирована на " + secondMeetingDate))
               .shouldBe(Condition.visible);

    }
}



