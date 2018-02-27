/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.smoothieapp.dao;

import com.mycompany.smoothieapp.Database;
import com.mycompany.smoothieapp.data.Annos;
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
        PreparedStatement stmt = conn.prepareStatement("SELECT RaakaAine.nimi AS raakaAine, COUNT(*) AS lkm "
                + "FROM RaakaAine, AnnosRaakaAine "
                + "WHERE RaakaAine.id = AnnosRaakaAine.raaka_aine_id "
                + "GROUP BY RaakaAine.nimi "
                + "ORDER BY lkm DESC "
                + "LIMIT 1;");
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

        return new RaakaAine(-1, "Ei mitään");
    }

    public Annos getAnnosWithMostRaakaAine() throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT Annos.nimi AS annos, COUNT(*) AS lkm "
                + "FROM Annos, AnnosRaakaAine "
                + "WHERE Annos.id = AnnosRaakaAine.annos_id "
                + "GROUP BY Annos.nimi "
                + "ORDER BY lkm DESC "
                + "LIMIT 1;");
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            AnnosDao ADao = new AnnosDao(database);

            Annos suurin = ADao.findByName(rs.getString("annos"));

            rs.close();
            stmt.close();
            conn.close();

            return suurin;
        }

        rs.close();
        stmt.close();
        conn.close();

        return new Annos(-1, "Ei mitään");
    }

    public int numberOfRaakaAine() throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) AS lkm FROM RaakaAine;");
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {

            int arvo = rs.getInt("lkm");

            rs.close();
            stmt.close();
            conn.close();

            return arvo;
        }

        rs.close();
        stmt.close();
        conn.close();

        return -1;
    }

    public int numberOfAnnos() throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) AS lkm FROM Annos;");
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {

            int arvo = rs.getInt("lkm");

            rs.close();
            stmt.close();
            conn.close();

            return arvo;
        }

        rs.close();
        stmt.close();
        conn.close();

        return -1;
    }

}
