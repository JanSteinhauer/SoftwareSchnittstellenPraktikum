package models;

import play.db.Database;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class UserFactory {

    private Database db;

    @Inject
    UserFactory(Database db) {
        this.db = db;
    }

    /**
     * Authenticates a user with the given credentials
     *
     * @param username username from user input
     * @param password password from user input
     * @return Found user or null if user not found
     */
    public User authenticate(String username, String password) {
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
    }

    public User create(String email, String names, String password) {

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
            /*INSERT INTO `sopra-2022WS-team02`.`User` (`name`, `password`, `mail`, `Score_idScore`) VALUES ('admin', 'admin', 'admin@gmail.com', '0');*/

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                user = new User(id, names, email);
            }
            stmt.close();
            return user;
        });
    }


    public int create_score() {
        int score_id = 0;
        db.withConnection(conn -> {


        });
        return 0;
    }

    /**
     * Retrieves a user from database with given ID
     *
     * @param id id of user to find
     * @return User if found, else null
     */
    public User getUserById(int id) {
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

    public User getUserByName(String name) {
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

    public User getUserByEmail(String email) {
        return db.withConnection(conn -> {
            User user = null;
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM User WHERE `mail`= ?");
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(rs);
            }
            stmt.close();
            return user;
        });
    }



    /**
     * Polymorphism method for getUserById(int)
     *
     * @param id String of id
     * @return User if found, else null
     */
    public User getUserById(String id) {
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
    }

    public class Score{
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
            /*this.points = points;*/
        }

        private Score(ResultSet rs) throws SQLException {
            this.id = rs.getInt("idScore");
            this.level1 = rs.getInt("level1");
            this.level2 = rs.getInt("level2");
            this.level3 = rs.getInt("level3");
            this.highscore = this.level1 + this.level2 + this.level3;
            /* this.points = rs.getInt("Points");*/
        }

        public int get_id(){
            return id;
        }

        public int getHighscore(){
            return highscore;
        }

    }

    public class User {
        private int id;
        private String username;
        private String mail;

        private int score_id;
       /* private int points;*/

        private User(int id, String username, String mail) {
            this.id = id;
            this.username = username;
            this.mail = mail;
            /*this.points = points;*/
        }

        private User(ResultSet rs) throws SQLException {
            this.id = rs.getInt("idUser");
            this.username = rs.getString("name");
            this.mail = rs.getString("mail");
            this.score_id = rs.getInt("Score_idScore");
           /* this.points = rs.getInt("Points");*/
        }

        /**
         * Updates the user if it already exists and creates it otherwise. Assumes an
         * autoincrement id column.
         */
        public void save() {
            db.withConnection(conn -> {
                String sql = "UPDATE User SET Username = ?, Points = ?, Email = ? WHERE UserId = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, this.username);
                /*      stmt.setInt(2, this.points);*/
                stmt.setString(3, this.mail);
                stmt.setInt(4, this.id);
                stmt.executeUpdate();
                stmt.close();
            });
        }

        /**
         * Delete the user from the database
         */
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

        /*public int getPoints() {
            return points;
        }

        public void setPoints(int points) {
            this.points = points;
        }

        public void addPoints(int points) {
            this.points += points;
            this.save();
        }*/
    }

}
