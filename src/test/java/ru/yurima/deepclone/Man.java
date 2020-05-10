package ru.yurima.deepclone;

import java.util.ArrayList;
import java.util.List;

class Man {
    private String name;
    private int age;
    private List<String> favoriteBooks;
    private List<Man> friends = new ArrayList<>();

    public Man(String name, int age, List<String> favoriteBooks) {
        this.name = name;
        this.age = age;
        this.favoriteBooks = favoriteBooks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getFavoriteBooks() {
        return favoriteBooks;
    }

    public void setFavoriteBooks(List<String> favoriteBooks) {
        this.favoriteBooks = favoriteBooks;
    }

    public List<Man> getFriends() {
        return friends;
    }

    public boolean addFriend(Man man){
        return friends.add(man);
    }

    public boolean removeFriend(Man man) {
        return friends.remove(man);
    }

}
