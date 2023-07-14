package models;

import play.db.Database;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class GameFactory {

    private Database db;

    @Inject
    GameFactory(Database db) {
        this.db = db;
    }


    /*public User authenticate(String username, String password) {
        return db.withConnection(conn -> {
            User user = null;
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM User WHERE `name` = ? AND `password` = ?");
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            System.out.println("authenticate" + username + " " + password + " " + rs);
            if (rs.next()) {
                user = new User(rs);
                System.out.println("alles gut");
            }
            stmt.close();
            return user;
        });
    }*/

   /* public User create(String email, String names, String password) {

        return db.withConnection(conn -> {
            String sql_Score = "INSERT INTO Score (`level1`, `level2`, `level3`) VALUES (?, ?, ?)";
            PreparedStatement stmt_Score = conn.prepareStatement(sql_Score, Statement.RETURN_GENERATED_KEYS);
            stmt_Score.setInt(1, 0);
            stmt_Score.setInt(2, 0);
            stmt_Score.setInt(3, 0);
            stmt_Score.executeUpdate();
            ResultSet rs_socre = stmt_Score.getGeneratedKeys();
            int score_id = -1;
            if (rs_socre.next()) {
                score_id = rs_socre.getInt(1);
            }
            System.out.println("Score id  " + score_id);
            stmt_Score.close();
            // TODO hier noch try catch wenn user error wirft sollte der Score wieder gelöscht werden
            User user = null;
            String sql = "INSERT INTO User (`name`, `password`, `mail`, `Score_idScore`) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, names);
            stmt.setString(2, password);
            stmt.setString(3, email);
            stmt.setInt(4, score_id);
            *//*INSERT INTO `sopra-2022WS-team02`.`User` (`name`, `password`, `mail`, `Score_idScore`) VALUES ('admin', 'admin', 'admin@gmail.com', '0');*//*

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                user = new User(id, names, email);
            }
            stmt.close();
            return user;
        });
    }*/


   /* public int create_score() {
        int score_id = 0;
        db.withConnection(conn -> {


        });
        return 0;
    }*/


  /*  public User getUserById(int id) {
        return db.withConnection(conn -> {
            User user = null;
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM User WHERE `idUser`= ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(rs);
            }
            stmt.close();
            return user;
        });
    }
*/
  /*  public User getUserByName(String name) {
        return db.withConnection(conn -> {
            User user = null;
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM User WHERE `name`= ?");
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(rs);
            }
            stmt.close();
            return user;
        });
    }
*/
    public Ingredient getIngredient(String name) {
        return db.withConnection(conn -> {
            Ingredient ingredient = null;
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Ingredient WHERE `name`= ?");
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ingredient = new Ingredient(rs);
            }
            stmt.close();
            return ingredient;
        });
    }

    public Machine getMachine_byId(int id) {
        return db.withConnection(conn -> {
            Machine machine = null;
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Machine WHERE `idMachine`= ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                machine = new Machine(rs);
            }
            stmt.close();
            return machine;
        });
    }

    public Recipe getRecipe_byName(String name) {
        return db.withConnection(conn -> {
            Recipe recipe = null;
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Recipe WHERE `name`= ?");
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                recipe = new Recipe(rs);
            }
            stmt.close();
            return recipe;
        });
    }


    public Spoon getSpoon_byId(int id) {
        return db.withConnection(conn -> {
            Spoon spoon = null;
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Spoon WHERE `idspoon`= ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                spoon = new Spoon(rs);
            }
            stmt.close();
            return spoon;
        });
    }




   /* public User getUserById(String id) {
        return getUserById(Integer.parseInt(id));
    }

    public List<User> getAllUsers() {
        return db.withConnection(conn -> {
            List<User> users = new ArrayList<>();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM User");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = new User(rs);
                users.add(user);
            }
            stmt.close();
            return users;
        });
    }

    public List<Score> getAllScores() {
        return db.withConnection(conn -> {
            List<Score> scores = new ArrayList<>();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Score");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Score user = new Score(rs);
                scores.add(user);
            }
            stmt.close();
            return scores;
        });
    }*/
    public class Ingredient{
        private String name, description, unit;

        public Ingredient(String name, String description, String unit) {
            this.name = name;
            this.description = description;
            this.unit = unit;
        }

        private Ingredient(ResultSet rs) throws SQLException {
            this.name = rs.getString("name");
            this.description = rs.getString("description");
            this.unit = rs.getString("unit");


        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }
    }

    public class Machine{
        private int id;
        private String name, decription;

        public Machine(int id, String name, String decription) {
            this.id = id;
            this.name = name;
            this.decription = decription;
        }

        private Machine(ResultSet rs) throws SQLException {
            this.id = rs.getInt("idMachine");
            this.name = rs.getString("name");
            this.decription = rs.getString("decription");

        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDecription() {
            return decription;
        }

        public void setDecription(String decription) {
            this.decription = decription;
        }
    }

    public class Recipe{
        private String name;
        private int brewtime, cup_idcup, Spoon_idspoon;

        public Recipe(String name, int brewtime, int cup_idcup, int spoon_idspoon) {
            this.name = name;
            this.brewtime = brewtime;
            this.cup_idcup = cup_idcup;
            Spoon_idspoon = spoon_idspoon;
        }

        private Recipe(ResultSet rs) throws SQLException {
            this.name = rs.getString("name");
            this.brewtime = rs.getInt("brewtime");
            this.cup_idcup = rs.getInt("cup_idcup");
            this.Spoon_idspoon = rs.getInt("Spoon_idspoon");

        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getBrewtime() {
            return brewtime;
        }

        public void setBrewtime(int brewtime) {
            this.brewtime = brewtime;
        }

        public int getCup_idcup() {
            return cup_idcup;
        }

        public void setCup_idcup(int cup_idcup) {
            this.cup_idcup = cup_idcup;
        }

        public int getSpoon_idspoon() {
            return Spoon_idspoon;
        }

        public void setSpoon_idspoon(int spoon_idspoon) {
            Spoon_idspoon = spoon_idspoon;
        }
    }

    public class Spoon{
        private int idspoon, size;
        private String name;

        public Spoon(int idspoon, int size, String name) {
            this.idspoon = idspoon;
            this.size = size;
            this.name = name;
        }

        private Spoon(ResultSet rs) throws SQLException {
            this.idspoon = rs.getInt("idspoon");
            this.name = rs.getString("name");
            this.size = rs.getInt("size");

        }

        public int getIdspoon() {
            return idspoon;
        }

        public void setIdspoon(int idspoon) {
            this.idspoon = idspoon;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

/*    public class Score{
        private int id;
        private int level1;
        private int level2;
        private int level3;

        private int highscore;

        private Score(int id, int level1, int level2, int level3) {
            this.id = id;
            this.level1 = level1;
            this.level2 = level2;
            this.level3 = level3;
            this.highscore = level1 + level2 + level3;
            *//*this.points = points;*//*
        }

        private Score(ResultSet rs) throws SQLException {
            this.id = rs.getInt("idScore");
            this.level1 = rs.getInt("level1");
            this.level2 = rs.getInt("level2");
            this.level3 = rs.getInt("level3");
            this.highscore = this.level1 + this.level2 + this.level3;
            *//* this.points = rs.getInt("Points");*//*
        }

        public int get_id(){
            return id;
        }

        public int getHighscore(){
            return highscore;
        }

    }*/

    /*public class User {
        private int id;
        private String username;
        private String mail;

        private int score_id;
        *//* private int points;*//*

        private User(int id, String username, String mail) {
            this.id = id;
            this.username = username;
            this.mail = mail;
            *//*this.points = points;*//*
        }

        private User(ResultSet rs) throws SQLException {
            this.id = rs.getInt("idUser");
            this.username = rs.getString("name");
            this.mail = rs.getString("mail");
            this.score_id = rs.getInt("Score_idScore");
            *//* this.points = rs.getInt("Points");*//*
        }

        *//**
         * Updates the user if it already exists and creates it otherwise. Assumes an
         * autoincrement id column.
         *//*
        public void save() {
            db.withConnection(conn -> {
                String sql = "UPDATE User SET Username = ?, Points = ?, Email = ? WHERE UserId = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, this.username);
                *//*      stmt.setInt(2, this.points);*//*
                stmt.setString(3, this.mail);
                stmt.setInt(4, this.id);
                stmt.executeUpdate();
                stmt.close();
            });
        }

        *//**
         * Delete the user from the database
         *//*
        public void delete() {
            db.withConnection(conn -> {
                String sql = "DELETE FROM User WHERE UserId = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, this.id);
                stmt.executeUpdate();
                stmt.close();
            });
        }

        public List<User> getFriends() {
            return db.withConnection(conn -> {
                List<User> result = new ArrayList<>();
                String sql = "SELECT User_idUser1 FROM User_has_Friends WHERE User_idUser = ?;";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, this.id);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt(1);
                    User user = getUserById(id);
                    result.add(user);
                }

                String sql2 = "SELECT User_idUser FROM User_has_Friends WHERE User_idUser1 = ?;";
                stmt = conn.prepareStatement(sql2);
                stmt.setInt(1, this.id);
                ResultSet rs2 = stmt.executeQuery();
                while (rs2.next()) {
                    int id = rs2.getInt(1);
                    User user = getUserById(id);
                    result.add(user);
                }
                stmt.close();
                return result;
            });
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getScore_id(){
            return score_id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
            this.save();
        }

        public String getMail() {
            return mail;
        }

        public void setMail(String mail) {
            this.mail = mail;
        }

        *//*public int getPoints() {
            return points;
        }

        public void setPoints(int points) {
            this.points = points;
        }

        public void addPoints(int points) {
            this.points += points;
            this.save();
        }*//*
    }*/

}

