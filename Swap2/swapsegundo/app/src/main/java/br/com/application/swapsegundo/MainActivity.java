package br.com.application.swapsegundo;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private Spinner primeiroSpinner, segundoSpinner;
    private Button btnConverter;
    private TextView txtResposta;
    private EditText inputMontante;
    private ProgressBar progressBar;
    private Double val_cotacao = 0.0;
    private Conversao conversao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        primeiroSpinner = (Spinner) findViewById(R.id.primeiroSpinner);
        segundoSpinner = (Spinner) findViewById(R.id.segundoSpinner);
        btnConverter = (Button) findViewById(R.id.btnConverter);
        txtResposta = (TextView) findViewById(R.id.txtResposta);
        inputMontante = (EditText) findViewById(R.id.inputMontante);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        View rootView = findViewById(android.R.id.content);

        configSpinner();

        converterMoedas();

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Ocultar o teclado
                hideKeyboard();
            }
        });
    }

    private void converterMoedas() {
        btnConverter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(inputMontante.getText().toString())){
                    Moedas moedaSelecionada = (Moedas) primeiroSpinner.getSelectedItem();
                    String codigoMoeda = moedaSelecionada.getCode();
                    Moedas SegundamoedaSelecionada = (Moedas) segundoSpinner.getSelectedItem();
                    String SegundocodigoMoeda = SegundamoedaSelecionada.getCode();
                    if(codigoMoeda.equals(SegundocodigoMoeda)){
                        txtResposta.setText("Consulta inválida.");
                    }else{
                        String consulta = codigoMoeda+"-"+SegundocodigoMoeda;
                        progressBar.setVisibility(View.VISIBLE);
                        getAwesome(consulta);
                    }
                }
                hideKeyboardClickBtn(MainActivity.this, inputMontante);
            }
        });
    }

    private void getAwesome(String consulta) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Service.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service service = retrofit.create(Service.class);
        Call<Conversao> request =  service.GetAPI(consulta);

        request.enqueue(new Callback<Conversao>() {
            @Override
            public void onResponse(Call<Conversao> call, Response<Conversao> response) {
                if(!response.isSuccessful()){
                    Log.i(TAG, "Erro: " + response.code());
                }else{
                    conversao = response.body();
                    apresentarResposta(consulta);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Conversao> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Erro", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Erro: " + t.getMessage());
                t.printStackTrace();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void apresentarResposta(String consulta) {
        switch (consulta) {
            case "BRL-USD":
                double brlUsdValue = Double.parseDouble(inputMontante.getText().toString()) * Double.parseDouble(conversao.getBRLUSD().getHigh());
                txtResposta.setText(String.valueOf(
                        inputMontante.getText().toString()) + " BRL = " +
                        String.format("%.2f", brlUsdValue) + " USD");
                break;
            case "BRL-EUR":
                double brlEurValue = Double.parseDouble(inputMontante.getText().toString()) * Double.parseDouble(conversao.getBRLEUR().getHigh());
                txtResposta.setText(String.valueOf(
                        inputMontante.getText().toString()) + " BRL = " +
                        String.format("%.2f", brlEurValue) + " EUR");
                break;
            case "USD-BRL":
                double usdBrlValue = Double.parseDouble(inputMontante.getText().toString()) * Double.parseDouble(conversao.getUSDBRL().getHigh());
                txtResposta.setText(String.valueOf(
                        inputMontante.getText().toString()) + " USD = " +
                        String.format("%.2f", usdBrlValue) + " BRL");
                break;
            case "USD-EUR":
                double usdEurValue = Double.parseDouble(inputMontante.getText().toString()) * Double.parseDouble(conversao.getUSDEUR().getHigh());
                txtResposta.setText(String.valueOf(
                        inputMontante.getText().toString()) + " USD = " +
                        String.format("%.2f", usdEurValue) + " EUR");
                break;
            case "EUR-BRL":
                double eurBrlValue = Double.parseDouble(inputMontante.getText().toString()) * Double.parseDouble(conversao.getEURBRL().getHigh());
                txtResposta.setText(String.valueOf(
                        inputMontante.getText().toString()) + " EUR = " +
                        String.format("%.2f", eurBrlValue) + " BRL");
                break;
            case "EUR-USD":
                double eurUsdValue = Double.parseDouble(inputMontante.getText().toString()) * Double.parseDouble(conversao.getEURUSD().getHigh());
                txtResposta.setText(String.valueOf(
                        inputMontante.getText().toString()) + " EUR = " +
                        String.format("%.2f", eurUsdValue) + " USD");
                break;
            default:
                Toast.makeText(this, "Erro", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    private void configSpinner() {
        Moedas BRL = new Moedas("BRL", "BRL / Real Brasileiro", R.drawable.brasilicon);
        Moedas USD = new Moedas("USD", "USD / Dólar", R.drawable.euaicon);
        Moedas EUR = new Moedas("EUR", "EUR / Euro", R.drawable.europaicon);
        ArrayList<Moedas> moedas = new ArrayList<Moedas>();
        ArrayList<Moedas> moedas2 = new ArrayList<Moedas>();
        moedas.add(BRL); moedas.add(USD); moedas.add(EUR);
        moedas2.add(USD); moedas2.add(EUR); moedas2.add(BRL);
        Adapter_item_spinner adapter = new Adapter_item_spinner(this, moedas);
        Adapter_item_spinner adapter2 = new Adapter_item_spinner(this, moedas2);
        primeiroSpinner.setAdapter(adapter);
        segundoSpinner.setAdapter(adapter2);
    }

    public static void hideKeyboardClickBtn(Context context, View editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }


}