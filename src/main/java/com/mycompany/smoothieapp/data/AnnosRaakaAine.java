/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.smoothieapp.data;

/**
 *
 * @author sofvanh
 */
public class AnnosRaakaAine {
    
    private int raakaAineId;
    private int annosId;
    private int jarjestys;
    private int maara;
    private String ohje;
    
    public AnnosRaakaAine(int raakaAineId, int annosId, int jarjestys, int maara, String ohje) {
        this.raakaAineId = raakaAineId;
        this.annosId = annosId;
        this.jarjestys = jarjestys;
        this.maara = maara;
        this.ohje = ohje;
    }
    
    public int getRaakaAineId() {
        return raakaAineId;
    }
    
    public void setRaakaAineId(int id) {
        raakaAineId = id;
    }
    
    public int getAnnosId() {
        return annosId;
    }
    
    public void setAnnosId(int id) {
        annosId = id;
    }
    
    public int getJarjestys() {
        return jarjestys;
    }
    
    public void setJarjestys(int jarjestys) {
        this.jarjestys = jarjestys;
    }
    
    public int getMaara() {
        return maara;
    }
    
    public void setMaara(int maara) {
        this.maara = maara;
    }
    
    public String getOhje() {
        return ohje;
    }
    
    public void setOhje(String ohje) {
        this.ohje = ohje;
    }
    
}
