package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.GameFactory;
import models.UserFactory;
import play.mvc.*;
import scala.Int;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */


    UserFactory users;
    GameFactory games;
    UserFactory.User user;

    @Inject
    public HomeController(UserFactory users, GameFactory games) {
        this.users = users;
        this.games = games;
    }

    public Result login() {
        return ok(views.html.login.render());
    }

    public Result register() {return ok(views.html.register.render());}

    public Result main(Http.Request request) {
        if(check_for_login_user(request)){
            return ok(views.html.main.render());
        } else {
            return ok(views.html.login.render());
        }
    }

    public Result profile(Http.Request request) {
        ProfileController profileController = new ProfileController(users);
        Http.Session session = request.session();
        String username = session.get("username").get();
        profileController.getFriends(username);
        String mail = profileController.getEmail(username);

        if(check_for_login_user(request)){
            return ok(views.html.profile.render(mail));
        } else {
            return ok(views.html.login.render());
        }

    }

    public Result highscore(Http.Request request) {
        HighscoreController highscoreController = new HighscoreController(users);
        Http.Session session = request.session();
        String username = session.get("username").get();
        LinkedList<Map.Entry<String, UserFactory.Score>> topFiveAndUser = highscoreController.get_highscore(username);
        List<String> names = new ArrayList<>();
        List<Integer> scores = new ArrayList<>();
        for (Map.Entry<String, UserFactory.Score> entry : topFiveAndUser){
            names.add(entry.getKey());
            scores.add(entry.getValue().getHighscore());
        }
        if(check_for_login_user(request)){
            return ok(views.html.highscore.render(names, scores, username));
        } else {
            return ok(views.html.login.render());
        }
    }

    public Result game1(Http.Request request) {
        if(check_for_login_user(request)){
            GameController gameController = new GameController(games);
            System.out.println(gameController.getIngredient("bean"));
            System.out.println(gameController.getMachine(1));
            System.out.println(gameController.getRecipe("cappuccino"));
            System.out.println(gameController.getSpoon(1));
            return ok(views.html.game1.render());
        } else {
            return ok(views.html.login.render());
        }

    }

    public boolean check_for_login_user(Http.Request request){
        Http.Session x = request.session();
        Optional username = x.get("username");

        if(username.isEmpty()){
            return false;
        } else {
            return true;
        }
    }

}
