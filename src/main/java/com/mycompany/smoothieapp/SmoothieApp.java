/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.smoothieapp;

import java.sql.Connection;
import java.sql.DriverManager;
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

    public static void main(String[] args) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:smoothiedatabase.db");

        Spark.get("/", (res, req) -> {
            
            
            HashMap map = new HashMap<>();
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
    }

}
