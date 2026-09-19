package ch.wiss.m335.einkaufsliste.data;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * Die Datenbank der App.
 *
 * Es gibt genau eine Datenbank pro App. Darum das Feld instance und die
 * Methode getInstance.
 */
@Database(entities = {ShoppingItem.class}, version = 1, exportSchema = false)
public abstract class ShoppingDatabase extends RoomDatabase {

    /** Log-Tag für das ganze Modul 335. */
    private static final String TAG = "Modul335";

    /** Der Dateiname der Datenbank auf dem Gerät. */
    private static final String DB_NAME = "shopping.db";

    /** Die eine Datenbank. Wird beim ersten Zugriff gebaut. */
    private static ShoppingDatabase instance;

    /**
     * Der Zugriff auf die Tabelle shopping_items.
     *
     * @return das Dao, über das alle Abfragen laufen
     */
    public abstract ShoppingItemDao shoppingItemDao();

    /**
     * Liefert die Datenbank und baut sie beim ersten Aufruf.
     *
     * @param context irgendein Context, zum Beispiel eine Activity
     * @return die eine Datenbank der App
     */
    public static synchronized ShoppingDatabase getInstance(Context context) {
        if (instance == null) {
            Context appContext = context.getApplicationContext();
            instance = Room.databaseBuilder(appContext, ShoppingDatabase.class, DB_NAME)
                    .allowMainThreadQueries()
                    .build();
            seedIfEmpty();
            Log.d(TAG, "database opened, name=" + DB_NAME
                    + ", items=" + instance.shoppingItemDao().count());
        }
        return instance;
    }

    /**
     * Füllt die Tabelle beim allerersten Start mit fünf Beispieleinträgen.
     *
     * Die Bedingung count() > 0 ist der Kern dieser Methode. Ohne sie kämen
     * bei jedem Start fünf Einträge dazu.
     */
    private static void seedIfEmpty() {
        ShoppingItemDao dao = instance.shoppingItemDao();
        int existing = dao.count();
        if (existing > 0) {
            Log.d(TAG, "seed skipped, count=" + existing);
            return;
        }

        dao.insert(newItem("Vollkornbrot", "Migros", 2, 3.80));
        dao.insert(newItem("Milch", "Migros", 6, 1.65));
        dao.insert(newItem("Kaffeebohnen", "Coop", 1, 12.90));
        dao.insert(newItem("Zahnpasta", "Coop", null, null));
        dao.insert(newItem("Batterien AA", "Interdiscount", null, null));

        Log.d(TAG, "seed done, count=" + dao.count());
    }

    /**
     * Baut einen Eintrag aus vier Werten.
     *
     * @param article der Artikel
     * @param shop der Laden
     * @param quantity die Menge, oder null
     * @param pricePerUnit der Preis pro Stück, oder null
     * @return der fertige Eintrag, noch ohne id
     */
    private static ShoppingItem newItem(String article, String shop,
                                        Integer quantity, Double pricePerUnit) {
        ShoppingItem item = new ShoppingItem();
        item.setArticle(article);
        item.setShop(shop);
        item.setQuantity(quantity);
        item.setPricePerUnit(pricePerUnit);
        return item;
    }
}