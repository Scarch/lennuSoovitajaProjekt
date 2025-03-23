package com.srh.ee.lennu_soovitaja;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Lend {
    
    private static int counter = 0;
    private int id;
    private String kust;
    private String kuhu;
    private String kuupaev;
    private int lennuaeg;
    private int hind;
    private String algus;
    private String lopp;
    private List<List<List<Istekoht>>> istekohad;

    public Lend(String kust, String kuhu, String kuupaev, int lennuaeg, int hind) {
        this.id = ++counter;
        this.kust = kust;
        this.kuhu = kuhu;
        this.kuupaev = kuupaev;
        this.lennuaeg = lennuaeg;
        this.hind = hind;
        looJuhuslikudAjad();
        this.istekohad = looIstekohadJuhuslikult(30);
    }

    /**
     * Antud vaid kust, kuhu ja kuupäev, et loome ülejäänud väärtused juhuslikult
     * @param kust 
     * @param kuhu
     * @param kuupaev
     */
    public Lend(String kust, String kuhu, String kuupaev) {
        this.id = ++counter;
        this.kust = kust;
        this.kuhu = kuhu;
        this.kuupaev = kuupaev;
        this.lennuaeg = (int) (Math.random() * 21);
        this.hind = (int) (Math.random() * 2000);
        looJuhuslikudAjad();
        this.istekohad = looIstekohadJuhuslikult(30);
    }
    
    /**
     * Koostatud tehisintellekti abiga
     */
    private void looJuhuslikudAjad() {
        Random random = new Random();
        int hour = random.nextInt(24);
        int minute = random.nextInt(60);
        String minuteStr = minute < 10 ? "0" + minute : String.valueOf(minute);
        this.algus = hour + ":" + minuteStr;
        
        // Calculate end time based on flight duration
        int endHour = hour + (lennuaeg / 60);
        int endMinute = minute + (lennuaeg % 60);
        
        if (endMinute >= 60) {
            endHour += 1;
            endMinute -= 60;
        }
        
        if (endHour >= 24) {
            endHour -= 24;
        }
        
        String endMinuteStr = endMinute < 10 ? "0" + endMinute : String.valueOf(endMinute);
        this.lopp = endHour + ":" + endMinuteStr;
    }

    /**
     * Koostatud tehisintellekti abiga
     */
    public List<List<List<Istekoht>>> looIstekohadJuhuslikult(int ridadeArv) {
        List<List<List<Istekoht>>> istekohad = new ArrayList<>();
        for (int i = 0; i < ridadeArv; i++) {
            List<List<Istekoht>> veerud = new ArrayList<>();
            
            // Vasakpoolne veerg, 3 istekohta
            List<Istekoht> istekohadRida = new ArrayList<>();
            for (int k = 0; k < 3; k++) {
                String label = String.valueOf((char) (65 + k));
                String status = Math.random() < 0.75 ? "available" : "occupied";
                istekohadRida.add(new Istekoht(label, status));
            }
            veerud.add(istekohadRida);

            // Parempoolne veerg, 3 istekohta
            List<Istekoht> paremIstekohadRida = new ArrayList<>();
            for (int k = 0; k < 3; k++) {
                String label = String.valueOf((char) (68 + k));
                String status = Math.random() < 0.75 ? "available" : "occupied";
                paremIstekohadRida.add(new Istekoht(label, status));
            }
            veerud.add(paremIstekohadRida);

            istekohad.add(veerud);
        }
        return istekohad;
    }

    
    
    public int getId() {
        return id;
    }

    public String getKust() {
        return kust;
    }

    public void setKust(String kust) {
        this.kust = kust;
    }

    public String getKuhu() {
        return kuhu;
    }

    public void setKuhu(String kuhu) {
        this.kuhu = kuhu;
    }

    public String getKuupaev() {
        return kuupaev;
    }

    public void setKuupaev(String kuupaev) {
        this.kuupaev = kuupaev;
    }

    public int getLennuaeg() {
        return lennuaeg;
    }

    public void setLennuaeg(int lennuaeg) {
        this.lennuaeg = lennuaeg;
    }

    public int getHind() {
        return hind;
    }

    public void setHind(int hind) {
        this.hind = hind;
    }
    
    public String getAlgus() {
        return algus;
    }
    
    public void setAlgus(String algus) {
        this.algus = algus;
    }
    
    public String getLopp() {
        return lopp;
    }
    
    public void setLopp(String lopp) {
        this.lopp = lopp;
    }
    
    public List<List<List<Istekoht>>> getIstekohad() {
        return istekohad;
    }
    
    public void setIstekohad(List<List<List<Istekoht>>> istekohad) {
        this.istekohad = istekohad;
    }

    public int leiaVabadIstekohad() {
        int vabadKohad = 0;
        for (List<List<Istekoht>> veergud : istekohad) {
            for (List<Istekoht> veerg : veergud) {
                for (Istekoht koht : veerg) {
                    if (koht.getStatus().equals("available")) {
                        vabadKohad++;
                    }
                }
            }
        }
        return vabadKohad;
    }

    // Loodud tehisintellekti abiga
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Lend [id=").append(id)
          .append(", kust=").append(kust)
          .append(", kuhu=").append(kuhu)
          .append(", kuupaev=").append(kuupaev)
          .append(", lennuaeg=").append(lennuaeg)
          .append(", hind=").append(hind)
          .append(", algus=").append(algus)
          .append(", lopp=").append(lopp)
          .append("]\n");
        
        sb.append(formatSeatingChart());
        return sb.toString();
    }
    
    // Loodud tehisintellekti abiga
    private String formatSeatingChart() {
        if (istekohad == null || istekohad.isEmpty()) {
            return "Istekohad pole saadaval";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("Istekohad:\n");
        
        for (int i = 0; i < istekohad.size(); i++) {
            sb.append("  Rida ").append(i + 1).append(": ");
            List<List<Istekoht>> veergud = istekohad.get(i);
            
            // Left column (veerg 1)
            List<Istekoht> vasakVeerg = veergud.get(0);
            for (Istekoht koht : vasakVeerg) {
                String symbol = koht.getStatus().equals("available") ? "□" : "■";
                sb.append(koht.getLabel()).append(symbol).append(" ");
            }
            
            // Add a separator between columns
            sb.append(" | ");
            
            // Right column (veerg 2)
            List<Istekoht> paremVeerg = veergud.get(1);
            for (Istekoht koht : paremVeerg) {
                String symbol = koht.getStatus().equals("available") ? "□" : "■";
                sb.append(koht.getLabel()).append(symbol).append(" ");
            }
            
            sb.append("\n");
        }
        
        return sb.toString();
    }

    public static void main(String[] args) {
        Lend lend = new Lend("Tallinn", "Helsingi", "2021-12-24", 12, 100);
        System.out.println(lend);
    }
    
}
