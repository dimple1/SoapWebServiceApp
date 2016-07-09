package com.example.indianic.soapwebserviceapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

//This Project for test purpose, How to sync with git
public class MainActivity extends AppCompatActivity {
    private static final String SOAP_ACTION = "http://www.webserviceX.NET/ConvertWeight";
    private static final String METHOD_NAME = "ConvertWeight";
    private static final String NAMESPACE = "http://www.webserviceX.NET/";
    private static final String URL = "http://www.webservicex.net/ConvertWeight.asmx?op=ConvertWeight";

    private TextView textViewCWR;
    private EditText editTextWR;
    private Button buttonWR;

    String TAG = "Response";
    SoapPrimitive resultString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewCWR = (TextView)findViewById(R.id.txt_cwr);
        editTextWR = (EditText) findViewById(R.id.et_wr);
        buttonWR = (Button)findViewById(R.id.btn_wr);

        buttonWR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AsyncCallWR task = new AsyncCallWR();
                task.execute();
            }
        });
    }

    private class AsyncCallWR extends AsyncTask<Void,Void,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i(TAG, "onPreExecute");
        }


        protected String doInBackground(Void... params) {
            Log.i(TAG, "doInBackground");
            CalculateWeight();
            return null;
        }


        protected void onPostExecute(Void aVoid) {
            Log.i(TAG, "onPostExecute");
            Toast.makeText(MainActivity.this, "Response" + resultString.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public String CalculateWeight(){
            SoapObject request = new SoapObject(NAMESPACE,METHOD_NAME);
            request.addProperty("Weight",editTextWR.getText().toString());
            request.addProperty("FromUnit","Kilograms");
            request.addProperty("ToUnit", "Grams");


            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;
            soapEnvelope.setOutputSoapObject(request);

            try{
            HttpTransportSE transport = new HttpTransportSE(URL);

            transport.call(SOAP_ACTION, soapEnvelope);
            resultString = (SoapPrimitive) soapEnvelope.getResponse();

            if(resultString != null){

                Log.e("Response",resultString.toString());
                return resultString.toString();
            }
            }catch (Exception e){
                Log.e("Soap",e.getMessage());
            }
        return resultString.toString();
    }
}

//http://wsdlbrowser.com/soapclient?wsdl_url=http%3A%2F%2Fwww.webservicex.net%2FConvertWeight.asmx%3Fop%3DConvertWeight%26wsdl&function_name=ConvertWeight