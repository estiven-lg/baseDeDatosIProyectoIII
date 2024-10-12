package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Person {

    private String dpi;
    private String firstName;
    private String secondName;
    private String firstSurname;
    private String secondSurname;
    private String homeAddress;
    private String homePhone;
    private String mobilePhone;
    private float baseSalary;
    private float bonus;

    public Person(String dpi, String firstName, String secondName, String firstSurname, String secondSurname, String homeAdress, String homePhone, String mobilePhone, float baseSalary, float bonus) {
        this.dpi = dpi;
        this.firstName = firstName;
        this.secondName = secondName;
        this.firstSurname = firstSurname;
        this.secondSurname = secondSurname;
        this.homeAddress = homeAdress;
        this.homePhone = homePhone;
        this.mobilePhone = mobilePhone;
        this.baseSalary = baseSalary;
        this.bonus = bonus;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getFirstSurname() {
        return firstSurname;
    }

    public void setFirstSurname(String firstSurname) {
        this.firstSurname = firstSurname;
    }

    public String getSecondSurname() {
        return secondSurname;
    }

    public void setSecondSurname(String secondSurname) {
        this.secondSurname = secondSurname;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAdress) {
        this.homeAddress = homeAdress;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public float getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(float baseSalary) {
        this.baseSalary = baseSalary;
    }

    public float getBonus() {
        return bonus;
    }

    public void setBonus(float bonus) {
        this.bonus = bonus;
    }

    public static Person parseResultSetToPerson(ResultSet resultSet) throws SQLException {

        if (resultSet.next()) {
            String dpi = resultSet.getString("dpi");
            String firstName = resultSet.getString("first_name");
            String secondName = resultSet.getString("second_name");
            String firstSurname = resultSet.getString("first_surname");
            String secondSurname = resultSet.getString("second_surname");
            String homeAddress = resultSet.getString("home_address");
            String homePhone = resultSet.getString("home_phone");
            String mobilePhone = resultSet.getString("mobile_phone");
            float baseSalary = resultSet.getFloat("base_salary");
            float bonus = resultSet.getFloat("bonus");
            ;

            return new Person(dpi, firstName, secondName, firstSurname, secondSurname,
                    homeAddress, homePhone, mobilePhone, baseSalary, bonus);
        } else {

            return null;
        }
    }

    public static List<Person> parseResultSetToPersonList(ResultSet resultSet) throws SQLException {
        List<Person> persons = new ArrayList<>();

        while (resultSet.next()) {

            String dpi = resultSet.getString("dpi");
            String firstName = resultSet.getString("first_name");
            String secondName = resultSet.getString("second_name");
            String firstSurname = resultSet.getString("first_surname");
            String secondSurname = resultSet.getString("second_surname");
            String homeAddress = resultSet.getString("home_address");
            String homePhone = resultSet.getString("home_phone");
            String mobilePhone = resultSet.getString("mobile_phone");
            float baseSalary = resultSet.getFloat("base_salary");
            float bonus = resultSet.getFloat("bonus");

            Person person = new Person(dpi, firstName, secondName, firstSurname, secondSurname,
                    homeAddress, homePhone, mobilePhone, baseSalary, bonus);

            persons.add(person);
        }

        return persons;
    }

    @Override
    public String toString() {
        return "DPI:" + this.dpi + " Name:" + this.firstName + " " + this.secondSurname;
    }
}
