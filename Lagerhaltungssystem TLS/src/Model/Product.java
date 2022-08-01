package Model;

import java.io.Serializable;
import java.time.LocalDate;

public class Product implements Serializable {

    public static final String STK = "Stück";
    private String artName;
    private String artNumber;
    private final String einheit;
    private double vkPreis, ekPreis;
    private LocalDate[] xPoints;
    private int[] yPoints;
    private int steuersatz, amount, minBestand, melBestand, maxBestand;
    private boolean ordered = false;
    private LocalDate lieferdatum = null;
    private Lagerort lagerort;

    public Product (String artName, String einheit, String artNumber, Lagerort lagerort, int minBestand, int maxBestand, int melBestand, int amount, double ekPreis, double vkPreis, int steuersatz) {
        this.artName = artName;
        this.artNumber = artNumber;
        this.ekPreis = ekPreis;
        this.vkPreis = vkPreis;
        this.steuersatz = steuersatz;
        this.amount = amount;
        this.minBestand = minBestand;
        this.melBestand = melBestand;
        this.maxBestand = maxBestand;
        this.lagerort = lagerort;
        this.einheit = einheit;
    }

    @Override
    public String toString () {
        return this.artName;
    }


    public double getVkPreis() {
        return vkPreis;
    }

    public void setVkPreis(double vkPreis) {
        this.vkPreis = vkPreis;
    }

    public double getEkPreis() {
        return ekPreis;
    }

    public void setEkPreis(double ekPreis) {
        this.ekPreis = ekPreis;
    }

    public int getSteuersatz() {
        return steuersatz;
    }

    public void setSteuersatz(int steuersatz) {
        this.steuersatz = steuersatz;
    }

    public int getAmount() {
        return yPoints[0];
    }

    public void setAmount(int amount) {
        if(this.xPoints[xPoints.length-1].equals(LocalDate.now())) {
            this.yPoints[yPoints.length] = amount;
        } else {

        }
    }

    public int getMinBestand() {
        return minBestand;
    }

    public void setMinBestand(int minBestand) {
        this.minBestand = minBestand;
    }

    public int getMelBestand() {
        return melBestand;
    }

    public void setMelBestand(int melBestand) {
        this.melBestand = melBestand;
    }

    public int getMaxBestand() {
        return maxBestand;
    }

    public void setMaxBestand(int maxBestand) {
        this.maxBestand = maxBestand;
    }

    public String getArtNumber() {
        return artNumber;
    }

    public void setArtNumber(String artNumber) {
        this.artNumber = artNumber;
    }

    public String getArtName() {
        return artName;
    }

    public void setArtName(String artName) {
        this.artName = artName;
    }

    public int[] getyPoints() {
        return yPoints;
    }

    public void setyPoints(int[] yPoints) {
        this.yPoints = yPoints;
    }

    public LocalDate[] getxPoints() {
        return xPoints;
    }

    public void setxPoints(LocalDate[] xPoints) {
        this.xPoints = xPoints;
    }

    public void setDayAmount(int amount) {
        this.amount = amount;

        if (xPoints[xPoints.length-1].compareTo(LocalDate.now()) == 0) {
            yPoints[yPoints.length-1] = amount;
        }
    }

    public boolean isOrdered() {
        return ordered;
    }

    public LocalDate getLieferdatum() {
        return lieferdatum;
    }

    public void setOrder(LocalDate date) {
        this.ordered = true;
        this.lieferdatum = date;
    }

    public String getEinheit() {
        return this.einheit;
    }

    public Lagerort getLagerort() {
        return lagerort;
    }

    public void setLagerort(Lagerort lagerort) {
        this.lagerort = lagerort;
    }
}
