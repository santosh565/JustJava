package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private String createOrderSummary(int price , boolean addWhippedCream , boolean addsChocolate, String name){
        String priceMessage ="Name :"+name + "\nAdd Whipped Cream? " + addWhippedCream+ "\nAdd Chocolate? " + addsChocolate +   "\nQuantity :"+quantity +"\nTotal : $" + price + "\nThank You!";
        return priceMessage;
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox checkBox = findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = checkBox.isChecked();

        CheckBox chocolateCheckBox = findViewById(R.id.chololate_checkbox);
        boolean addChocolate = chocolateCheckBox.isChecked();

        EditText editText = findViewById(R.id.edtTxt_name);
        String userName = editText.getText().toString();

        int price = calculatePrice(hasWhippedCream,addChocolate);
        String priceMessage = createOrderSummary(price,hasWhippedCream,addChocolate,userName);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for "+  userName);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    private int calculatePrice(boolean addCream, boolean addChocolate){

        int basePrice = 5;

        if (addCream) basePrice =basePrice+ 1;

        if (addChocolate){
            basePrice = basePrice+2;

        }

        return quantity*basePrice;

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(""+number);
    }


    public void decrement(View view) {

        if(quantity == 1){
            Toast.makeText(getApplicationContext(),"cannot go further",Toast.LENGTH_LONG).show();
            return;
        }

        quantity = quantity - 1;
        display(quantity);

    }

    public void increment(View view) {

        if(quantity == 10){
            Toast.makeText(getApplicationContext(),"cannot go further",Toast.LENGTH_LONG).show();
            return;
        }
        quantity = quantity+1;
        display(quantity);

    }

    /**
     * This method displays the given text on the screen.
     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }


}