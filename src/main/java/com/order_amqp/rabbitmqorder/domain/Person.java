package com.order_amqp.rabbitmqorder.domain;

public class Person {
    private String username;
    private String phone;
    private int age;

    public Person() {
    }

    public Person(String username, String phone, int age) {
        this.username = username;
        this.phone = phone;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", age=" + age +
                '}';
    }
}
