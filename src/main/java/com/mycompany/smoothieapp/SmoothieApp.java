/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.smoothieapp;

import com.mycompany.smoothieapp.dao.*;
import com.mycompany.smoothieapp.data.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import spark.Spark;
import static spark.Spark.staticFiles;
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
        Spark.get("/", (req, res) -> {
            AnnosDao smoothiedao = new AnnosDao(db);

            HashMap map = new HashMap<>();
            map.put("smoothiet", smoothiedao.findAll());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        // Get smoothie-specific page
        Spark.get("/smoothiet/:id", (req, res) -> {
            RaakaAineDao RADao = new RaakaAineDao(db);
            AnnosDao smoothiedao = new AnnosDao(db);
            AnnosRaakaAineDao reseptidao = new AnnosRaakaAineDao(db);

            HashMap map = new HashMap<>();
            // Find the correct smoothie
            Annos smoothie = smoothiedao.findOne(Integer.parseInt(req.params(":id")));
            // Fetch and sort all AnnosRaakaAine connections associated with the smoothie
            List<AnnosRaakaAine> resepti = reseptidao.findByAnnos(smoothie.getId());
            Collections.sort(resepti, (a, b) -> a.getJarjestys() < b.getJarjestys() ? -1 : a.getJarjestys() == b.getJarjestys() ? 0 : 1);
            //
            List<RaakaAine> raakaAineet = new ArrayList<>();
            resepti.stream().forEach(ara -> {
                try {
                ara.setRaakaAine(RADao.findOne(ara.getRaakaAineId()));
                } catch (Exception e) {
                    
                }
            });
            
            map.put("raakaAineet", raakaAineet);
            map.put("smoothie", smoothie);
            map.put("resepti", resepti);
            
            return new ModelAndView(map, "recipe");
        }, new ThymeleafTemplateEngine());

        // Get smoothiet page
        Spark.get("/smoothiet", (req, res) -> {
            AnnosDao smoothiedao = new AnnosDao(db);
            RaakaAineDao RADao = new RaakaAineDao(db);

            HashMap map = new HashMap<>();
            map.put("smoothiet", smoothiedao.findAll());
            map.put("raakaAineet", RADao.findAll());

            return new ModelAndView(map, "smoothiet");
        }, new ThymeleafTemplateEngine());

        // Post add new smoothie
        Spark.post("/smoothiet/uusi", (req, res) -> {
            AnnosDao smoothiedao = new AnnosDao(db);
            Annos uusi = new Annos(0, req.queryParams("smoothie"));
            smoothiedao.saveOrUpdate(uusi);

            res.redirect("/smoothiet");
            return 0;
        });

        // Post delete smoothie recipe
        Spark.post("/smoothiet/:id/poista", (req, res) -> {
            AnnosDao smoothiedao = new AnnosDao(db);
            AnnosRaakaAineDao reseptidao = new AnnosRaakaAineDao(db);
            
            smoothiedao.delete(Integer.parseInt(req.params(":id")));
            reseptidao.deleteByAnnos(Integer.parseInt(req.params(":id")));

            res.redirect("/smoothiet");
            return 0;
        });
        
        // Post add raaka-aine to recipe
        Spark.post("/smoothiet/muokkaa", (req,res) -> {
            AnnosRaakaAineDao reseptidao = new AnnosRaakaAineDao(db);
            
            
            AnnosRaakaAine uusi = new AnnosRaakaAine(
                    Integer.parseInt(req.queryParams("lisattavaRaakaAine")), 
                    Integer.parseInt(req.queryParams("muokattavaSmoothie")),
                    Integer.parseInt(req.queryParams("lisattavanJarjestys")),
                    Integer.parseInt(req.queryParams("lisattavanMaara")),
                    req.queryParams("lisattavanOhje")
            );
            
            
            reseptidao.saveOrUpdate(uusi);
            
            res.redirect("/smoothiet");
            return 0;
        });

        // Get raaka-aineet page
        Spark.get("/raakaaineet", (req, res) -> {
            RaakaAineDao RADao = new RaakaAineDao(db);

            HashMap map = new HashMap<>();
            map.put("lista", RADao.findAll());

            return new ModelAndView(map, "raakaaineet");
        }, new ThymeleafTemplateEngine());

        // Post add new raaka-aine
        Spark.post("/raakaaineet/uusi", (req, res) -> {
            RaakaAineDao RADao = new RaakaAineDao(db);
            RaakaAine uusi = new RaakaAine(0, req.queryParams("raakaA"));
            RADao.saveOrUpdate(uusi);

            res.redirect("/raakaaineet");
            return 0;
        });

        // Post delete raaka-aine
        Spark.post("raakaaineet/:id/poista", (req, res) -> {
            RaakaAineDao RADao = new RaakaAineDao(db);
            AnnosRaakaAineDao reseptidao = new AnnosRaakaAineDao(db);
            
            RADao.delete(Integer.parseInt(req.params(":id")));
            reseptidao.deleteByRaakaAine(Integer.parseInt(req.params(":id")));
            
            res.redirect("/raakaaineet");
            return 0;
        });
    }
}
