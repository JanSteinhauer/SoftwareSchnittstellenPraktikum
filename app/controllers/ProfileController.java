package controllers;

import models.UserFactory;
import scala.collection.LinearSeq;

import java.util.List;

public class ProfileController {
    UserFactory users;

    public ProfileController(UserFactory users) {
        this.users = users;
    }

    public String getEmail(String username){
        UserFactory.User user = users.getUserByName(username);

        return user.getMail();
    }

    public List<UserFactory.User> getFriends(String username){
        UserFactory.User user = users.getUserByName(username);
        List<UserFactory.User> friends = user.getFriends();

        return friends;
    }


}
