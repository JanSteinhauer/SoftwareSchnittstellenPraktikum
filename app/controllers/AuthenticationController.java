package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.UserFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;

public class AuthenticationController extends Controller {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    UserFactory users;

    @Inject
    public AuthenticationController(UserFactory users) {
        this.users = users;
    }

    public Result authenticate(Http.Request request) {
        try {
            JsonNode json = request.body().asJson();
            String username = json.get("username").asText();
            String password = json.get("password").asText();

            //UserFactory.User user = users.create(username, password, "admin@gmail.com");

            if (!username.isEmpty() && !password.isEmpty()) {
                UserFactory.User user = users.authenticate(username, password);

                if (user != null) {
                    logger.info("User " + username + " was added to the session.");
                    return ok("Welcome " + username)
                            .addingToSession(request, "username", username);
                } else {
                    return unauthorized("Invalid username or password");
                }
            } else {
                return forbidden("Field(s) can't be empty");
            }
        } catch (Exception e) {
            return badRequest("Missing parameter [username or password]");
        }
    }

    public Result register(Http.Request request) {
        try {
            JsonNode json = request.body().asJson();

            String username = json.get("username").asText();
            String password = json.get("password").asText();
            String email = json.get("email").asText();

            if (!username.isEmpty() && !password.isEmpty() && !email.isEmpty()) {
                UserFactory.User userHasName = users.getUserByName(username);
                UserFactory.User userHasEmail = users.getUserByEmail(email);

                if (userHasName == null && userHasEmail == null) {
                    users.create(email, username, password);
                    return ok();
                } else if (userHasEmail != null) {
                    return notAcceptable(email + "already registered");
                } else {
                    return badRequest(username + " already exists");
                }
            } else {
                return forbidden("Field(s) can't be empty");
            }
        } catch (Exception e) {
            return forbidden("Something went wrong");        }

    }

    public Result logout(Http.Request request) {
        logger.info("User was removed from the session.");
        return redirect("/login").removingFromSession(request, "username");
    }
}
