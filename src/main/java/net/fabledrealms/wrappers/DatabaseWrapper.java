package net.fabledrealms.wrappers;

import net.fabledrealms.Core;
import net.fabledrealms.util.LogUtil;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseWrapper {


    private final Connection connection;


    public DatabaseWrapper(Core main, String database_id){
        String host = main.getConfigFile().getFile().getString("settings.databases." + database_id + ".host");
        String port = main.getConfigFile().getFile().getString("settings.databases." + database_id + ".port");
        String database = main.getConfigFile().getFile().getString("settings.databases." + database_id + ".database");
        String username = main.getConfigFile().getFile().getString("settings.databases." + database_id + ".username");
        String password = main.getConfigFile().getFile().getString("settings.databases." + database_id + ".password");
        String url = "jdbc:mysql://" + host + ":" + port + "/" + database;
        LogUtil.sendLog(url);
        Connection tryConnection;
        try {
            tryConnection = DriverManager.getConnection(url, username, password);
            Bukkit.getLogger().info("Connection to Database:" + database_id + " has been established successfully!");
        } catch (SQLException exception){
            exception.printStackTrace();
            tryConnection = null;
            Bukkit.getLogger().warning("Connection to Database:" + database_id + " has failed!");
        }
        this.connection = tryConnection;
    }

    public void initialization() throws SQLException {
        Statement statement = this.connection.createStatement();
        statement.execute
                ("CREATE TABLE IF NOT EXISTS players (uuid VARCHAR(36) primary key, characterID int," +
                        "className VARCHAR(36), levelMain int, levelWoodcutting int, levelFishing int, levelMining int, levelSmithing int," +
                        "levelCrafting int, levelGathering int, expMain int, expWoodcutting int, expFishing int, expMining int, " +
                        "expSmithing int, expCrafting int, expGathering int, balance int)");
        statement.close();
    }

    private Statement getStatement(){
        Statement returnedStatement;
        try {
           returnedStatement = connection.createStatement();
        } catch (SQLException exception){
            returnedStatement = null;
            exception.printStackTrace();
        }
        return  returnedStatement;
    }

    public void execute(String sqlQuery){
        try {
            getStatement().execute(sqlQuery);
        } catch (SQLException exception){
            exception.printStackTrace();
        }
    }

    public Object executeQuery(String sqlQuery){
        Object returned;
        try {
           returned = getStatement().execute(sqlQuery);
        } catch (SQLException exception){
            exception.printStackTrace();
            returned = null;
        }
        return returned;
    }

    public void disconnect(){
        try {
            connection.close();
        } catch (SQLException exception){
            exception.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
