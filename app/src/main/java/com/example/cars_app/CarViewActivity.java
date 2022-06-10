package com.example.cars_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class CarViewActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextInputEditText edtModel, edtColor, edtDpl, edtDescription;
    private ImageView imageView;
    //This variable is like a car no.
    private int carId;
    private final static int PICK_IMAGE_REQUEST_CODE = 1;
    private static Uri imageUri;

    private AccessDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_view);

        toolbar = findViewById(R.id.activity_car_view_toolbar);
        setSupportActionBar(toolbar);

        imageView = findViewById(R.id.activity_car_view_image);
        edtModel = findViewById(R.id.edtModel);
        edtColor = findViewById(R.id.edtColor);
        edtDpl = findViewById(R.id.edtDpl);
        edtDescription = findViewById(R.id.edtDescription);

        database = AccessDatabase.getInstance(this);

        Intent intent = getIntent();
        //defaultValue=-1 because if the user didn't send any value it understand randomly that this an adding process
        //I used this function because I want to get the value of the variable sent by the Intent
        carId = intent.getIntExtra(MainActivity.CAR_KEY, -1);

        if (carId == -1) {
            //Add process
        } else {
            //show|edit process
            disableFields();
            database.openDatabase();
            Cars car = database.getCar(carId);
            database.closeDatabase();
            if (car != null)
                fillAllFields(car);
        }//end else

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE);
            }//end onClick()
        });

    }//end onCreate()

    private void enableFields() {
        imageView.setEnabled(true);
        edtModel.setEnabled(true);
        edtColor.setEnabled(true);
        edtDpl.setEnabled(true);
        edtDescription.setEnabled(true);
    }//end enableFields()

    private void disableFields() {
        imageView.setEnabled(false);
        edtModel.setEnabled(false);
        edtColor.setEnabled(false);
        edtDpl.setEnabled(false);
        edtDescription.setEnabled(false);
    }//end disableFields()

    private void clearFields() {
        imageView.setImageURI(null);
        edtModel.setText(" ");
        edtColor.setText(" ");
        edtDpl.setText(" ");
        edtDescription.setText(" ");
    }//end clearFields()

    private void fillAllFields(Cars cars) {
        if (cars.getImage() != null && !cars.getImage().equals(""))
            imageView.setImageURI(Uri.parse(cars.getImage()));
        edtModel.setText(cars.getModel());
        edtColor.setText(cars.getColor());
        edtDpl.setText(cars.getDpl() + "");
        edtDescription.setText(cars.getDescription());
    }//end fillAllFields()


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);

        MenuItem menu_check = menu.findItem(R.id.ic_check);
        MenuItem menu_edit = menu.findItem(R.id.ic_edit);
        MenuItem menu_delete = menu.findItem(R.id.ic_delete);

        if (carId == -1) {
            //view||add data process
            menu_check.setVisible(true);
            menu_edit.setVisible(false);
            menu_delete.setVisible(false);
        } else {
            //delete||edit data process
            menu_check.setVisible(false);
            menu_edit.setVisible(true);
            menu_delete.setVisible(true);
        }//end else
        return true;
    }//end onCreateOptionsMenu()

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String model, color, image = "", description;
        double dpl;
        database.openDatabase();
        switch (item.getItemId()) {
            case R.id.ic_check:
                model = edtModel.getText().toString();
                color = edtColor.getText().toString();
                description = edtDescription.getText().toString();
                if (imageUri != null)
                    image = imageUri.toString();
                dpl = Double.parseDouble(edtDpl.getText().toString());
                Cars cars = new Cars(carId, model, color, description, image, dpl);
                boolean result;
                if (carId == -1) {
                    result = database.insertData(cars);
                    if (result) {
                        Toast.makeText(this, "Inserted row", Toast.LENGTH_SHORT).show();
                        setResult(MainActivity.ADD_CAR_REQUEST_CODE, null);
                        finish();
                    }//end nested if()
                } else {
                    result = database.updateData(cars);
                    if (result) {
                        Toast.makeText(this, "Updated row", Toast.LENGTH_SHORT).show();
                        setResult(MainActivity.EDIT_CAR_REQUEST_CODE, null);
                        finish();
                    }//end nested if()
                }//end else
                return true;
            case R.id.ic_edit:
                MenuItem check = toolbar.getMenu().findItem(R.id.ic_check);
                MenuItem edit = toolbar.getMenu().findItem(R.id.ic_edit);
                MenuItem delete = toolbar.getMenu().findItem(R.id.ic_delete);
                check.setVisible(true);
                edit.setVisible(false);
                delete.setVisible(false);
                enableFields();
                return true;
            case R.id.ic_delete:
                Cars carsData = new Cars(carId, null, null, null, null, 0);
                result = database.deleteData(carsData);
                if (result) {
                    Toast.makeText(this, "Deleted Car Data", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK, null);
                    finish();
                }//end if()
                return true;
        }//end switch()
        database.closeDatabase();
        return false;
    }//end onOptionsItemSelected()

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                imageUri = data.getData();
                imageView.setImageURI(imageUri);
            }//end nested if()
        }//end if()
    }//end onActivityResult()
}//end class