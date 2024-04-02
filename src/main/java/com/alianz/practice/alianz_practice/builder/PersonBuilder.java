package com.alianz.practice.alianz_practice.builder;

import com.alianz.practice.alianz_practice.Entity.Person;
import com.alianz.practice.alianz_practice.model.PersonModel;

public class PersonBuilder {
    private int id;
    private int age;
    private String sex;

    public static PersonBuilder from (PersonModel person ){
        return new PersonBuilder().withId(person.getId()).withAge(person.getAge()).withSex(person.getSex());
    }

    private PersonBuilder withId(int i) {
        this.id = i;
       return this;
    }

    private PersonBuilder withAge(int i) {
        this.age = i;
        return this;
     }

     private PersonBuilder withSex(String s) {
        this.sex = s;
        return this;
     }
    
     public Person build(){
        return new Person(id, age, sex);
     }
}
