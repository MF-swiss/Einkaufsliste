package ch.wiss.m335.einkaufsliste;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import ch.wiss.m335.einkaufsliste.data.ShoppingDatabase;
import ch.wiss.m335.einkaufsliste.data.ShoppingItem;

/**
 * Formular zum Anlegen und Ändern eines Eintrags.
 */
public class ItemFormActivity extends AppCompatActivity {

    private TextInputLayout tilArticle;
    private TextInputLayout tilShop;

    private TextInputEditText etArticle;
    private TextInputEditText etShop;
    private TextInputEditText etQuantity;
    private TextInputEditText etPrice;

    /** Die id des bearbeiteten Eintrags, oder -1 für einen neuen Eintrag. */
    private int itemId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_form);

        tilArticle = findViewById(R.id.tilArticle);
        tilShop = findViewById(R.id.tilShop);
        etArticle = findViewById(R.id.etArticle);
        etShop = findViewById(R.id.etShop);
        etQuantity = findViewById(R.id.etQuantity);
        etPrice = findViewById(R.id.etPrice);

        itemId = getIntent().getIntExtra(MainActivity.EXTRA_ITEM_ID, -1);
        if (itemId != -1) {
            loadItem();
        }

        findViewById(R.id.btnSave).setOnClickListener(v -> {
            if (isInputValid()) {
                save();
            }
        });
    }

    /**
     * Lädt den bestehenden Eintrag und füllt die vier Felder.
     */
    private void loadItem() {
        ShoppingItem item = ShoppingDatabase.getInstance(this)
                .shoppingItemDao()
                .findById(itemId);

        etArticle.setText(item.getArticle());
        etShop.setText(item.getShop());
        if (item.getQuantity() != null) {
            etQuantity.setText(String.valueOf(item.getQuantity()));
        }
        if (item.getPricePerUnit() != null) {
            etPrice.setText(String.valueOf(item.getPricePerUnit()));
        }
    }

    /**
     * Prüft alle Formularfelder.
     *
     * @return true, wenn gespeichert werden darf
     */
    private boolean isInputValid() {
        tilArticle.setError(null);
        tilShop.setError(null);

        boolean valid = true;

        String article = etArticle.getText().toString().trim();
        if (article.length() < 4) {
            tilArticle.setError(getString(R.string.error_min_length));
            valid = false;
        }

        String shop = etShop.getText().toString().trim();
        if (shop.length() < 4) {
            tilShop.setError(getString(R.string.error_min_length));
            valid = false;
        }

        return valid;
    }

    /**
     * Liest die vier Felder und legt einen Eintrag an oder ändert ihn.
     */
    private void save() {
        ShoppingItem item = new ShoppingItem();
        item.setArticle(etArticle.getText().toString().trim());
        item.setShop(etShop.getText().toString().trim());

        String quantityText = etQuantity.getText().toString().trim();
        item.setQuantity(quantityText.isEmpty() ? null : Integer.parseInt(quantityText));

        String priceText = etPrice.getText().toString().trim();
        item.setPricePerUnit(priceText.isEmpty() ? null : Double.parseDouble(priceText));

        if (itemId == -1) {
            ShoppingDatabase.getInstance(this).shoppingItemDao().insert(item);
        } else {
            item.setId(itemId);
            ShoppingDatabase.getInstance(this).shoppingItemDao().update(item);
        }

        finish();
    }
}