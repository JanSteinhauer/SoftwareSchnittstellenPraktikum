package controllers;

import models.UserFactory;

import javax.inject.Inject;
import java.util.*;

public class HighscoreController {

    UserFactory users;

    @Inject
    public HighscoreController(UserFactory users) {
        this.users = users;
    }

    public LinkedList<Map.Entry<String, UserFactory.Score>> get_highscore(String username){
        HashMap<String, UserFactory.Score> highscore_level = new HashMap<>();
        List<UserFactory.User> userlist = users.getAllUsers();
        List<UserFactory.Score> scoreList = users.getAllScores();


        // map die Users zu den Score
        for (UserFactory.User user: userlist){
            for (UserFactory.Score score: scoreList){
                if(user.getScore_id() == score.get_id()) {
                    highscore_level.put(user.getUsername(), score);
                }
            }
        }


/*        for (Map.Entry<String, UserFactory.Score> entry : highscore_level.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue().get_id());
        }*/


        /*List<Map.Entry<String, UserFactory.Score>> resultList = new ArrayList<>(highscore_level.entrySet());*/

        LinkedList<Map.Entry<String, UserFactory.Score>> resultList = new LinkedList<>();
        for (Map.Entry<String, UserFactory.Score> entry : highscore_level.entrySet()) {
            resultList.add(entry);
        }

        // TODO bubble sort is implemented
        for (int i = 0; i < resultList.size() - 1; i++) {
            for (int j = 0; j < resultList.size() - i - 1; j++) {
                if (resultList.get(j).getValue().getHighscore() < resultList.get(j + 1).getValue().getHighscore()) {
                    Map.Entry<String, UserFactory.Score> temp = resultList.get(j);
                    resultList.set(j, resultList.get(j + 1));
                    resultList.set(j + 1, temp);
                }
            }
        }


        // INFO wenn user unter den
        boolean lastuser = false;
        if(resultList.size() >= 6){
            while (resultList.size() > 6) {
                if(resultList.getLast().getKey().equals(username)){
                    resultList.addFirst(resultList.getLast());
                    lastuser = true;
                }
                resultList.removeLast();
            }
        }

        if(lastuser){
            resultList.addLast(resultList.getFirst());
            resultList.removeFirst();
        }

/*        for (int i = 0; i < resultList.size(); i++) {
            System.out.println(resultList.get(i).getKey() + ": " + resultList.get(i).getValue().getHighscore());
        }*/

        return resultList;
    }
}
