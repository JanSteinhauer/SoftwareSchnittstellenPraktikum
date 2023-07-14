package controllers;



import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.GameFactory;
import models.UserFactory;
import play.api.http.MediaType;
import play.mvc.Http;
import play.mvc.Result;
import com.fasterxml.jackson.databind.JsonNode;
import models.UserFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.twirl.api.Content;


import javax.inject.Inject;
import java.util.*;

import static play.mvc.Results.forbidden;
import static play.mvc.Results.ok;

public class GameController {

    GameFactory game;

    @Inject
    public GameController(GameFactory game) {
        this.game = game;
    }


    public String getIngredient(String name){
        GameFactory.Ingredient ingredient = game.getIngredient(name);
        return ingredient.getName();

    }

    public String getMachine(int id){
        GameFactory.Machine machine = game.getMachine_byId(id);
        return machine.getName();

    }

    public String getRecipe(String name){
        GameFactory.Recipe recipe = game.getRecipe_byName(name);
        return recipe.getName();

    }

    public String getSpoon(int id){
        GameFactory.Spoon spoon = game.getSpoon_byId(id);
        return spoon.getName();

    }


    public Result get_obj_ingredient(Http.Request request) {
        try {
            JsonNode json = request.body().asJson();
            String name = json.get("name").asText();
            GameFactory.Ingredient ingredient = game.getIngredient(name);
            ObjectMapper objectMapper = new ObjectMapper();
            String ingredientJson = objectMapper.writeValueAsString(ingredient);
            return ok(ingredientJson).as("application/json");
        } catch (Exception e) {
            return forbidden("Something went wrong");        }

    }
}
