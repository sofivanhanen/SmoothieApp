/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.smoothieapp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

/**
 *
 * @author sofvanh
 */
public class SmoothieApp {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Database db = new Database("jdbc:sqlite:smoothiedatabase.db");
        Connection conn = db.getConnection();
        
        // Get index page
        Spark.get("/", (res, req) -> {
            
            
            HashMap map = new HashMap<>();
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
        
        // Get raaka-aineet page
        Spark.get("/raakaaineet", (res, req) -> {
            
            HashMap map = new HashMap<>();
            return new ModelAndView(map, "raakaaineet");
        }, new ThymeleafTemplateEngine());
    }

}
