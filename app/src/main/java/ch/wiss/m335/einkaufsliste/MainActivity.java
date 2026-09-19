package ch.wiss.m335.einkaufsliste;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import ch.wiss.m335.einkaufsliste.data.ShoppingDatabase;
import ch.wiss.m335.einkaufsliste.data.ShoppingItem;

/**
 * Die Startseite. Zeigt alle Einträge der Einkaufsliste.
 */
public class MainActivity extends AppCompatActivity {

    /** Log-Tag für das ganze Modul 335. */
    private static final String TAG = "Modul335";


    public static final String EXTRA_ITEM_ID = "extra_item_id";
    /** Der Behälter für die Zeilen. Im Layout ist er leer, gefüllt wird er in buildItemList. */
    private LinearLayout llItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.clMain), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        llItems = findViewById(R.id.llItems);
        loadItems();

        findViewById(R.id.fabAdd).setOnClickListener(v ->
                startActivity(new Intent(this, ItemFormActivity.class)));
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadItems();
    }

    /**
     * Holt alle Einträge aus der Datenbank und lässt sie anzeigen.
     */
    private void loadItems() {
        List<ShoppingItem> items = ShoppingDatabase.getInstance(this)
                .shoppingItemDao()
                .getAll();
        buildItemList(items);
        Log.d(TAG, "items loaded, total=" + items.size());
    }

    /**
     * Baut für jeden Eintrag eine Zeile und hängt sie in llItems.
     *
     * @param items die Einträge, die angezeigt werden sollen
     */
    private void buildItemList(List<ShoppingItem> items) {
        llItems.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);

        for (ShoppingItem item : items) {
            View row = inflater.inflate(R.layout.item_shopping, llItems, false);

            TextView tvArticle = row.findViewById(R.id.tvArticle);
            TextView tvShop = row.findViewById(R.id.tvShop);
            TextView tvAmount = row.findViewById(R.id.tvAmount);

            tvArticle.setText(item.getArticle());
            tvShop.setText(item.getShop());

            if (item.getQuantity() != null && item.getPricePerUnit() != null) {
                double amount = item.getQuantity() * item.getPricePerUnit();
                tvAmount.setText(getString(R.string.amount_format, amount));
            } else {
                tvAmount.setText("");
            }

            row.setOnClickListener(v -> {
                Intent intent = new Intent(this, ItemFormActivity.class);
                intent.putExtra(EXTRA_ITEM_ID, item.getId());
                startActivity(intent);
            });

            llItems.addView(row);
        }
    }
}