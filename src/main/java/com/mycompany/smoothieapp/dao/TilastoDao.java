/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.smoothieapp.dao;

import com.mycompany.smoothieapp.Database;
import com.mycompany.smoothieapp.data.RaakaAine;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author haaotso
 */
public class TilastoDao {
    
    private Database database;
    
    public TilastoDao(Database database) {
        this.database = database;
    }
    
    public RaakaAine getMostUsed() throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement
        ("SELECT RaakaAine.nimi AS raakaAine, COUNT(*) AS lkm "
                + "FROM RaakaAine, AnnosRaakaAine "
                + "WHERE RaakaAine.id = AnnosRaakaAine.raaka_aine_id "
                + "GROUP BY RaakaAine.nimi "
                + "ORDER BY lkm DESC;");
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            RaakaAineDao RADao = new RaakaAineDao(database);
            
            RaakaAine suosituin = RADao.findByName(rs.getString("raakaAine"));
            
            rs.close();
            stmt.close();
            conn.close();
            
            return suosituin;
        }
        
        rs.close();
        stmt.close();
        conn.close();
        
        return null;
    }
    
}
