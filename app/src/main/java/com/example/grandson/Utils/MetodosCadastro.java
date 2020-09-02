package com.example.grandson.Utils;

import android.util.Log;
import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public  class MetodosCadastro {

    // METEDO PARA VALIDACAO DE E-MAIL.
    public static boolean validarEmail(String email){
        if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //CASO E-MAIL SEJA VALIDO.
            Log.i("validarEmail: ", "E-mail valido");
            return true;
        }else {
            // CASO E-MAIL SEJA INVALIDO.
            Log.i("validarEmail: ", "E-mail invalido");
            return false;
        }
    }
    // METODO VALIDACAO DE CEP
    public static  boolean validarCEP(String cep){
        String zipcode = cep;
        Pattern pattern_zipcode = Pattern.compile("(^\\d{5}-\\d{3}|^\\d{2}.\\d{3}-\\d{3}|\\d{8})");
        Matcher matcher = pattern_zipcode.matcher(zipcode);
        if(zipcode.equals("")){
            return false;
        }
        if (!matcher.matches()) {
            return false;
            // CASO CEP SEJA INVALIDO.
        } else {
            return true;
            // CASO CEP ESTEJA NO FORMATO ESPERADO.
        }
    }


    /*
    public static boolean validaCPF(String cpf) {
        cpf = cpf.replace(".","").replace("-","").trim();
        if (cpf == null || cpf.length() != 11 || isCPFPadrao(cpf))
            return false;

        try {
            Long.parseLong(cpf);
        } catch (NumberFormatException e) { // CPF não possui somente números
            return false;
        }

        return calcDigVerif(cpf.substring(0, 9)).equals(cpf.substring(9, 11));
    }
    private static String calcDigVerif(String num) {
        Integer primDig, segDig;
        int soma = 0, peso = 10;
        for (int i = 0; i < num.length(); i++)
            soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;

        if (soma % 11 == 0 | soma % 11 == 1)
            primDig = new Integer(0);
        else
            primDig = new Integer(11 - (soma % 11));

        soma = 0;
        peso = 11;
        for (int i = 0; i < num.length(); i++)
            soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;

        soma += primDig.intValue() * 2;
        if (soma % 11 == 0 | soma % 11 == 1)
            segDig = new Integer(0);
        else
            segDig = new Integer(11 - (soma % 11));

        return primDig.toString() + segDig.toString();
    }*/
}
