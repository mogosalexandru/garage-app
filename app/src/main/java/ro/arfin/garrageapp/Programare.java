package ro.arfin.garrageapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import ro.arfin.garrageapp.Database.CarModelObject;
import ro.arfin.garrageapp.Database.DatabaseOperations;

public class Programare extends AppCompatActivity {

    private DatabaseOperations databaseOperations = new DatabaseOperations();
    private ArrayList<CarModelObject> modelContent = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programare);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        modelContent = (ArrayList<CarModelObject>) getIntent().getSerializableExtra("modelContent");

        for (int i = 0; i < modelContent.size(); i++) {
            Button myButton = new Button(Programare.this);

            myButton.setText(modelContent.get(i).getModelName().toString());

            System.out.println(modelContent.get(i).getModelName().toString());

            LinearLayout mLinearLayout = (LinearLayout) findViewById(R.id.viewCarModel);
            LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            mLinearLayout.addView(myButton, mLayoutParams);
        }

        //enable top back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button mButonPlus = (Button) findViewById(R.id.butonPlus);
        mButonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Programare.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_addcar, null);

                final EditText mModel = (EditText) mView.findViewById(R.id.car_modelContent);
                final Spinner mMotorizare = (Spinner) mView.findViewById(R.id.car_motorizareContent);
                Button mAdauga = (Button) mView.findViewById(R.id.button_add_car);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                mAdauga.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!mModel.getText().toString().isEmpty()) {
//                            Button myButton = new Button(Programare.this);
//                            myButton.setText(mModel.getText().toString());
//
//                            LinearLayout mLinearLayout = (LinearLayout) findViewById(R.id.viewCarModel);
//                            LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                            mLinearLayout.addView(myButton, mLayoutParams);
//
//                            databaseOperations.writeNewModel(mModel.getText().toString(),mMotorizare.getSelectedItem().toString());
                            dialog.dismiss();

                            AlertDialog.Builder mBuilder = new AlertDialog.Builder(Programare.this);
                            View mView = getLayoutInflater().inflate(R.layout.dialog_addoperation, null);

                            mBuilder.setView(mView);
                            final AlertDialog dialog = mBuilder.create();
                            dialog.show();


                        } else {
                            Toast.makeText(Programare.this,
                                    "Completati toate campurile.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(Programare.this,
                                "Succes.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        Button mButonMinus = (Button) findViewById(R.id.butonMinus);
        mButonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Programare.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_removecar, null);

                final EditText mContent = (EditText) mView.findViewById(R.id.contentSterge);
                Button mSterge = (Button) mView.findViewById(R.id.butonSterge);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                mSterge.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!mContent.getText().toString().isEmpty()) {
                            String mButtonId = "b" + mContent.getText().toString();

                            LinearLayout mLinearLayout = (LinearLayout) findViewById(R.id.viewCarModel);
                            //mLinearLayout.removeView(R.id.);

                            dialog.dismiss();
                        } else {
                            Toast.makeText(Programare.this,
                                    "Completati toate campurile.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

        @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;

    }
}
