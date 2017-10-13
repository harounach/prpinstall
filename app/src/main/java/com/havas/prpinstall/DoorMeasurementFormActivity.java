package com.havas.prpinstall;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.TabSettings;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DoorMeasurementFormActivity extends AppCompatActivity {

    public static final String LOG_TAG = DoorMeasurementFormActivity.class.getSimpleName();



    Spinner isThisInstallationSpinner;
    Spinner doorExactDimensionsSpinner;
    Spinner swingOfDoorSpinner;
    Spinner styleOfDoorSpinner;
    Spinner doorAdditionalItemsSpinner;
    Spinner doorWasLeadTestingPerformedSpinner;

    String isThisInstallationSpinnerString = "";
    String doorExactDimensionsSpinnerString = "";
    String swingOfDoorSpinnerString = "";
    String styleOfDoorSpinnerString = "";
    String doorAdditionalItemsSpinnerString = "";
    String doorWasLeadTestingPerformedSpinnerString = "";

    String mStringCustomerName;
    String mStringPONumber;
    String mString1;
    String mString2;
    String mString3;
    String mString4;
    String mString5;
    String mString6;
    String mString7;
    String mString8;



    EditText customerNameEditText;
    EditText pONumberEditText;
    //1
    EditText location_edit_text;
    //3
    EditText door_width_edit_text;
    EditText door_height_edit_text;
    EditText door_thickness_edit_text;

    //4
    CheckBox thickness_in_inches_jamb_checkbox;
    EditText thickness_in_inches_jamb_edit_text;
    CheckBox thickness_in_inches_wall_checkbox;
    EditText thickness_in_inches_wall_edit_text;

    //8
    CheckBox dump_frees_20_per_door_checkbox;
    EditText dump_frees_20_per_door_edit_text;

    //lead testing
    EditText door_was_lead_testing_performed_date_edit_text;
    EditText if_yes_edit_text;
    EditText if_no_edit_text;

    EditText custom_edit_text;
    EditText additional_labor_charges_edit_text;



    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 123;

    Document document;
    File mPdfFile;


    public static int sPageNumber = 0;

    ScrollView content;
    ProgressBar progressBar;
    ImageButton addNewPageButton;
    ImageButton sendEmailButton;
    TextView pageCountText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door_measurement_form);

        customerNameEditText = (EditText) findViewById(R.id.customer_name_edit_text);
        pONumberEditText = (EditText) findViewById(R.id.p_o_number_edit_text);

        //1
        location_edit_text = (EditText) findViewById(R.id.location_edit_text);
        //3
        door_width_edit_text = (EditText) findViewById(R.id.door_width_edit_text);
        door_height_edit_text = (EditText) findViewById(R.id.door_height_edit_text);
        door_thickness_edit_text = (EditText) findViewById(R.id.door_thickness_edit_text);

        //4
        thickness_in_inches_jamb_checkbox = (CheckBox) findViewById(R.id.thickness_in_inches_jamb_checkbox);
        thickness_in_inches_jamb_edit_text = (EditText) findViewById(R.id.thickness_in_inches_jamb_edit_text);
        thickness_in_inches_wall_checkbox = (CheckBox) findViewById(R.id.thickness_in_inches_wall_checkbox);
        thickness_in_inches_wall_edit_text = (EditText) findViewById(R.id.thickness_in_inches_wall_edit_text);

        //8
        dump_frees_20_per_door_checkbox = (CheckBox) findViewById(R.id.dump_frees_20_per_door_checkbox);
        dump_frees_20_per_door_edit_text = (EditText) findViewById(R.id.dump_frees_20_per_door_edit_text);

        //lead testing
        door_was_lead_testing_performed_date_edit_text = (EditText) findViewById(R.id.door_was_lead_testing_performed_date_edit_text);
        if_yes_edit_text = (EditText) findViewById(R.id.if_yes_edit_text);
        if_no_edit_text = (EditText) findViewById(R.id.if_no_edit_text);

        custom_edit_text = (EditText) findViewById(R.id.custom_edit_text);
        additional_labor_charges_edit_text = (EditText) findViewById(R.id.additional_labor_charges_edit_text);


        isThisInstallationSpinner = (Spinner) findViewById(R.id.is_this_installation_spinner);
        doorExactDimensionsSpinner = (Spinner) findViewById(R.id.door_exact_dimensions_spinner);
        swingOfDoorSpinner = (Spinner) findViewById(R.id.swing_of_door_spinner);
        styleOfDoorSpinner = (Spinner) findViewById(R.id.style_of_door_spinner);
        doorAdditionalItemsSpinner = (Spinner) findViewById(R.id.door_additional_items_spinner);
        doorWasLeadTestingPerformedSpinner = (Spinner) findViewById(R.id.door_was_lead_testing_performed_spinner);

        content = (ScrollView) findViewById(R.id.door_form_view);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        addNewPageButton = (ImageButton) findViewById(R.id.add_new_page_btn);
        sendEmailButton = (ImageButton) findViewById(R.id.send_email_btn);
        pageCountText = (TextView) findViewById(R.id.page_count_text);

        addNewPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddPageTask().execute();
            }
        });
        sendEmailButton.setVisibility(View.INVISIBLE);

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
        IsThisInstallationSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        isThisInstallationSpinner.setAdapter(IsThisInstallationSpinnerAdapter);
        isThisInstallationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                isThisInstallationSpinnerString = (String) adapterView.getItemAtPosition(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    //(2)
    private void setUpDoorExactDimensionsSpinner(){
        ArrayAdapter DoorExactDimensionsSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.door_exact_dimensions_array, android.R.layout.simple_spinner_item);
        DoorExactDimensionsSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        doorExactDimensionsSpinner.setAdapter(DoorExactDimensionsSpinnerAdapter);
        doorExactDimensionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                doorExactDimensionsSpinnerString = (String) adapterView.getItemAtPosition(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    //(5)
    private void setUpSwingOfDoorSpinner(){
        ArrayAdapter SwingOfDoorSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.swing_of_Door_array, android.R.layout.simple_spinner_item);
        SwingOfDoorSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        swingOfDoorSpinner.setAdapter(SwingOfDoorSpinnerAdapter);
        swingOfDoorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                swingOfDoorSpinnerString = (String) adapterView.getItemAtPosition(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    //(6)
    private void setUpStyleOfDoorSpinner(){
        ArrayAdapter StyleOfDoorSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.style_of_door_array, android.R.layout.simple_spinner_item);
        StyleOfDoorSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        styleOfDoorSpinner.setAdapter(StyleOfDoorSpinnerAdapter);
        styleOfDoorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                styleOfDoorSpinnerString = (String) adapterView.getItemAtPosition(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    //(7)
    private void setUpDoorAdditionalItemsSpinner(){
        ArrayAdapter DoorAdditionalItemsSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.door_additional_items_array, android.R.layout.simple_spinner_item);
        DoorAdditionalItemsSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        doorAdditionalItemsSpinner.setAdapter(DoorAdditionalItemsSpinnerAdapter);
        doorAdditionalItemsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                doorAdditionalItemsSpinnerString = (String) adapterView.getItemAtPosition(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    //(lead testing)
    private void setUpDoorWasLeadTestingPerformedSpinner(){
        ArrayAdapter DoorWasLeadTestingPerformedSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.door_was_lead_testing_performed_array, android.R.layout.simple_spinner_item);
        DoorWasLeadTestingPerformedSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        doorWasLeadTestingPerformedSpinner.setAdapter(DoorWasLeadTestingPerformedSpinnerAdapter);
        doorWasLeadTestingPerformedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                doorWasLeadTestingPerformedSpinnerString = (String) adapterView.getItemAtPosition(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    public void saveFormAsPdf(View view) {
        if(sPageNumber>0){
            closePdfDocument();
            sendEmail(mPdfFile);
        }
    }
    public void addPage() throws DocumentException, IOException {
        Chunk checked = new Chunk("O");
        checked.setBackground(BaseColor.BLACK);
        Chunk not_checked = new Chunk("O");
        not_checked.setBackground(BaseColor.WHITE);

        Font font = new Font();
        font.setSize(10f);

        mStringCustomerName = customerNameEditText.getText().toString();
        mStringPONumber = pONumberEditText.getText().toString();

        //1
        mString1 = "1)"+getString(R.string.is_this_installation_label)+" "+isThisInstallationSpinnerString+"  "+
                " Location:  "+location_edit_text.getText();

        //2
        mString2 = "2)"+getString(R.string.door_exact_dimensions_label)+": "+doorExactDimensionsSpinnerString;
        //3
        mString3 = "3)"+getString(R.string.door_width)+" = "+door_width_edit_text.getText().toString()+"   "+
                getString(R.string.door_height)+" = "+door_height_edit_text.getText().toString()+"   "+
                getString(R.string.door_thickness)+" = "+door_thickness_edit_text.getText().toString();

        //4
        mString4 = "4)"+getString(R.string.thickness_in_inches_label)+"  ";
        String mString4_jamb = "  "+getString(R.string.thickness_in_inches_jamb)+" "+
                thickness_in_inches_jamb_edit_text.getText().toString()+" ";
        String mString4_wall = "  "+getString(R.string.thickness_in_inches_wall)+" "+
                thickness_in_inches_wall_edit_text.getText().toString()+" ";
        Chunk mString4_chunk = new Chunk(mString4);
        Chunk mString4_jamb_chunk = new Chunk(mString4_jamb);
        Chunk mString4_wall_chunk = new Chunk(mString4_wall);

        //5
        mString5 = "5)"+getString(R.string.swing_of_door_label)+"  "+swingOfDoorSpinnerString;

        //6
        mString6 = "6)"+getString(R.string.style_of_door_label)+"  "+styleOfDoorSpinnerString;
        //7
        mString7 = "7)"+getString(R.string.door_additional_items_label)+"  "+doorAdditionalItemsSpinnerString;

        mString8 = "8)"+getString(R.string.additional_labor_charges_label);

        String mString8_part = " "+getString(R.string.dump_frees_20_per_door_label)+"  "+
                dump_frees_20_per_door_edit_text.getText().toString();
        Chunk mString8_part_chunk = new Chunk(mString8_part);

        //lead testing
        String door_was_lead_testing_performed_string = getString(R.string.door_was_lead_testing_performed_label)+
                "  "+doorWasLeadTestingPerformedSpinnerString+"  "+"DATE:  "+
                door_was_lead_testing_performed_date_edit_text.getText().toString();
        String if_yes_string = getString(R.string.if_yes_label)+"  "+if_yes_edit_text.getText().toString();
        String if_no_string = getString(R.string.if_no_label)+"  "+if_no_edit_text.getText().toString();
        String custom_string = "Custom:   "+custom_edit_text.getText().toString();
        String additional_labor_charges_string = "Additional Labor Charges:  "+
                additional_labor_charges_edit_text.getText().toString();






        String attentionText = getString(R.string.attention_text);

        Drawable d = getResources().getDrawable(R.drawable.logo);

        BitmapDrawable bitDw = ((BitmapDrawable) d);

        Bitmap bmp = bitDw.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);

        Image image = Image.getInstance(stream.toByteArray());
        image.setAlignment(Image.TOP|Image.TEXTWRAP);
        image.setBorder(10);
        image.scaleToFit(100, 100);
        if(sPageNumber>0){
            document.newPage();
        }

        document.add(image);
        Paragraph customerNamePONumberParagraph = new Paragraph();
        //customerNamePONumberParagraph.setFont(font);

        String customerNamePONumberString = "Customer Name:  "+mStringCustomerName+"           "
                +"P.O.#:  "+mStringPONumber;

        customerNamePONumberParagraph.add(customerNamePONumberString);

        Paragraph paragraph1 = new Paragraph();
        Paragraph paragraph2 = new Paragraph();
        Paragraph paragraph3 = new Paragraph();
        Paragraph paragraph4 = new Paragraph();
        Paragraph paragraph5 = new Paragraph();
        Paragraph paragraph6 = new Paragraph();
        Paragraph paragraph7 = new Paragraph();
        Paragraph paragraph8 = new Paragraph();
        Paragraph paragraph8_part = new Paragraph();
        //lead testing
        Paragraph paragraph_lead_testing = new Paragraph(getString(R.string.door_lead_testing_label));
        Paragraph door_was_lead_testing_performed_paragraph = new Paragraph();
        Paragraph if_yes_paragraph = new Paragraph();
        Paragraph if_no_paragraph = new Paragraph();
        Paragraph custom_paragraph = new Paragraph();
        Paragraph additional_labor_charges_paragraph = new Paragraph();

        //set font to paragraph
        //1
        paragraph1.setFont(font);
        paragraph1.add(mString1);
        //2
        paragraph2.setFont(font);
        paragraph2.add(mString2);
        //3
        paragraph3.setFont(font);
        paragraph3.add(mString3);
        //4
        paragraph4.setFont(font);
        paragraph4.add(mString4_chunk);
        paragraph4.add(isChecked(thickness_in_inches_jamb_checkbox));
        paragraph4.add(mString4_jamb_chunk);
        paragraph4.add(isChecked(thickness_in_inches_wall_checkbox));
        paragraph4.add(mString4_wall_chunk);
        //5
        paragraph5.setFont(font);
        paragraph5.add(mString5);
        //6
        paragraph6.setFont(font);
        paragraph6.add(mString6);
        //7
        paragraph7.setFont(font);
        paragraph7.add(mString7);
        //8
        paragraph8.setFont(font);
        paragraph8.add(mString8);

        paragraph8_part.setFont(font);
        paragraph8_part.add(isChecked(dump_frees_20_per_door_checkbox));
        paragraph8_part.add(mString8_part_chunk);

        //lead testing

        door_was_lead_testing_performed_paragraph.setFont(font);
        door_was_lead_testing_performed_paragraph.add(door_was_lead_testing_performed_string);

        if_yes_paragraph.setFont(font);
        if_yes_paragraph.add(if_yes_string);

        if_no_paragraph.setFont(font);
        if_no_paragraph.add(if_no_string);

        custom_paragraph.setFont(font);
        custom_paragraph.add(custom_string);

        additional_labor_charges_paragraph.setFont(font);
        additional_labor_charges_paragraph.add(additional_labor_charges_string);


        document.add(customerNamePONumberParagraph);



        document.add(paragraph1);
        document.add(paragraph2);
        document.add(paragraph3);
        document.add(paragraph4);
        document.add(paragraph5);
        document.add(paragraph6);
        document.add(paragraph7);
        document.add(paragraph8);
        document.add(paragraph8_part);
        document.add(paragraph_lead_testing);
        document.add(door_was_lead_testing_performed_paragraph);
        document.add(if_yes_paragraph);
        document.add(if_no_paragraph);
        document.add(new Paragraph("\n"));
        document.add(custom_paragraph);
        document.add(new Paragraph("\n"));
        document.add(additional_labor_charges_paragraph);

        document.add(new Paragraph("\n"));
        Paragraph attentionParagraph = new Paragraph();
        attentionParagraph.setFont(font);
        attentionParagraph.add(attentionText);
        document.add(attentionParagraph);
        sPageNumber++;

    }
    public Chunk isChecked(CheckBox checkBox){
        Chunk checked = new Chunk("O");
        checked.setBackground(BaseColor.BLACK);
        Chunk not_checked = new Chunk("O");
        not_checked.setBackground(BaseColor.WHITE);

        if(checkBox.isChecked()){
            return checked;
        }else {
            return not_checked;
        }
    }

    public void initPdfDocument() throws DocumentException, FileNotFoundException {
        File pdfFolder = new File(Environment.getExternalStorageDirectory()+ "/PdfOutput");

        if (!pdfFolder.exists()) {
            pdfFolder.mkdir();
            Log.i(LOG_TAG, "Pdf Directory created");
        }

        //Create time stamp
        Date date = new Date() ;
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HHmmss").format(date);
        mPdfFile = new File(pdfFolder +"/"+timeStamp+ ".pdf");

        OutputStream output = new FileOutputStream(mPdfFile);

        //document = new Document(PageSize.A4, 16, 16, 64, 34);
        document = new Document();

        PdfWriter.getInstance(document, output);
        document.open();
    }

    public void closePdfDocument(){
        document.close();
    }
    public void clearForm(){
        mStringCustomerName = "";
        mStringPONumber = "";
        mString1 = "";
        mString2 = "";
        mString3 = "";
        mString4 = "";
        mString5 = "";
        mString6 = "";
        mString7 = "";
        mString8 = "";
        customerNameEditText.setText("");
        pONumberEditText.setText("");
        //1
        location_edit_text.setText("");
        //3
        door_width_edit_text.setText("");
        door_height_edit_text.setText("");
        door_thickness_edit_text.setText("");

        //4
        thickness_in_inches_jamb_checkbox.setChecked(false);
        thickness_in_inches_jamb_edit_text.setText("");
        thickness_in_inches_wall_checkbox.setChecked(false);
        thickness_in_inches_wall_edit_text.setText("");

        //8
        dump_frees_20_per_door_checkbox.setChecked(false);
        dump_frees_20_per_door_edit_text.setText("");

        //lead testing
        door_was_lead_testing_performed_date_edit_text.setText("");
        if_yes_edit_text.setText("");
        if_no_edit_text.setText("");

        custom_edit_text.setText("");
        additional_labor_charges_edit_text.setText("");

    }

    @Override
    protected void onStart() {
        super.onStart();

        if(isSDCardAvailable()){
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
                    initPdfDocument();
                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

        }else {
            finish();
            Toast.makeText(this, "Please, Insert SD Card", Toast.LENGTH_LONG).show();
        }



    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                    try {
                        initPdfDocument();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    } catch (FileNotFoundException e) {
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

    public boolean isSDCardAvailable(){
        return Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }
    public void showProgressBar(){
        content.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }
    public void hideProgressBar(){
        content.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }
    public void showSendEmailButton(){
        if(sPageNumber>0){
            sendEmailButton.setVisibility(View.VISIBLE);
        }
    }
    public void refreshPageCountText(){
        pageCountText.setText("Page Added: "+sPageNumber);
    }

    class AddPageTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            showProgressBar();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                addPage();
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            hideProgressBar();
            clearForm();
            showSendEmailButton();
            refreshPageCountText();
        }
    }


}
