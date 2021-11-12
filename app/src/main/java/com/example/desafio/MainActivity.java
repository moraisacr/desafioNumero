//Augusto Cesar Rezende de Morais

package com.example.desafio;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
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
import org.w3c.dom.Text;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
//DECLARACAO DE VARIAVEL GLOBAL
    private RequestQueue queue;
    private int acerto;
    public int recebeValue = 0;
    EditText digitar;
    Button enviar;
    TextView tela;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        components();
        requestUser();

    }
//INICIALIZACAO DOS COMPONENTES
    private void components(){
        queue = Volley.newRequestQueue(this);
        digitar = (EditText) findViewById(R.id.digitar);
        enviar = (Button) findViewById(R.id.enviar);
        tela = (TextView) findViewById(R.id.tela);

    }
//CHAMADA DA API
    private void requestUser(){
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                U.BASE_URL,
                null,
                //TRATAMENTO DE EXCECAO
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Value value = new Value(
                                    response.getInt("value")
                            );setComponents(value);
                            recebeValue = value.getValue();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }

        );
        queue.add(request);
    }
 
    private void setComponents(Value value){
        acerto = value.getValue();
    }
    //METODO DO BOTAO
    public void selecionarBotao(View v) {
        int x = new Random().nextInt(300);
        TextView s = findViewById(R.id.tela);
        s.setText("0");
    }

    //CHAMANDO BOTAO ENVIAR
    public void botaoEnviar(View view) {

        TextView digit = findViewById(R.id.digitosNumero);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = digitar.getText().toString();

                tela.setText(name);
                TextView s = findViewById(R.id.digitosNumero);
                //DEFINICAO DE DIGITOS
                if (Integer.parseInt(name) > 0 && Integer.parseInt(name) < 10) {
                    digit.setText("1/3");
                } else if (Integer.parseInt(name) >= 10 && Integer.parseInt(name) < 100) {
                    digit.setText("2/3");
                } else if (Integer.parseInt(name) >= 100 && Integer.parseInt(name) <= 300) {
                    digit.setText("3/3");
                } else {
                    digit.setText("0/3");
                }

                TextView result = findViewById(R.id.resultado);
                //MENSAGENS DA TELA
                if(Integer.parseInt(name)  > recebeValue && Integer.parseInt(name)  <=300){
                    result.setText("É menor");
                }else if(Integer.parseInt(name) <recebeValue && Integer.parseInt(name)  >=0){
                    result.setText("É maior");
                }else if(Integer.parseInt(name) == recebeValue){
                    result.setText("Acertou!");
                }else if((Integer.parseInt(name) <0 || Integer.parseInt(name)  >300)){
                    result.setText("Erro");
                }
            }
        });

    }

}//FIM DO PROGRAMA

