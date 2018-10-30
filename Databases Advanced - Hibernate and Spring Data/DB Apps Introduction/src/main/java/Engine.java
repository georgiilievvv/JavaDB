import com.mysql.cj.xdevapi.Collection;

import java.sql.*;
import java.util.*;

public class Engine implements Runnable,Writer {

    private Scanner sc;
    private Connection connection;

    private String query;

    Engine(Connection connection, Scanner sc) {
        this.connection = connection;
        this.sc = sc;
    }

    public void run() {
        try {
            PrintAllMinionNames();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     1.Get Villains’ Names
     */

    private void GetVillainsNames() throws SQLException {

        System.out.print("Minimum minions per Villain: ");
        int count = Integer.parseInt(sc.nextLine());
        this.setQuery("SELECT name ,COUNT(v.minion_id) AS minions FROM villains JOIN minions_villains v ON villains.id = v.villain_id GROUP BY name HAVING minions > ? ORDER BY minions DESC");
        PreparedStatement pstmt = connection.prepareStatement(this.getQuery());

        pstmt.setInt(1,count);

        ResultSet result = pstmt.executeQuery();

        while (result.next()){
            write(String.format("%s %d",
                    result.getString(1),
                    result.getInt(2)));
        }
    }

    /*
     2.Get Minion Names
     */

    private void GetMinionNames() throws SQLException {

        System.out.print("Villain id: ");
        int input = Integer.parseInt(sc.nextLine());
        this.setQuery("SELECT v.name, m.name, m.age FROM minions_villains mv JOIN minions m ON mv.minion_id = m.id JOIN villains v ON mv.villain_id = v.id WHERE mv.villain_id = ?");

        PreparedStatement pstmt = connection
                .prepareStatement(this.getQuery());

        pstmt.setInt(1, input);

        ResultSet result = pstmt.executeQuery();

        if (result.first()){

            write("Villain: " + result.getString(1));
            int counter = 1;

            while (result.next()){
                write(counter + ". " +
                        result.getString(2) + " " +
                        result.getInt(3));
                counter++;
            }

        }else {
            write("No villain with ID 10 exists in the database.");
        }
    }

    /*
    3. Add Minion
     */

    public void AddMinion() throws SQLException {

        String[] minion = sc.nextLine().split("\\s+");
        String[] villain = sc.nextLine().split("\\s+");


        String minionName = minion[1];
        int minionAge = Integer.parseInt(minion[2]);
        String minionTown = minion[3];
        String villainName = villain[1];

        if (this.checkIfEntityExists(minionTown, "towns")){
            insertTown(minionTown);
            write(minionTown + " was added to the database.");
        }


        if (this.checkIfEntityExists(villainName, "villains")){
            insertVillain(villainName);
            write(villainName + " was added to the database.");
        }

        createMinion(minionName,minionAge,getId(minionTown,"towns"));
        minionVillainConnection(
                this.getId(minionName,"minions"),
                this.getId(villainName, "villains"));
        write(String.format("Successfully added %s to be minion of %s",minionName,villainName));

    }

    private void createMinion(String minionName, int minionAge, int town_id) throws SQLException {
        this.setQuery(String.format
                ("INSERT INTO minions(name, age, town_id) VALUES ('%s', %d, %d)"
                        ,minionName,minionAge,town_id));

        PreparedStatement preparedStatement = this.connection
                .prepareStatement(this.getQuery());

        preparedStatement.execute();
    }

    private int getId(String name,String tableName) throws SQLException {
        this.setQuery("SELECT id FROM " + tableName  + " WHERE name = '" + name + "'");
        PreparedStatement preparedStatement = this.connection
                .prepareStatement(this.getQuery());

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()){
            return resultSet.getInt(1);
        }
        else return 0;
    }

    private void minionVillainConnection(int minion_id, int villain_id) throws SQLException {
        this.setQuery(String.format("INSERT INTO minions_villains" +
                "(minion_id, villain_id) VALUES " +
                "(%d, %d)", minion_id, villain_id));

        PreparedStatement preparedStatement = this.connection
                .prepareStatement(this.getQuery());

        preparedStatement.execute();
    }

    private void insertTown(String name) throws SQLException {
        this.setQuery(String.format("INSERT INTO %s (name) VALUES('%s')","towns",name));
        PreparedStatement preparedStatement = this.connection
                .prepareStatement(this.getQuery());

        preparedStatement.execute();
    }

    private void insertVillain(String name) throws SQLException {
        this.setQuery(String.format("INSERT INTO %s (name, evilness_factor) VALUES('%s', 'evil')","villains",name));
        PreparedStatement preparedStatement = this.connection
                .prepareStatement(this.getQuery());

        preparedStatement.execute();


    }

    private boolean checkIfEntityExists(String name,String tableName) throws SQLException {
        this.setQuery(String.format("SELECT name FROM %s WHERE name = '%s'",tableName,name));
        PreparedStatement preparedStatement = this.connection
                .prepareStatement(this.getQuery());

        ResultSet resultSet = preparedStatement.executeQuery();

        return !resultSet.next();
    }

    /*
     4. Change Town Names Casing
     */

    public void ChangeTownNamesCasing() throws SQLException {

        String countryName = sc.nextLine();
        setQuery("SELECT name, country FROM towns WHERE country = ?");
        PreparedStatement pstmt = connection.prepareStatement(getQuery());

        pstmt.setString(1, countryName);

        ResultSet result = pstmt.executeQuery();

        if (result.next()){

            List<String> townsAffected = new ArrayList<String>();

            do{

                String townName = result.getString(1).toUpperCase();
                updateTown(townName, countryName);

                townsAffected.add(townName);
            }while(result.next());

            write(townsAffected.size() + " town names were affected. \n" + townsAffected);

        }else {
            write("No town names were affected.");
        }
    }

    private void updateTown(String town,String country) throws SQLException {
        this.setQuery("UPDATE towns SET name = ? WHERE country = ?");
        PreparedStatement preparedStatement = this.connection
                .prepareStatement(query);
        preparedStatement.setString(1,town);
        preparedStatement.setString(2,country);

        preparedStatement.execute();
    }

    /*
         6. Print All Minion Names
         */
    public void PrintAllMinionNames() throws SQLException {

        Deque <String> minionNames = new ArrayDeque<>();
        this.setQuery("SELECT name FROM minions");
        PreparedStatement pstmt = connection.prepareStatement(getQuery());

        ResultSet result = pstmt.executeQuery();

        while (result.next()){

            minionNames.add(result.getString(1));
        }

        boolean isOdd = true;

        while (!minionNames.isEmpty()){

            write(isOdd ? minionNames.pollFirst() : minionNames.pollLast());
            isOdd = !isOdd;
        }
    }


    /*
    7. Increase Minions Age
     */

    public void IncreaseMinionsAge() throws SQLException {
        int[] arr = Arrays.stream(sc.nextLine().split(" "))
                .mapToInt(Integer::parseInt).toArray();

        for (Integer currentId : arr) {


            String name = this.updatedName(this.takeName(currentId));

            this.setQuery("UPDATE minions SET name = ?,\n" +
                    "age = age + 1 WHERE id = ?");

            PreparedStatement preparedStatement = this.connection.
                    prepareStatement(this.getQuery());
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, currentId);

            preparedStatement.execute();

        }

        this.setQuery("SELECT name, age FROM minions");

        PreparedStatement preparedStatement = this.connection
                .prepareStatement(this.getQuery());

        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            write(resultSet.getString(1)
                + " " + resultSet.getInt(2));
        }
    }

    private String updatedName(String name) {
        String[] split = name.split("\\s+");

        StringBuilder newName = new StringBuilder();

        for (String s : split) {
            newName.append(s.toUpperCase().charAt(0))
                    .append(s.substring(1))
                    .append(" ");
        }

        return newName.toString();
    }

    private String takeName(int id) throws SQLException {
        this.setQuery("SELECT name FROM minions WHERE id = " + id);
        PreparedStatement preparedStatement = this.connection.
                prepareStatement(this.getQuery());

        ResultSet result = preparedStatement.executeQuery();

        return result.next()? result.getString(1) : "Incorrect minion id";

    }

    /*
    8.Increase Age Stored Procedure
     */

    public void IncreaseAgeStoredProcedure() throws SQLException {

        int minionId = Integer.parseInt(sc.nextLine());
        this.setQuery(String.format("CALL usp_get_older(%d)", minionId));
        PreparedStatement preparedStatement = this.connection
                .prepareStatement(this.getQuery());

        preparedStatement.execute();

        this.setQuery("SELECT name, age FROM minions WHERE id = " + minionId);
        preparedStatement = this.connection.prepareStatement(this.getQuery());

        ResultSet result = preparedStatement.executeQuery();

        if (result.next()) {
            write(String.format("%s %d",
                    result.getString(1)
                    , result.getInt(2)));
        }else write("Incorrect minion id.");
    }

    public void write(String str) {
        System.out.println(str);
    }

    private String getQuery() {
        return query;
    }

    private void setQuery(String query) {
        this.query = query;
    }
}