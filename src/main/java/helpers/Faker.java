package helpers;

import java.text.DateFormatSymbols;

import org.joda.time.LocalDate;

import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.PersonProperties;

public class Faker {
    Fairy fairy;
    LocalDate localDate;
    String day, month, year;

    public Faker() {
        fairy = Fairy.create();
    }

    public String getFullName() {
        return fairy.person(PersonProperties.male()).getFullName();
    }

    public String getFemaleFirstName() {
        return fairy.person(PersonProperties.female()).getFirstName();
    }

    public void generateLocalDate() {
        localDate = fairy.person(PersonProperties.withAge(30)).getDateOfBirth().toLocalDate();
        day = String.valueOf(localDate.getDayOfMonth());
        month = getMonthName(localDate.getMonthOfYear());
        year = String.valueOf(localDate.getYear());
    }

    private String getMonthName(int monthNumber) {
        String month = "wrong";
        DateFormatSymbols monthsNames = new DateFormatSymbols();
        String[] months = monthsNames.getMonths();
        if (monthNumber >= 0 && monthNumber <= 11) {
            month = months[monthNumber];
        }
        return month;
    }

    public String getPassword() {
        return fairy.person(PersonProperties.withUsername("Amado")).getPassword();
    }

    public String getMaleFirstName() {
        return fairy.person(PersonProperties.male()).getFirstName();
    }

    public String getMaleLastName() {
        return fairy.person(PersonProperties.male()).getLastName();
    }

    public String getRandomNumber() {
        return fairy.person(PersonProperties.male()).getNationalIdentityCardNumber();
    }

    public String getAge() {
        return String.valueOf(fairy.person(PersonProperties.minAge(14)).getAge());
    }

    public String getCity() {
        return fairy.person(PersonProperties.female()).getAddress().getCity();
    }

    public String getCityMalePerson() {
        return fairy.person(PersonProperties.male()).getAddress().getCity();
    }

    public String getEmail() {
        return fairy.person().getEmail();
    }

    public String getPhone() {
        return fairy.person().getTelephoneNumber();
    }

    public String getAddress() {
        return fairy.person().getAddress().getAddressLine1();
    }

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public String getDayOfMonth() {
        return day;
    }

    public String getRandomMessage() {

        return fairy.textProducer().sentence();
    }
}
