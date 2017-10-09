package com.havas.prpinstall;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.pdf.PdfDocument;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.print.PrintAttributes;
import android.print.pdf.PrintedPdfDocument;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.itextpdf.text.pdf.PdfAWriter;

import org.w3c.dom.Document;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;



public class WindowMeasurementFormActivity extends AppCompatActivity {

    private static final String LOG_TAG = WindowMeasurementFormActivity.class.getSimpleName();

    EditText customerNameEditText;
    EditText pONumberEditText;

    Spinner roomLocationSpinner;
    Spinner quantityOfSizeSpinner;
    Spinner exactDimensionSpinner;
    Spinner windowNeedToMeetCodeSpinner;
    Spinner safetyGlazingRequiredSpinner;
    Spinner styleOfWindowDesiredSpinner;
    Spinner customWidowSpinner;

    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_measurement_form);

        customerNameEditText = (EditText) findViewById(R.id.customer_name_edit_text);
        pONumberEditText = (EditText) findViewById(R.id.p_o_number_edit_text);

        roomLocationSpinner = (Spinner) findViewById(R.id.room_location_spinner);
        quantityOfSizeSpinner = (Spinner) findViewById(R.id.quantity_of_size_spinner);
        exactDimensionSpinner = (Spinner) findViewById(R.id.exact_dimension_spinner);
        windowNeedToMeetCodeSpinner = (Spinner) findViewById(R.id.window_need_to_meet_code_for_egress_spinner);
        safetyGlazingRequiredSpinner = (Spinner) findViewById(R.id.safety_glazing_required_spinner);
        styleOfWindowDesiredSpinner = (Spinner) findViewById(R.id.style_of_the_window_desired_spinner);
        customWidowSpinner = (Spinner) findViewById(R.id.custom_window_spinner);

        //set up spinners
        setUpRoomLocationSpinner();
        setUpQuantityOfSizeSpinner();
        setUpExactDimensionSpinner();
        setUpWindowNeedToMeetCodeSpinner();
        setUpSafetyGlazingRequiredSpinner();
        setUpStyleOfWindowDesiredSpinner();
        setUpCustomWidowSpinner();
    }
    //(1)
    private void setUpRoomLocationSpinner(){
        ArrayAdapter RoomLocationSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.room_location_array, android.R.layout.simple_spinner_item);
        RoomLocationSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        roomLocationSpinner.setAdapter(RoomLocationSpinnerAdapter);
    }
    //(2)
    private void setUpQuantityOfSizeSpinner(){
        ArrayAdapter QuantityOfSizeSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.quantity_of_this_size_array, android.R.layout.simple_spinner_item);
        QuantityOfSizeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        quantityOfSizeSpinner.setAdapter(QuantityOfSizeSpinnerAdapter);
    }
    //(3)
    private void setUpExactDimensionSpinner(){
        ArrayAdapter ExactDimensionSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.exact_dimension_array, android.R.layout.simple_spinner_item);
        ExactDimensionSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        exactDimensionSpinner.setAdapter(ExactDimensionSpinnerAdapter);
    }
    //(7)
    private void setUpWindowNeedToMeetCodeSpinner(){
        ArrayAdapter WindowNeedToMeetCodeSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.window_need_to_meet_code_array, android.R.layout.simple_spinner_item);
        WindowNeedToMeetCodeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        windowNeedToMeetCodeSpinner.setAdapter(WindowNeedToMeetCodeSpinnerAdapter);
    }
    //(8)
    private void setUpSafetyGlazingRequiredSpinner(){
        ArrayAdapter SafetyGlazingRequiredSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.safety_glazing_required_array, android.R.layout.simple_spinner_item);
        SafetyGlazingRequiredSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        safetyGlazingRequiredSpinner.setAdapter(SafetyGlazingRequiredSpinnerAdapter);
    }
    //(11)
    private void setUpStyleOfWindowDesiredSpinner(){
        ArrayAdapter StyleOfWindowDesiredSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.style_of_the_window_desired_array, android.R.layout.simple_spinner_item);
        StyleOfWindowDesiredSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        styleOfWindowDesiredSpinner.setAdapter(StyleOfWindowDesiredSpinnerAdapter);
    }
    //(custom window)
    private void setUpCustomWidowSpinner(){
        ArrayAdapter CustomWidowSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.custom_window_array, android.R.layout.simple_spinner_item);
        CustomWidowSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        customWidowSpinner.setAdapter(CustomWidowSpinnerAdapter);
    }

    public void saveFormAsPdf(View view) {
        //check if user has permission to write to external storage
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                //request write to external storage permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

        }else{
            //user already has external storage permission
            try {
                String customerName = customerNameEditText.getText().toString();
                String pONumber = pONumberEditText.getText().toString();
                if(customerName.equals("")&&pONumber.equals("")){
                    Toast.makeText(this, "Please specify customer name and P.O", Toast.LENGTH_LONG).show();
                }else{
                    generatePdf(customerName, pONumber);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    private void generatePdf(String customerName, String pONumber) throws IOException {
        File pdfFolder = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), "pdfdemo");
        if (!pdfFolder.exists()) {
            pdfFolder.mkdir();
            Log.i(LOG_TAG, "Pdf Directory created");
        }

        //Create time stamp
        Date date = new Date() ;
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HHmmss").format(date);
        String formattedName = customerName.replaceAll("\\s+","_");
        File pdfFile = new File(pdfFolder +"/"+ formattedName+"-"+pONumber +"-"+timeStamp+ ".pdf");

        OutputStream output = new FileOutputStream(pdfFile);


        ScrollView content = (ScrollView) findViewById(R.id.window_form_view);
        // create a new document
        PdfDocument document = new PdfDocument();

        // crate a page description
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(content.getWidth(),
                3000, 1).create();

        // start a page
        PdfDocument.Page page = document.startPage(pageInfo);

        // draw something on the page


        //content.measure(2250, 1400);
        //content.measure(content.getMeasuredWidth(), content.getMeasuredHeight());
        //content.layout(0,0, 2250, 1400);
        content.layout(0, 0, content.getWidth(), content.getHeight());
        content.draw(page.getCanvas());

        // finish the page
        document.finishPage(page);

        // add more pages

        // write the document content
        document.writeTo(output);

        // close the document
        document.close();
        output.close();
        //Now email the pdf generated.
        sendEmail(pdfFile);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted
                    try {
                        String customerName = customerNameEditText.getText().toString();
                        String pONumber = pONumberEditText.getText().toString();
                        if(customerName.equals("")&&pONumber.equals("")){
                            Toast.makeText(this, "Please specify customer name and P.O", Toast.LENGTH_LONG).show();
                        }else{
                            generatePdf(customerName, pONumber);
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                    Toast.makeText(this, "Sorry, you have to enable write external storage permission",
                            Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }

    }
    private void sendEmail(File pdfFile){
        Uri attachment = Uri.fromFile(pdfFile);
        String[] addresses = {"prpinstall@gmail.com"};
        String subject = customerNameEditText.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_STREAM, attachment);
        if (intent.resolveActivity(getPackageManager()) != null) {
            finish();
            startActivity(intent);

        }

    }

}
