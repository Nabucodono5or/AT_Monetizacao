package com.example.daenerys.at_monetizacao;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {
    EditText nome, email, cpf, senha, confSenha;
    Button btnCadstrar;
    ArrayList<EditText> entrada;
    InterstitialAd interstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        interstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
            }
        });

        requestNewInterstitial();

        nome = findViewById(R.id.editTextNome);
        email = findViewById(R.id.editTextEmail);
        cpf = findViewById(R.id.editTextcpf);
        senha = findViewById(R.id.editTextSenha);
        confSenha = findViewById(R.id.editTextConfirmar);
        btnCadstrar = findViewById(R.id.btnCadastrar);

        cpf.addTextChangedListener(MaskUtil.insert(cpf,MaskType.CPF));

        entrada = new ArrayList<>();
        entrada.add(nome);
        entrada.add(email);
        entrada.add(cpf);
        entrada.add(senha);
    }//onCreate

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        interstitialAd.loadAd(adRequest);
    }


    //esse método será usado dentro de um for
    private boolean validaCada(EditText editText, int position){
        Validador validador = new Validador();

        switch (position){
            case 0:
                if(validador.validaNome(nome.getText().toString())) {
                    return true;
                }else {
                    Toast.makeText(this,"O nome e sobrnome não podem permitir caracteres especiais",Toast.LENGTH_SHORT).show();
                }
               break;
            case 1:
                if(validador.validaEmail(email.getText().toString())){
                    return true;
                }else {
                    Toast.makeText(this,"Email invalido",Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                if (validador.validaCpf(cpf.getText().toString())) {
                    return true;
                } else {
                    Toast.makeText(this,"cpf invalido",Toast.LENGTH_SHORT).show();
                }
                break;
            case 3:
                if(validador.validaSenha(senha.getText().toString(), confSenha.getText().toString())){
                    return true;
                }else {
                    Toast.makeText(this,"O campo Senha e Confirmar Senha devem ser iguais",Toast.LENGTH_SHORT).show();
                }
                break;
        }//switch

        return false;
    }//validaCada



     private boolean isNull(){
        for(EditText e : entrada){
            if(e.getText().toString().matches("")){
                return true;
            }//if
        }//for e

        return false;
    }//isNull



    public void onClickSalvar(View view){
        if (isNull()) {
            Toast.makeText(this, "Error: Preencha todos os campos", Toast.LENGTH_SHORT).show();
        }else {
            for (int i = 0; i < entrada.size(); i++) {
                EditText e = entrada.get(i);
                if(!validaCada(e,i)) {
                    e.findFocus();
                    return;
                }//if
            }//for i

            armazenarDados();
            if(interstitialAd.isLoaded()){
                interstitialAd.show();
            }
        }//else
    }//onClickSalvar


    public void armazenarDados(){
        String nomeArq;
        File file;

        try{

            nomeArq = nome.getText().toString();
            file = new File(this.getFilesDir(), nomeArq);

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));

            bufferedWriter.append(nome.getText().toString() +" \n");
            bufferedWriter.append(email.getText().toString() + " \n");
            bufferedWriter.append(senha.getText().toString() + " \n");
            bufferedWriter.append(cpf.getText().toString() + " \n");

            bufferedWriter.flush();
            bufferedWriter.close();

            Toast.makeText(this, "Dados armazenados com sucesso", Toast.LENGTH_SHORT).show();

            limpar();
        }catch (Exception e1){
            Toast.makeText(this,e1.getMessage(),Toast.LENGTH_LONG).show();
        }//catch
    }//armazenaDados


    public void limpar(){
        nome.setText("");
        senha.setText("");
        cpf.setText("");
        email.setText("");
        confSenha.setText("");
    }//onClickLimpar
}//class
