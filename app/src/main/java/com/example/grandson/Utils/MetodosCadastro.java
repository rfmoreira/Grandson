package com.example.grandson.Utils;

import android.content.Context;
import android.util.Log;
import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public  class MetodosCadastro {

    // METEDO PARA VALIDACAO DE E-MAIL.
    public static boolean isEmail(String email){
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

    // METODO VALIDACAO CARTAO DE CREDITO
    public static boolean isCredCard(String cartao){

        String numCartao = cartao;

        String numString;

        int soma = 0;

    //Cartão com seqüências de caracteres menor ou igual a 15 dígitos

        if (numCartao.length() <= 15)

        {
            for(int i = 0; i <= numCartao.length(); i++)

            {
                numString = (numCartao.substring(i,i+1));

                if (i % 2 == 0)

                {
                    soma += (Integer.parseInt(numString) * 1);
                }
                else
                {
                    if ((Integer.parseInt(numString) * 2) > 9)
                    {
                        soma += ((Integer.parseInt(numString) * 2) - 9);
                    }
                    else
                    {
                        soma += ((Integer.parseInt(numString) * 2));
                    }
                }
            }
        }

        //Cartão com seqüências de caracteres maior ou igual a 16 dígitos

        if (numCartao.length() >= 16)
        {
            for(int i = 0; i <= numCartao.length(); i++)

            {
                numString = (numCartao.substring(i,i+1));
                if (i % 2 == 0)
                {
                    if ((Integer.parseInt(numString) * 2) > 9)

                    {
                        soma += ((Integer.parseInt(numString) * 2) - 9);

                    }
                    else
                    {
                        soma += ((Integer.parseInt(numString) * 2));

                    }
                }
                else
                {
                    soma +=(Integer.parseInt(numString) * 1);
                }
            }
        }

        if (soma % 10 == 0)

        {
            //txtStatus.set_Text("Cartão valido!");
            return true;
        }

        else

        {
            //txtStatus.set_Text("Cartão invalido!");
            return false;
        }

    }


    // METODO VALIDACAO DE CEP
    public static  boolean isCEP(String cep){
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

    // METODO VALIDACAO DE CPF
    public static boolean isCPF(String cpf){
        boolean result = false;
        int[] numerosCpf = new int[11];
        int quantidadeZeros = 11 - cpf.length();
        int soma = 0;

        //validar se existe algum caracter que não seja dígito
        for(int i = 0; i < cpf.length(); i++){
            if(!Character.isDigit(cpf.charAt(i))){
                return result;
            }
        }

        for(int i = 0; i < quantidadeZeros; i++){
            numerosCpf[i] = 0;
        }

        int j = 0;
        for(int i = quantidadeZeros; i < 11; i++){
            numerosCpf[i] = Integer.parseInt(cpf.substring(j,j+1));
            j++;
        }

        //valida se a quantidade de dígitos informados é maior de 11
        if(cpf.length() > 11){
            return result;
        }

        //valida se todos os números são iguais
        if(numerosCpf[0] == numerosCpf[1] &&
                numerosCpf[1] == numerosCpf[2] &&
                numerosCpf[2] == numerosCpf[3] &&
                numerosCpf[3] == numerosCpf[4] &&
                numerosCpf[4] == numerosCpf[5] &&
                numerosCpf[5] == numerosCpf[6] &&
                numerosCpf[6] == numerosCpf[7] &&
                numerosCpf[7] == numerosCpf[8] &&
                numerosCpf[8] == numerosCpf[9] &&
                numerosCpf[9] == numerosCpf[10]){
            return result;
        }

        //calculo do primeiro dígito
        for(int i = 0; i < numerosCpf.length-2; i++){
            soma += numerosCpf[i] * (10-i);
        }

        int primeiroDigito = (11-(soma%11));

        //Cálculo do segundo dígito
        soma = 0;
        for(int i = 0; i < numerosCpf.length-1; i++){
            soma += numerosCpf[i] * (11-i);
        }

        int segundoDigito = (11-(soma%11));

        result = (primeiroDigito == numerosCpf[9]) && (segundoDigito ==  numerosCpf[10]);

        return result;
    }

    // METODO PARA VERIFICACAO DE CAMPOS VAZIOS
    public static boolean isCampoVazio(String val){
       boolean result = (val.toString().isEmpty() || val.trim().isEmpty());
       return  result;
    }

    //METODO PARA RETIRADA DE MASCARAS
    public static String unMask(String value){
        String result = value.trim().replaceAll("[().\\s,-]+","");
        return result;
    }



}
