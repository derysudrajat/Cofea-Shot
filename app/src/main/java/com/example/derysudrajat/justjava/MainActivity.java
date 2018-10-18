package com.example.derysudrajat.justjava;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    int numberOfCoffees=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void submitOrder(View view) {
        //initialize
        EditText name = findViewById(R.id.nameEditText);
        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
        CheckBox ChocolateCheckBox = findViewById(R.id.chocolate_checkbox);

        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        boolean hasChocolate = ChocolateCheckBox.isChecked();
        String Customer = name.getText().toString().trim();
        name.setText("".trim());
        toasMassage();

        int price =calculatePrice(hasWhippedCream,hasChocolate);
        String priceMassage = createOrderSummary(price,hasWhippedCream, hasChocolate,Customer);
        //send to Email
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order for "+Customer);
        intent.putExtra(Intent.EXTRA_TEXT, priceMassage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        displayMassage(priceMassage);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    public void increment(View view){
        numberOfCoffees+=1;
        display(numberOfCoffees);
    }
    public void decrement(View view){
        if (numberOfCoffees>1){
            numberOfCoffees-=1;
            display(numberOfCoffees);
        }

    }
    public void toasMassage(){
        Context context = getApplicationContext();
        CharSequence text = "Order was created!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
    @SuppressLint("SetTextI18n")
    private void display(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText(""+number);
    }
    private String createOrderSummary(int price, boolean addWhipeedCream, boolean addChocolate, String name) {

        return "Name: "+name
                +"\nAdd whipped cream ? "+addWhipeedCream
                +"\nAdd Chocolate ? "+addChocolate
                +"\nQuantity: "+numberOfCoffees
                +"\nTotal: Rp."+price
                +"\nThank you!";
    }
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate){
        int topping=0;
        if (addWhippedCream==true) topping+=1000;
        if (addChocolate==true) topping+=2000;
        return numberOfCoffees*(5000+topping);
    }
    private void displayMassage(String massage) {
        TextView orderSummaryTextView = findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(massage);
    }
}
