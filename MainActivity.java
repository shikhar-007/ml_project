package com.example.diabetesprediction;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText PREGNANCIES, GLUCOSE, BP, SKIN, INSULIN, BMI, PEDIGREE, AGE;
    Button predict;
    TextView result;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PREGNANCIES = findViewById(R.id.PREGNANCIES);
        GLUCOSE = findViewById(R.id.GLUCOSE);
        BP = findViewById(R.id.BP);
        SKIN = findViewById(R.id.SKIN);
        INSULIN = findViewById(R.id.INSULIN);
        BMI = findViewById(R.id.BMI);
        PEDIGREE = findViewById(R.id.PEDIGREE);
        AGE = findViewById(R.id.Age);
        predict = findViewById(R.id.predict);
        result = findViewById(R.id.result);

        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "http://my-json-feed";

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONObject jsonObject = new JSONObject((Map) response);
                                    String data = jsonObject.getString("placement");
                                    if(data.equals("1")){
                                        result.setText("Patient is Diabetic!");
                                    }else{
                                        result.setText("Patient is Non-diabetic.");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Log.d("response","recived an error");
                                result.setText("That didn't work out check problems");


                            }
                        }) {

                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("PREGNANCIES", PREGNANCIES.getText().toString());
                        params.put("GLUCOSE", GLUCOSE.getText().toString());
                        params.put("SKIN", SKIN.getText().toString());
                        params.put("INSULIN", INSULIN.getText().toString());
                        params.put("BMI", BMI.getText().toString());
                        params.put("PEDIGREE", PEDIGREE.getText().toString());
                        params.put("AGE", AGE.getText().toString());

                        return params;
                    }

                };

                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                Request<?> stringRequest;
                stringRequest = null;
                queue.add(stringRequest);


            }
        });

    }
}