package com.havas.prpinstall;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DoorMeasurementFormActivity extends AppCompatActivity {

    public static final String LOG_TAG = DoorMeasurementFormActivity.class.getSimpleName();

    EditText customerNameEditText;
    EditText pONumberEditText;

    Spinner isThisInstallationSpinner;
    Spinner doorExactDimensionsSpinner;
    Spinner swingOfDoorSpinner;
    Spinner styleOfDoorSpinner;
    Spinner doorAdditionalItemsSpinner;
    Spinner doorWasLeadTestingPerformedSpinner;

    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door_measurement_form);

        customerNameEditText = (EditText) findViewById(R.id.customer_name_edit_text);
        pONumberEditText = (EditText) findViewById(R.id.p_o_number_edit_text);

        isThisInstallationSpinner = (Spinner) findViewById(R.id.is_this_installation_spinner);
        doorExactDimensionsSpinner = (Spinner) findViewById(R.id.door_exact_dimensions_spinner);
        swingOfDoorSpinner = (Spinner) findViewById(R.id.swing_of_door_spinner);
        styleOfDoorSpinner = (Spinner) findViewById(R.id.style_of_door_spinner);
        doorAdditionalItemsSpinner = (Spinner) findViewById(R.id.door_additional_items_spinner);
        doorWasLeadTestingPerformedSpinner = (Spinner) findViewById(R.id.door_was_lead_testing_performed_spinner);

        //set up spinners
        setUpIsThisInstallationSpinner();
        setUpDoorExactDimensionsSpinner();
        setUpSwingOfDoorSpinner();
        setUpStyleOfDoorSpinner();
        setUpDoorAdditionalItemsSpinner();
        setUpDoorWasLeadTestingPerformedSpinner();


    }

    //(1)
    private void setUpIsThisInstallationSpinner(){
        ArrayAdapter IsThisInstallationSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.is_this_installation_array, android.R.layout.simple_spinner_item);
        IsThisInstallationSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        isThisInstallationSpinner.setAdapter(IsThisInstallationSpinnerAdapter);
    }
    //(2)
    private void setUpDoorExactDimensionsSpinner(){
        ArrayAdapter DoorExactDimensionsSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.door_exact_dimensions_array, android.R.layout.simple_spinner_item);
        DoorExactDimensionsSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        doorExactDimensionsSpinner.setAdapter(DoorExactDimensionsSpinnerAdapter);
    }
    //(5)
    private void setUpSwingOfDoorSpinner(){
        ArrayAdapter SwingOfDoorSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.swing_of_Door_array, android.R.layout.simple_spinner_item);
        SwingOfDoorSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        swingOfDoorSpinner.setAdapter(SwingOfDoorSpinnerAdapter);
    }
    //(6)
    private void setUpStyleOfDoorSpinner(){
        ArrayAdapter StyleOfDoorSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.style_of_door_array, android.R.layout.simple_spinner_item);
        StyleOfDoorSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        styleOfDoorSpinner.setAdapter(StyleOfDoorSpinnerAdapter);
    }
    //(7)
    private void setUpDoorAdditionalItemsSpinner(){
        ArrayAdapter DoorAdditionalItemsSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.door_additional_items_array, android.R.layout.simple_spinner_item);
        DoorAdditionalItemsSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        doorAdditionalItemsSpinner.setAdapter(DoorAdditionalItemsSpinnerAdapter);
    }
    //(lead testing)
    private void setUpDoorWasLeadTestingPerformedSpinner(){
        ArrayAdapter DoorWasLeadTestingPerformedSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.door_was_lead_testing_performed_array, android.R.layout.simple_spinner_item);
        DoorWasLeadTestingPerformedSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        doorWasLeadTestingPerformedSpinner.setAdapter(DoorWasLeadTestingPerformedSpinnerAdapter);
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
