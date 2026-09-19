package ch.wiss.m335.einkaufsliste.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Der Zugriff auf die Tabelle shopping_items.
 *
 * Ein Dao ist der Vertrag mit einer Tabelle: anlegen, ändern, löschen,
 * eine holen, alle holen, zählen.
 */
@Dao
public interface ShoppingItemDao {

    /**
     * Legt einen neuen Eintrag an.
     *
     * @param item der neue Eintrag
     * @return die id, die Room vergeben hat
     */
    @Insert
    long insert(ShoppingItem item);

    /**
     * Speichert Änderungen an einem bestehenden Eintrag.
     *
     * @param item der geänderte Eintrag, inklusive seiner id
     */
    @Update
    void update(ShoppingItem item);

    /**
     * Löscht einen Eintrag.
     *
     * @param item der zu löschende Eintrag
     */
    @Delete
    void delete(ShoppingItem item);

    /**
     * Liefert alle Einträge, nach Laden und dann nach Artikel sortiert.
     *
     * @return alle Einträge, nie null
     */
    @Query("SELECT * FROM shopping_items ORDER BY shop, article")
    List<ShoppingItem> getAll();

    /**
     * Liefert einen einzelnen Eintrag.
     *
     * @param id die Nummer des Eintrags
     * @return der Eintrag, oder null wenn es ihn nicht gibt
     */
    @Query("SELECT * FROM shopping_items WHERE id = :id")
    ShoppingItem findById(int id);

    /**
     * Zählt alle Einträge.
     *
     * @return Anzahl Einträge in der Tabelle
     */
    @Query("SELECT COUNT(*) FROM shopping_items")
    int count();
}