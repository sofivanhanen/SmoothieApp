/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.smoothieapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sofvanh
 */
public class AnnosRaakaAineDao {
    
    private Database database;
    
    // Table name
    private static final String ANNOS_RAAKA_AINE = "AnnosRaakaAine";
    
    // Column names
    private static final String RAAKA_AINE_ID = "raaka_aine_id";
    private static final String ANNOS_ID = "annos_id";
    private static final String JARJESTYS = "jarjestys";
    private static final String MAARA = "maara";
    private static final String OHJE = "ohje";
    
    public AnnosRaakaAineDao(Database database) {
        this.database = database;
    }
    
    public List<AnnosRaakaAine> findByAnnos(Integer annosId) throws SQLException {
        List<AnnosRaakaAine> list = new ArrayList<>();
        
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " 
                    + ANNOS_RAAKA_AINE + " WHERE " + ANNOS_ID + " = ?");
            stmt.setInt(1, annosId);

            ResultSet result = stmt.executeQuery();
            
            while (result.next()) {
                list.add(new AnnosRaakaAine(result.getInt(RAAKA_AINE_ID), 
                        result.getInt(ANNOS_ID), 
                        result.getInt(JARJESTYS), 
                        result.getInt(MAARA),
                        result.getString(OHJE)));
            }

            return list;
        }
    }
    
    public List<AnnosRaakaAine> findAll() throws SQLException {
        List<AnnosRaakaAine> list = new ArrayList<>();

        try (Connection conn = database.getConnection();
            ResultSet result = conn.prepareStatement("SELECT * FROM " + ANNOS_RAAKA_AINE).executeQuery()) {

            while (result.next()) {
                list.add(new AnnosRaakaAine(result.getInt(RAAKA_AINE_ID), 
                        result.getInt(ANNOS_ID), 
                        result.getInt(JARJESTYS), 
                        result.getInt(MAARA),
                        result.getString(OHJE)));
            }
        }

        return list;
    }
    
    public AnnosRaakaAine saveOrUpdate(AnnosRaakaAine annosRaakaAine) throws SQLException {
        // Note: No update functionality here
        for (AnnosRaakaAine a : findByAnnos(annosRaakaAine.getAnnosId())) {
            if (a.getRaakaAineId() == annosRaakaAine.getRaakaAineId()) return a;
        }

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO "
                    + ANNOS_RAAKA_AINE + " (" 
                    + RAAKA_AINE_ID + ", "
                    + ANNOS_ID + ", "
                    + JARJESTYS + ", "
                    + MAARA + ", "
                    + OHJE + ") VALUES (?, ?, ?, ?, ?)");
            stmt.setInt(1, annosRaakaAine.getRaakaAineId());
            stmt.setInt(2, annosRaakaAine.getAnnosId());
            stmt.setInt(3, annosRaakaAine.getJarjestys());
            stmt.setInt(4, annosRaakaAine.getMaara());
            stmt.setString(5, annosRaakaAine.getOhje());
            stmt.executeUpdate();
        }

        return annosRaakaAine;
    }
    
    public void deleteByAnnos(Integer annosId) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM "
                    + ANNOS_RAAKA_AINE + " WHERE "
                    + ANNOS_ID + " = ?");
            stmt.setInt(1, annosId);
            stmt.executeUpdate();
        }
    }
    
}
