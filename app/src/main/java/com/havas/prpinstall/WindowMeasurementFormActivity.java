package com.havas.prpinstall;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.TabSettings;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfWriter;


import java.io.ByteArrayOutputStream;
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

    private String roomLocationSpinnerString = "";
    private String quantityOfSizeSpinnerString = "";
    private String exactDimensionSpinnerString = "";
    private String windowNeedToMeetCodeSpinnerString = "";
    private String safetyGlazingRequiredSpinnerString = "";
    private String styleOfWindowDesiredSpinnerString = "";
    private String customWidowSpinnerString = "";

    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 123;



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
    String mString9;
    String mString10;
    String mString11;
    String mString12;
    String mString13;
    String mString14;

    public EditText window_width_edit_text;
    EditText window_height_edit_text;
    EditText jumb_thickness_edit_text;
    EditText sill_height_edit_text;
    EditText exterior_finish_edit_text;
    EditText interior_finish_edit_text;
    EditText height_to_top_edit_text;
    EditText fall_protection_edit_text;
    CheckBox additional_items_ext_trim_checkbox;
    EditText additional_items_ext_trim_edit_text;
    CheckBox additional_items_int_trim_checkbox;
    EditText additional_items_int_trim_edit_text;
    CheckBox additional_items_jamb_material_checkbox;
    EditText additional_items_jamb_material_edit_text;

    CheckBox delivery_checkbox;
    EditText delivery_edit_text;

    CheckBox window_size_up_to_101_ui_checkbox;
    CheckBox dollar_240_checkbox;
    EditText window_size_up_to_101_ui_edit_text;

    CheckBox window_size_up_to_132_ui_checkbox;
    CheckBox dollar_350_checkbox;
    EditText window_size_up_to_132_ui_edit_text;

    CheckBox oversize_larger_than_132_ui_checkbox;
    CheckBox dollar_checkbox;
    EditText dollar_edit_text;
    EditText oversize_larger_than_132_ui_edit_text;

    CheckBox custom_window_checkbox;

    CheckBox frame_removal_checkbox;
    CheckBox dollar_60_checkbox;
    EditText frame_removal_edit_text;

    CheckBox exterior_trim_checbox;
    EditText exterior_trim_edit_text;

    CheckBox rip_and_install_jamb_extension_checkbox;
    EditText rip_and_install_jamb_extension_edit_text;

    CheckBox drop_still_to_meet_egress_checkbox;
    EditText drop_still_to_meet_egress_edit_text;

    CheckBox dump_fee_20_per_window_checkbox;
    EditText dump_fee_20_per_window_edit_text;

    CheckBox was_lead_testing_performed_yes_checkbox;
    CheckBox was_lead_testing_performed_no_checkbox;

    EditText year_built_in_edit_text;

    EditText custom_labor_cost_edit_text;


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
        setContentView(R.layout.activity_window_measurement_form);

        window_width_edit_text = (EditText) findViewById(R.id.window_width_edit_text);
        window_height_edit_text = (EditText) findViewById(R.id.window_height_edit_text);
        jumb_thickness_edit_text = (EditText) findViewById(R.id.jumb_thickness_edit_text);
        sill_height_edit_text = (EditText) findViewById(R.id.sill_height_edit_text);
        exterior_finish_edit_text = (EditText) findViewById(R.id.exterior_finish_edit_text);
        interior_finish_edit_text = (EditText) findViewById(R.id.interior_finish_edit_text);
        height_to_top_edit_text = (EditText) findViewById(R.id.height_to_top_edit_text);
        fall_protection_edit_text = (EditText) findViewById(R.id.fall_protection_edit_text);
        additional_items_ext_trim_checkbox = (CheckBox) findViewById(R.id.additional_items_ext_trim_checkbox);
        additional_items_ext_trim_edit_text = (EditText) findViewById(R.id.additional_items_ext_trim_edit_text);
        additional_items_int_trim_checkbox = (CheckBox) findViewById(R.id.additional_items_int_trim_checkbox);;
        additional_items_int_trim_edit_text = (EditText) findViewById(R.id.additional_items_int_trim_edit_text);
        additional_items_jamb_material_checkbox = (CheckBox) findViewById(R.id.additional_items_jamb_material_checkbox);;
        additional_items_jamb_material_edit_text = (EditText) findViewById(R.id.additional_items_jamb_material_edit_text);

        delivery_checkbox = (CheckBox) findViewById(R.id.delivery_checkbox);;
        delivery_edit_text = (EditText) findViewById(R.id.delivery_edit_text);

        window_size_up_to_101_ui_checkbox = (CheckBox) findViewById(R.id.window_size_up_to_101_ui_checkbox);;
        dollar_240_checkbox = (CheckBox) findViewById(R.id.dollar_240_checkbox);;
        window_size_up_to_101_ui_edit_text = (EditText) findViewById(R.id.window_size_up_to_101_ui_edit_text);

        window_size_up_to_132_ui_checkbox = (CheckBox) findViewById(R.id.window_size_up_to_132_ui_checkbox);;
        dollar_350_checkbox = (CheckBox) findViewById(R.id.dollar_350_checkbox);;
        window_size_up_to_132_ui_edit_text = (EditText) findViewById(R.id.window_size_up_to_132_ui_edit_text);

        oversize_larger_than_132_ui_checkbox = (CheckBox) findViewById(R.id.oversize_larger_than_132_ui_checkbox);;
        dollar_checkbox = (CheckBox) findViewById(R.id.dollar_checkbox);
        dollar_edit_text = (EditText) findViewById(R.id.dollar_edit_text);
        oversize_larger_than_132_ui_edit_text = (EditText) findViewById(R.id.oversize_larger_than_132_ui_edit_text);

        custom_window_checkbox = (CheckBox) findViewById(R.id.custom_window_checkbox);;

        frame_removal_checkbox = (CheckBox) findViewById(R.id.frame_removal_checkbox);;
        dollar_60_checkbox = (CheckBox) findViewById(R.id.dollar_60_checkbox);;
        frame_removal_edit_text = (EditText) findViewById(R.id.frame_removal_edit_text);

        exterior_trim_checbox = (CheckBox) findViewById(R.id.exterior_trim_checbox);;
        exterior_trim_edit_text = (EditText) findViewById(R.id.exterior_trim_edit_text);

        rip_and_install_jamb_extension_checkbox = (CheckBox) findViewById(R.id.rip_and_install_jamb_extension_checkbox);;
        rip_and_install_jamb_extension_edit_text = (EditText) findViewById(R.id.rip_and_install_jamb_extension_edit_text);

        drop_still_to_meet_egress_checkbox = (CheckBox) findViewById(R.id.drop_still_to_meet_egress_checkbox);;
        drop_still_to_meet_egress_edit_text = (EditText) findViewById(R.id.drop_still_to_meet_egress_edit_text);

        dump_fee_20_per_window_checkbox = (CheckBox) findViewById(R.id.dump_fee_20_per_window_checkbox);;
        dump_fee_20_per_window_edit_text = (EditText) findViewById(R.id.dump_fee_20_per_window_edit_text);

        was_lead_testing_performed_yes_checkbox = (CheckBox) findViewById(R.id.was_lead_testing_performed_yes_checkbox);;
        was_lead_testing_performed_no_checkbox = (CheckBox) findViewById(R.id.was_lead_testing_performed_no_checkbox);;

        year_built_in_edit_text = (EditText) findViewById(R.id.year_built_in_edit_text);

        custom_labor_cost_edit_text = (EditText) findViewById(R.id.custom_labor_cost_edit_text);

        customerNameEditText = (EditText) findViewById(R.id.customer_name_edit_text);
        pONumberEditText = (EditText) findViewById(R.id.p_o_number_edit_text);

        roomLocationSpinner = (Spinner) findViewById(R.id.room_location_spinner);
        quantityOfSizeSpinner = (Spinner) findViewById(R.id.quantity_of_size_spinner);
        exactDimensionSpinner = (Spinner) findViewById(R.id.exact_dimension_spinner);
        windowNeedToMeetCodeSpinner = (Spinner) findViewById(R.id.window_need_to_meet_code_for_egress_spinner);
        safetyGlazingRequiredSpinner = (Spinner) findViewById(R.id.safety_glazing_required_spinner);
        styleOfWindowDesiredSpinner = (Spinner) findViewById(R.id.style_of_the_window_desired_spinner);
        customWidowSpinner = (Spinner) findViewById(R.id.custom_window_spinner);

        content = (ScrollView) findViewById(R.id.window_form_view);
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
        setUpRoomLocationSpinner();
        setUpQuantityOfSizeSpinner();
        setUpExactDimensionSpinner();
        setUpWindowNeedToMeetCodeSpinner();
        setUpSafetyGlazingRequiredSpinner();
        setUpStyleOfWindowDesiredSpinner();
        setUpCustomWidowSpinner();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.window_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.add_page_id:{
                try {
                    try {
                        addPage();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    //(1)
    private void setUpRoomLocationSpinner(){
        ArrayAdapter RoomLocationSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.room_location_array, android.R.layout.simple_spinner_item);
        RoomLocationSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomLocationSpinner.setAdapter(RoomLocationSpinnerAdapter);
        roomLocationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                roomLocationSpinnerString = (String) adapterView.getItemAtPosition(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    //(2)
    private void setUpQuantityOfSizeSpinner(){
        ArrayAdapter QuantityOfSizeSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.quantity_of_this_size_array, android.R.layout.simple_spinner_item);
        QuantityOfSizeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quantityOfSizeSpinner.setAdapter(QuantityOfSizeSpinnerAdapter);
        quantityOfSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                quantityOfSizeSpinnerString = (String) adapterView.getItemAtPosition(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    //(3)
    private void setUpExactDimensionSpinner(){
        ArrayAdapter ExactDimensionSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.exact_dimension_array, android.R.layout.simple_spinner_item);
        ExactDimensionSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exactDimensionSpinner.setAdapter(ExactDimensionSpinnerAdapter);
        exactDimensionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                exactDimensionSpinnerString = (String) adapterView.getItemAtPosition(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    //(7)
    private void setUpWindowNeedToMeetCodeSpinner(){
        ArrayAdapter WindowNeedToMeetCodeSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.window_need_to_meet_code_array, android.R.layout.simple_spinner_item);
        WindowNeedToMeetCodeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        windowNeedToMeetCodeSpinner.setAdapter(WindowNeedToMeetCodeSpinnerAdapter);
        windowNeedToMeetCodeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                windowNeedToMeetCodeSpinnerString = (String) adapterView.getItemAtPosition(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    //(8)
    private void setUpSafetyGlazingRequiredSpinner(){
        ArrayAdapter SafetyGlazingRequiredSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.safety_glazing_required_array, android.R.layout.simple_spinner_item);
        SafetyGlazingRequiredSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        safetyGlazingRequiredSpinner.setAdapter(SafetyGlazingRequiredSpinnerAdapter);
        safetyGlazingRequiredSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                safetyGlazingRequiredSpinnerString = (String) adapterView.getItemAtPosition(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    //(11)
    private void setUpStyleOfWindowDesiredSpinner(){
        ArrayAdapter StyleOfWindowDesiredSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.style_of_the_window_desired_array, android.R.layout.simple_spinner_item);
        StyleOfWindowDesiredSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        styleOfWindowDesiredSpinner.setAdapter(StyleOfWindowDesiredSpinnerAdapter);
        styleOfWindowDesiredSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                styleOfWindowDesiredSpinnerString = (String) adapterView.getItemAtPosition(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    //(custom window)
    private void setUpCustomWidowSpinner(){
        ArrayAdapter CustomWidowSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.custom_window_array, android.R.layout.simple_spinner_item);
        CustomWidowSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        customWidowSpinner.setAdapter(CustomWidowSpinnerAdapter);
        customWidowSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                customWidowSpinnerString = (String) adapterView.getItemAtPosition(pos);
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
        intent.putExtra(Intent.EXTRA_SUBJECT, "PRP Install");
        intent.putExtra(Intent.EXTRA_STREAM, attachment);
        if (intent.resolveActivity(getPackageManager()) != null) {
            finish();
            startActivity(intent);

        }

    }
    private void createPdfWithITextLibrary(){

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



        mString1 = "1)"+getString(R.string.room_location_label)+" "+roomLocationSpinnerString;
        mString2 = "2)"+getString(R.string.quantity_of_size_label)+": "+quantityOfSizeSpinnerString;
        mString3 = "3)"+getString(R.string.exact_dimension_label)+": "+exactDimensionSpinnerString;

        mString4 = "4)"+getString(R.string.width_label)+" = "+
                window_width_edit_text.getText().toString()+"   "+getString(R.string.width_label)+
                " = "+window_height_edit_text.getText().toString();

        mString5 = "5)"+getString(R.string.jamb_thickness_label)+" = "+
                jumb_thickness_edit_text.getText().toString();

        mString6 = "6)"+getString(R.string.sill_height_label)+" = "+
                sill_height_edit_text.getText().toString();

        mString7 = "7)"+getString(R.string.window_need_to_meet_code_for_egress_label)+"  "+
                windowNeedToMeetCodeSpinnerString;

        mString8 = "8)"+getString(R.string.safety_glazing_required_label)+"  "+
                safetyGlazingRequiredSpinnerString;

        mString9 = "9)"+getString(R.string.exterior_finish_label)+"  "+
                exterior_finish_edit_text.getText().toString();

        mString10 = "10)"+getString(R.string.interior_finish_label)+"  "+
                interior_finish_edit_text.getText().toString();

        mString11 = "11)"+getString(R.string.style_of_the_window_desired_label)+":  "+
                styleOfWindowDesiredSpinnerString;

        mString12 = "12)"+getString(R.string.height_to_top_label)+":  "+
                height_to_top_edit_text.getText().toString();

        mString13 = "13)"+getString(R.string.fall_protection_label)+":  "+
                fall_protection_edit_text.getText().toString()+" each";

        String string14_1 = "";
        String string14_2 = "";
        String string14_3 = "";
        string14_1 = " "+getString(R.string.additional_items_ext_trim)+" "+
                additional_items_ext_trim_edit_text.getText().toString()+"\n";
        string14_2 = " "+getString(R.string.additional_items_int_trim)+" "+
                additional_items_int_trim_edit_text.getText().toString()+"\n";
        string14_3 = " "+getString(R.string.additional_items_jamb_material)+" "+
                additional_items_jamb_material_edit_text.getText().toString()+"\n";

        mString14 = "14)"+getString(R.string.additional_items_label)+":\n";



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
        Paragraph paragraph9 = new Paragraph();
        Paragraph paragraph10 = new Paragraph();
        Paragraph paragraph11 = new Paragraph();
        Paragraph paragraph12 = new Paragraph();
        Paragraph paragraph13 = new Paragraph();
        //set font to paragraph
        paragraph1.setFont(font);
        paragraph1.add(mString1);

        paragraph2.setFont(font);
        paragraph2.add(mString2);
        paragraph3.setFont(font);
        paragraph3.add(mString3);
        paragraph4.setFont(font);
        paragraph4.add(mString4);
        paragraph5.setFont(font);
        paragraph5.add(mString5);
        paragraph6.setFont(font);
        paragraph6.add(mString6);
        paragraph7.setFont(font);
        paragraph7.add(mString7);
        paragraph8.setFont(font);
        paragraph8.add(mString8);
        paragraph9.setFont(font);
        paragraph9.add(mString9);
        paragraph10.setFont(font);
        paragraph10.add(mString10);
        paragraph11.setFont(font);
        paragraph11.add(mString11);
        paragraph12.setFont(font);
        paragraph12.add(mString12);
        paragraph13.setFont(font);
        paragraph13.add(mString13);

        document.add(customerNamePONumberParagraph);



        document.add(paragraph1);
        document.add(paragraph2);
        document.add(paragraph3);
        document.add(paragraph4);
        document.add(paragraph5);
        document.add(paragraph6);
        document.add(paragraph7);
        document.add(paragraph8);
        document.add(paragraph9);
        document.add(paragraph10);
        document.add(paragraph11);
        document.add(paragraph12);
        document.add(paragraph13);


        Paragraph paragraph14 = new Paragraph();
        paragraph14.setFont(font);
        paragraph14.add(mString14);
        paragraph14.setTabSettings(new TabSettings(56f));
        paragraph14.add(Chunk.TABBING);
        paragraph14.add(isChecked(additional_items_ext_trim_checkbox));
        paragraph14.add(new Chunk(string14_1));
        paragraph14.setTabSettings(new TabSettings(56f));
        paragraph14.add(Chunk.TABBING);
        paragraph14.add(isChecked(additional_items_int_trim_checkbox));
        paragraph14.add(new Chunk(string14_2));
        paragraph14.setTabSettings(new TabSettings(56f));
        paragraph14.add(Chunk.TABBING);
        paragraph14.add(isChecked(additional_items_jamb_material_checkbox));
        paragraph14.add(new Chunk(string14_3));
        document.add(paragraph14);

        document.add(new Paragraph("\n"));


        Paragraph additional_work_required_paragraph = new Paragraph();
        //additional_work_required_paragraph.setFont(font);
        additional_work_required_paragraph.add(getString(R.string.additional_work_required_label)+":");
        document.add(additional_work_required_paragraph);



        //additional_work_required
        String delivery_string = "";

        String window_size_up_to_101_ui_string = "";
        String dollar240_string = "";

        String window_size_up_to_132_ui_string = "";
        String dollar350_string = "";

        String oversize_larger_than_132_ui_string = "";
        String dollar_string = "";
        String custom_price_string = "";

        String custom_window_string = "";

        String frame_removal_string ="";
        String dollar60_string = "";

        String exterior_trim_string = "";

        String rip_and_install_jamb_extension_string = "";

        String drop_still_to_meet_egress_string = "";

        String dump_fee_20_per_window_string = "";

        String was_lead_testing_performed_string ="";

        String year_built_in_string = "";

        String custom_labor_cost_string = "";

        //1
        delivery_string = " "+getString(R.string.delivery_label)+" "+
                delivery_edit_text.getText().toString();
        //2
        window_size_up_to_101_ui_string = " "+getString(R.string.window_size_up_to_101_ui_label)+"      ";
        dollar240_string = " $240    "+window_size_up_to_101_ui_edit_text.getText().toString();
        //3
        window_size_up_to_132_ui_string = " "+getString(R.string.window_size_up_to_132_ui_label)+"      ";
        dollar350_string = " $350    "+window_size_up_to_132_ui_edit_text.getText().toString();
        //4
        oversize_larger_than_132_ui_string = " "+getString(R.string.oversize_larger_than_132_ui_label)+"      ";
        dollar_string =" $    "+ dollar_edit_text.getText().toString()+" ";
        custom_price_string = "    Custom Price    "+oversize_larger_than_132_ui_edit_text.getText().toString();
        //5
        custom_window_string = " "+getString(R.string.custom_window_label)+" "+
                customWidowSpinnerString;
        //6
        frame_removal_string =" "+getString(R.string.frame_removal_label)+"  ";
        dollar60_string = " $60    "+frame_removal_edit_text.getText().toString();
        //7
        exterior_trim_string = " "+getString(R.string.exterior_trim_label)+" "+
                exterior_trim_edit_text.getText().toString();
        //8
        rip_and_install_jamb_extension_string = " "+getString(R.string.rip_and_install_jamb_extension_label)+" "+
                rip_and_install_jamb_extension_edit_text.getText().toString();
        //9
        drop_still_to_meet_egress_string = " "+getString(R.string.drop_still_to_meet_egress_label)+" "+
                drop_still_to_meet_egress_edit_text.getText().toString();
        //10
        dump_fee_20_per_window_string = " "+getString(R.string.dump_fee_20_per_window_label)+" "+
                dump_fee_20_per_window_edit_text.getText().toString();
        //11
        was_lead_testing_performed_string =" "+getString(R.string.was_lead_testing_performed_label);
        //12
        year_built_in_string = " "+getString(R.string.year_built_in_label)+" "+
                year_built_in_edit_text.getText().toString();
        //13
        custom_labor_cost_string = " "+getString(R.string.custom_labor_cost_label)+": "+
                custom_labor_cost_edit_text.getText().toString();

        //declare additional_work_required paragraphs
        Paragraph delivery_string_paragraph = new Paragraph();
        Paragraph window_size_up_to_101_ui_string_paragraph = new Paragraph();
        Paragraph window_size_up_to_132_ui_string_paragraph = new Paragraph();
        Paragraph oversize_larger_than_132_ui_string_paragraph = new Paragraph();
        Paragraph custom_window_string_paragraph = new Paragraph();
        Paragraph frame_removal_string_paragraph = new Paragraph();
        Paragraph exterior_trim_string_paragraph = new Paragraph();
        Paragraph rip_and_install_jamb_extension_string_paragraph = new Paragraph();
        Paragraph drop_still_to_meet_egress_string_paragraph = new Paragraph();
        Paragraph dump_fee_20_per_window_string_paragraph = new Paragraph();
        Paragraph lead_testing_paragraph = new Paragraph(getString(R.string.lead_testing_label));
        Paragraph was_lead_testing_performed_string_paragraph = new Paragraph();
        Paragraph year_built_in_string_paragraph = new Paragraph();
        Paragraph custom_labor_cost_string_paragraph = new Paragraph();
        //define chunks
        //1
        Chunk delivery_chunk = new Chunk(delivery_string);

        Chunk window_size_up_to_101_ui_chunk = new Chunk(window_size_up_to_101_ui_string);
        Chunk dollar240_chunk = new Chunk(dollar240_string);

        Chunk window_size_up_to_132_ui_chunk = new Chunk(window_size_up_to_132_ui_string);
        Chunk dollar350_chunk = new Chunk(dollar350_string);

        Chunk oversize_larger_than_132_ui_chunk = new Chunk(oversize_larger_than_132_ui_string);
        Chunk dollar_chunk = new Chunk(dollar_string);
        Chunk custom_price_chunk = new Chunk(custom_price_string);

        Chunk custom_window_chunk = new Chunk(custom_window_string);


        Chunk frame_removal_chunk = new Chunk(frame_removal_string);
        Chunk dollar60_chunk = new Chunk(dollar60_string);

        Chunk exterior_trim_chunk = new Chunk(exterior_trim_string);

        Chunk rip_and_install_jamb_extension_chunk = new Chunk(rip_and_install_jamb_extension_string);

        Chunk drop_still_to_meet_egress_chunk = new Chunk(drop_still_to_meet_egress_string);

        Chunk dump_fee_20_per_window_chunk = new Chunk(dump_fee_20_per_window_string);



        //fill in the paragraphs
        //1
        delivery_string_paragraph.setFont(font);
        delivery_string_paragraph.add(isChecked(delivery_checkbox));
        delivery_string_paragraph.add(delivery_chunk);
        //2
        window_size_up_to_101_ui_string_paragraph.setFont(font);
        window_size_up_to_101_ui_string_paragraph.add(isChecked(window_size_up_to_101_ui_checkbox));
        window_size_up_to_101_ui_string_paragraph.add(window_size_up_to_101_ui_chunk);
        window_size_up_to_101_ui_string_paragraph.add(isChecked(dollar_240_checkbox));
        window_size_up_to_101_ui_string_paragraph.add(dollar240_chunk);
        //3
        window_size_up_to_132_ui_string_paragraph.setFont(font);
        window_size_up_to_132_ui_string_paragraph.add(isChecked(window_size_up_to_132_ui_checkbox));
        window_size_up_to_132_ui_string_paragraph.add(window_size_up_to_132_ui_chunk);
        window_size_up_to_132_ui_string_paragraph.add(isChecked(dollar_350_checkbox));
        window_size_up_to_132_ui_string_paragraph.add(dollar350_chunk);
        //4
        oversize_larger_than_132_ui_string_paragraph.setFont(font);
        oversize_larger_than_132_ui_string_paragraph.add(isChecked(oversize_larger_than_132_ui_checkbox));
        oversize_larger_than_132_ui_string_paragraph.add(oversize_larger_than_132_ui_chunk);
        oversize_larger_than_132_ui_string_paragraph.add(isChecked(dollar_checkbox));
        oversize_larger_than_132_ui_string_paragraph.add(dollar_chunk);
        oversize_larger_than_132_ui_string_paragraph.add(custom_price_chunk);

        //5
        custom_window_string_paragraph.setFont(font);
        custom_window_string_paragraph.add(isChecked(custom_window_checkbox));
        custom_window_string_paragraph.add(custom_window_chunk);
        //6
        frame_removal_string_paragraph.setFont(font);
        frame_removal_string_paragraph.add(isChecked(frame_removal_checkbox));
        frame_removal_string_paragraph.add(frame_removal_chunk);
        frame_removal_string_paragraph.add(isChecked(dollar_60_checkbox));
        frame_removal_string_paragraph.add(dollar60_chunk);
        //7
        exterior_trim_string_paragraph.setFont(font);
        exterior_trim_string_paragraph.add(isChecked(exterior_trim_checbox));
        exterior_trim_string_paragraph.add(exterior_trim_chunk);

        //8
        rip_and_install_jamb_extension_string_paragraph.setFont(font);
        rip_and_install_jamb_extension_string_paragraph.add(isChecked(rip_and_install_jamb_extension_checkbox));
        rip_and_install_jamb_extension_string_paragraph.add(rip_and_install_jamb_extension_chunk);

        //9
        drop_still_to_meet_egress_string_paragraph.setFont(font);
        drop_still_to_meet_egress_string_paragraph.add(isChecked(drop_still_to_meet_egress_checkbox));
        drop_still_to_meet_egress_string_paragraph.add(drop_still_to_meet_egress_chunk);

        //10
        dump_fee_20_per_window_string_paragraph.setFont(font);
        dump_fee_20_per_window_string_paragraph.add(isChecked(dump_fee_20_per_window_checkbox));
        dump_fee_20_per_window_string_paragraph.add(dump_fee_20_per_window_chunk);

        //11
        was_lead_testing_performed_string_paragraph.setFont(font);
        was_lead_testing_performed_string_paragraph.add(was_lead_testing_performed_string);
        //12
        year_built_in_string_paragraph.setFont(font);
        year_built_in_string_paragraph.add(year_built_in_string);
        //13
        custom_labor_cost_string_paragraph.setFont(font);
        custom_labor_cost_string_paragraph.add(custom_labor_cost_string);


        document.add(delivery_string_paragraph);
        document.add(window_size_up_to_101_ui_string_paragraph);
        document.add(window_size_up_to_132_ui_string_paragraph);
        document.add(oversize_larger_than_132_ui_string_paragraph);
        document.add(custom_window_string_paragraph);
        document.add(frame_removal_string_paragraph);
        document.add(exterior_trim_string_paragraph);
        document.add(rip_and_install_jamb_extension_string_paragraph);
        document.add(drop_still_to_meet_egress_string_paragraph);
        document.add(dump_fee_20_per_window_string_paragraph);
        document.add(lead_testing_paragraph);
        document.add(was_lead_testing_performed_string_paragraph);
        document.add(year_built_in_string_paragraph);
        document.add(custom_labor_cost_string_paragraph);



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
        mString9 = "";
        mString10 = "";
        mString11 = "";
        mString12 = "";
        mString13 = "";
        mString14 = "";

        window_width_edit_text.setText("");
        window_height_edit_text.setText("");
        jumb_thickness_edit_text.setText("");
        sill_height_edit_text.setText("");
        exterior_finish_edit_text.setText("");
        interior_finish_edit_text.setText("");
        height_to_top_edit_text.setText("");
        fall_protection_edit_text.setText("");
        additional_items_ext_trim_checkbox.setChecked(false);
        additional_items_ext_trim_edit_text.setText("");
        additional_items_int_trim_checkbox.setChecked(false);
        additional_items_int_trim_edit_text.setText("");
        additional_items_jamb_material_checkbox.setChecked(false);
        additional_items_jamb_material_edit_text.setText("");

        delivery_checkbox.setChecked(false);
        delivery_edit_text.setText("");

        window_size_up_to_101_ui_checkbox.setChecked(false);
        dollar_240_checkbox.setChecked(false);
        window_size_up_to_101_ui_edit_text.setText("");

        window_size_up_to_132_ui_checkbox.setChecked(false);
        dollar_350_checkbox.setChecked(false);
        window_size_up_to_132_ui_edit_text.setText("");

        oversize_larger_than_132_ui_checkbox.setChecked(false);
        dollar_checkbox.setChecked(false);
        oversize_larger_than_132_ui_edit_text.setText("");

        custom_window_checkbox.setChecked(false);

        frame_removal_checkbox.setChecked(false);
        dollar_60_checkbox.setChecked(false);
        frame_removal_edit_text.setText("");

        exterior_trim_checbox.setChecked(false);
        exterior_trim_edit_text.setText("");

        rip_and_install_jamb_extension_checkbox.setChecked(false);
        rip_and_install_jamb_extension_edit_text.setText("");

        drop_still_to_meet_egress_checkbox.setChecked(false);
        drop_still_to_meet_egress_edit_text.setText("");

        dump_fee_20_per_window_checkbox.setChecked(false);
        dump_fee_20_per_window_edit_text.setText("");

        was_lead_testing_performed_yes_checkbox.setChecked(false);
        was_lead_testing_performed_no_checkbox.setChecked(false);

        year_built_in_edit_text.setText("");

        custom_labor_cost_edit_text.setText("");
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

    class AddPageTask extends AsyncTask<Void, Void, Void>{

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
