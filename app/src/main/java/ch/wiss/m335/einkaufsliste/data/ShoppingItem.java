package ch.wiss.m335.einkaufsliste.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Ein Eintrag der Einkaufsliste.
 *
 * Enthält Artikel, Laden, Menge und Preis pro Stück. Der Betrag wird
 * bewusst nicht gespeichert, da er sich jederzeit aus Menge mal Preis
 * berechnen lässt.
 */
@Entity(tableName = "shopping_items")
public class ShoppingItem {

    /** Die Nummer des Eintrags, von Room automatisch vergeben. */
    @PrimaryKey(autoGenerate = true)
    private int id;

    /** Der Artikel, Pflichtfeld, mindestens 4 Zeichen. */
    private String article;

    /** Der Laden, Pflichtfeld, mindestens 4 Zeichen. */
    private String shop;

    /** Die Menge, darf null sein, solange sie nicht eingetragen ist. */
    private Integer quantity;

    /** Der Preis pro Stück, darf null sein, solange er nicht eingetragen ist. */
    @ColumnInfo(name = "price_per_unit")
    private Double pricePerUnit;

    /** @return die Nummer des Eintrags */
    public int getId() { return id; }

    /** @param id die Nummer des Eintrags */
    public void setId(int id) { this.id = id; }

    /** @return der Artikel */
    public String getArticle() { return article; }

    /** @param article der Artikel */
    public void setArticle(String article) { this.article = article; }

    /** @return der Laden */
    public String getShop() { return shop; }

    /** @param shop der Laden */
    public void setShop(String shop) { this.shop = shop; }

    /** @return die Menge, oder null wenn sie nicht eingetragen ist */
    public Integer getQuantity() { return quantity; }

    /** @param quantity die Menge, oder null */
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    /** @return der Preis pro Stück, oder null wenn er nicht eingetragen ist */
    public Double getPricePerUnit() { return pricePerUnit; }

    /** @param pricePerUnit der Preis pro Stück, oder null */
    public void setPricePerUnit(Double pricePerUnit) { this.pricePerUnit = pricePerUnit; }
}