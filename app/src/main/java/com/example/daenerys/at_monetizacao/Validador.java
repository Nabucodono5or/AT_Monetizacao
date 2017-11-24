package com.example.daenerys.at_monetizacao;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by daenerys on 11/21/17.
 */

public class Validador {

    public boolean validaCpf(String cpf){
        boolean isCpfValid = false;
            if(cpf != null && cpf.length() == 14){
                isCpfValid = true;
            }//if
        return isCpfValid;
    }//cpf

    public boolean validaEmail(String mail){
        boolean isEmailValid = false;

        if (mail != null && mail.length() > 0){
            String expressao = "^[\\w\\.-]+@([\\w\\-]+\\.)+[a-z]{2,4}$";
            Pattern pattern = Pattern.compile(expressao, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(mail);
            if(matcher.matches()){
                isEmailValid = true;
            }//if
        }

        return isEmailValid;
    }//validaEmail

    public boolean validaNome(String nome){
        boolean isNomeValid = false;

        if(nome != null && nome.length() > 0){
            String expressao = "^[a-zA-Z-Zàèìòùáéíóúâêîôûãõ\b]+\\s+[a-zA-Z-Zààèìòùáéíóúâêîôûãõ\b]+$";
            Pattern pattern = Pattern.compile(expressao);
            Matcher matcher = pattern.matcher(nome);
            if (matcher.matches()){
                isNomeValid = true;
            }//if
        }//if

        return isNomeValid;
    }//validaNome


    public boolean validaSenha(String s, String conf){
        boolean isSenhaValid = false;

        if(s != null && s.length() > 0){
            if(s.equals(conf)){
                isSenhaValid = true;
            }
        }//if

        return isSenhaValid;
    }//validaSenha
}//class
