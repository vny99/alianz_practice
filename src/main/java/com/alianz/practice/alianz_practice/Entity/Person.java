package com.alianz.practice.alianz_practice.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Person {

    @Id
    private int id;
    private int age;
    private String sex;

    public Person() {
    }

    public Person(int id, int age, String sex) {
        this.id = id;
        this.age = age;
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Person [age=" + age + ", sex=" + sex + ", id=" + id + "]";
    }

}
