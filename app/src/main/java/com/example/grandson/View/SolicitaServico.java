package com.example.grandson.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.grandson.Api.RetrofitClientCEP;
import com.example.grandson.Api.RetrofitClientGrandson;
import com.example.grandson.Model.Cliente;
import com.example.grandson.Model.FormCadastrarServico;
import com.example.grandson.Model.Cep;
import com.example.grandson.Model.ServicosAgendados;
import com.example.grandson.R;
import com.example.grandson.Services.RetrofitServiceCEP;
import com.example.grandson.Services.RetrofitServiceGrandson;
import com.example.grandson.Utils.MetodosCadastro;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SolicitaServico extends FragmentActivity {


    private SupportMapFragment supportMapFragment;
    private Location location;
    private LocationManager locationManager;
    private FusedLocationProviderClient client;
    private Address address;

    private Cep cep;
    private TextInputLayout textInputData,textInputTime,textInputValor
            ,textInputCep,textLogradouro
            ,textInputNumero,textInputComplemento
            ,textInputBairro,textInputEstado;
    DatePickerDialog pickerData;
    TimePickerDialog pickerTime;
    private Spinner spinnerHoras;
    private Button btSolicitar;

    private String data;
    private String hora;
    private double qtdHoras;
    private double valor;

    private FormCadastrarServico formCadastrarServico;

    private int idParceiro;
    private String auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicita_servico);

        SharedPreferences pref = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        auth = pref.getString("token","");
        idParceiro = getIntent().getExtras().getInt("idParceiro",1);

        textInputData = (TextInputLayout) findViewById(R.id.textInputData);
        textInputTime = (TextInputLayout) findViewById(R.id.textInputTime);
        //textInputHoras = (TextInputLayout) findViewById(R.id.textInputHoras);
        textInputValor = (TextInputLayout) findViewById(R.id.textInputValor);
        textInputCep = (TextInputLayout) findViewById(R.id.textInputCep);
        textLogradouro = (TextInputLayout) findViewById(R.id.textLogradouro);
        textInputNumero = (TextInputLayout) findViewById(R.id.textInputNumero);
        textInputComplemento = (TextInputLayout) findViewById(R.id.textInputComplemento);
        textInputBairro = (TextInputLayout) findViewById(R.id.textInputBairro);
        textInputEstado = (TextInputLayout) findViewById(R.id.textInputEstado);
        spinnerHoras = (Spinner) findViewById(R.id.spinnerHoras);
        btSolicitar = (Button) findViewById(R.id.btSolicitar);

        getEnd();


        List<String> horas = getHoras();
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<>(this,R.layout.spinner_select_layout,horas);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinnerHoras.setAdapter(adapter);


        spinnerHoras.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String itemSelected = adapterView.getItemAtPosition(i).toString();
                qtdHoras = Double.parseDouble(itemSelected.replace(":","."));
                String[] split = itemSelected.split(":");
                int minutos = ((Integer.parseInt(split[0]) * 60) + Integer.parseInt(split[1]));
                valor = minutos * 0.4166666666666667;
                String v = String.format("%.2f", valor);
                textInputValor.getEditText().setText("R$ " + v.replace(".",","));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //Verficacao do foco do campo CEP para fazer consulta
        textInputCep.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){

                }else {
                    String cep = textInputCep.getEditText().getText().toString();
                    cep = MetodosCadastro.unMask(cep);
                    if (MetodosCadastro.isCEP(cep)){
                        consultarCEP(cep);
                    }else {
                        textInputCep.getEditText().setError("CEP Invalido");
                        textInputCep.requestFocus();
                    }
                }
            }
        });


        //MASCARA CEP
        SimpleMaskFormatter simpleMaskCep = new SimpleMaskFormatter("NN.NNN-NNN");
        MaskTextWatcher maskCep = new MaskTextWatcher(textInputCep.getEditText(), simpleMaskCep);
        textInputCep.getEditText().addTextChangedListener(maskCep);

        //MASCARA VALOR
        /*SimpleMaskFormatter simpleMaskValor = new SimpleMaskFormatter("R$ AAAAA");
        MaskTextWatcher maskValor = new MaskTextWatcher(textInputValor.getEditText(), simpleMaskValor);
        textInputValor.getEditText().addTextChangedListener(maskValor);*/

        textInputValor.getEditText().setText("R$ 25,00");
        textInputValor.getEditText().setTextColor(ContextCompat.getColor(this, R.color.black));

        // EXIBIR CALENDARIO E PEGAR DATA
        textInputData.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                pickerData = new DatePickerDialog(SolicitaServico.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                textInputData.getEditText().setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                data = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                            }
                        }, year, month, day);
                pickerData.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                pickerData.show();
            }
        });

        // EXIBIR RELATOGIO E PEGAR HORA DO SERVICO
        textInputTime.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // time picker dialog
                pickerTime = new TimePickerDialog(SolicitaServico.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                Calendar cldr = Calendar.getInstance();
                                int  hour = sHour;
                                int minutes = sMinute;
                                cldr.set(0, 0, 0, hour,minutes );
                                String time = hour+":"+minutes;
                                SimpleDateFormat f24 =  new SimpleDateFormat("HH:mm");
                                try {
                                    Date date = f24.parse(time);
                                    textInputTime.getEditText().setText(f24.format(date));
                                    hora = f24.format(date);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, 00, 00, true);
                pickerTime.show();
            }
        });

        btSolicitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                formatSolicitacao();
            }
        });

    }




    private void getCurrentLocation() {
        Log.i("getCurrent", "Entrou");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED ) {
            Log.i("getCurrent", "Entrou no if");
            Task<Location> task = client.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location loc) {
                         location =loc;
                    if(location != null ){
                        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(GoogleMap googleMap) {
                                //Log.i("getCurrent","Entrou no suport  "+location.getLatitude());
                                LatLng latLng = new LatLng(location.getLatitude()
                                        ,location.getLongitude());
                                try {
                                    address = getAddress(location.getLatitude(),location.getLongitude());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                MarkerOptions options = new MarkerOptions().position(latLng).title(address.getAddressLine(0));
                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
                                googleMap.isMyLocationEnabled();
                                googleMap.addMarker(options);
                            }
                        });
                    }
                }
            });
        }else {
           /* if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent, 1);
            }else{**/
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        44);
                Log.i("getCurrent","Entrou no else");
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 44){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getCurrentLocation();
            }
        }
    }


    public Address getAddress(double latitude, double longitude) throws IOException {
        Geocoder geocoder = new Geocoder(getApplicationContext());
        Address address = null;
        List<Address> addresses;

        addresses = geocoder.getFromLocation(latitude,longitude,1);
        if (addresses.size() >0){
                address = addresses.get(0);
        }
        Toast.makeText(this, "Address: " + address.getCountryName()
                +' '+ address.getFeatureName()
                +' '+ address.getAdminArea()
                +' '+ address.getAddressLine(0)

                , Toast.LENGTH_SHORT).show();
        Log.i("getCurrent","Address: " + address.getCountryName()
                +' '+ address.getFeatureName()
                +' '+ address.getAdminArea()
                +' '+ address.getAddressLine(0));
        return address;
    };


    private void consultarCEP(String sCep) {

        //instanciando a interface
        RetrofitServiceCEP restService = RetrofitClientCEP.getService();

        //passando os dados para consulta
        Call<Cep> call = restService.consultarCEP(sCep);

        //colocando a requisição na fila para execução
        call.enqueue(new Callback<Cep>() {
            @Override
            public void onResponse(Call<Cep> call, Response<Cep> response) {
                if (response.isSuccessful()) {
                    cep = response.body();
                    if (!cep.getErro()){

                            textLogradouro.getEditText().setText(cep.getLogradouro());
                            textInputComplemento.getEditText().setText(cep.getComplemento());
                            textInputBairro.getEditText().setText(cep.getBairro());
                            textInputEstado.getEditText().setText(cep.getLocalidade()+ " - "+cep.getUf());
                        if(textLogradouro.getEditText().getText().toString().isEmpty()){
                        }else {

                        }

                        Toast.makeText(getApplicationContext(), "CEP consultado com sucesso" , Toast.LENGTH_LONG).show();
                    }else{
                        textInputCep.getEditText().setError("CEP Invalido");
                    }
                }
            }

            @Override
            public void onFailure(Call<Cep> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Ocorreu um erro ao tentar consultar o CEP. Erro: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    private List<String> getHoras(){
        List<String> horas = Arrays.asList("01:00","01:30"
                ,"02:00","02:30"
                ,"03:00","03:30"
                ,"04:00","04:30"
                ,"05:00","05:30"
                ,"06:00"
        );

     return horas;
    }


    public String formatDateTime(){

        if(!textInputData.getEditText().toString().isEmpty() && !textInputTime.getEditText().toString().isEmpty()){
            String[] splitDate = data.split("/");
            String dia = splitDate[0];
            String mes = splitDate[1];
            String ano = splitDate[2];
            String time = hora;
            String dateTime = ano + "-" + mes + "-" + dia +"T"+time+":00";
            Log.i("Data",dateTime);
            //Toast.makeText(this, splitDate.length, Toast.LENGTH_SHORT).show();
            return dateTime;

        }else {
            Log.i("Data","Vazia");
            return "";
        }

    }

    private void formatSolicitacao(){

        String dateTime = formatDateTime();
        //int idCliente = 1;
        //int idParceiro = 1;
        int cep = Integer.parseInt(MetodosCadastro.unMask(textInputCep.getEditText().getText().toString()));
        String bairro = textInputBairro.getEditText().getText().toString();
        String complemento = textInputComplemento.getEditText().getText().toString();
        String logradouro =textLogradouro.getEditText().getText().toString();
        String estado = textInputEstado.getEditText().getText().toString();


        int numero = Integer.parseInt(MetodosCadastro.unMask(textInputNumero.getEditText().getText().toString()));
        int quantidadeDeHoras = 1;


        formCadastrarServico = new FormCadastrarServico(cep,bairro,complemento,logradouro,estado,dateTime,idParceiro,numero,quantidadeDeHoras,valor);
        solicitarServico(formCadastrarServico);
    }

    private void solicitarServico(FormCadastrarServico formCadastrarServico) {

        //instanciando a interface
        RetrofitServiceGrandson restService = RetrofitClientGrandson.getService();

        //passando os dados para consulta
        Call<ServicosAgendados> call = restService.cadastrarServico("Bearer " + auth, formCadastrarServico);

        Gson gson = new Gson();
        String json = gson.toJson(formCadastrarServico);
        Log.i("Json", json);

        call.enqueue(new Callback<ServicosAgendados>() {
            @Override
            public void onResponse(Call<ServicosAgendados> call, Response<ServicosAgendados> response) {

                if(response.isSuccessful()){
                    ServicosAgendados servicosAgendados = response.body();
                    Log.i("Cadastro","Sucesso\n" + servicosAgendados.toString() );


                }else {
                    Log.i("Cadastro","Erro");
                }

            }

            @Override
            public void onFailure(Call<ServicosAgendados> call, Throwable t) {
                Log.i("Cadastro","Falha" + t.getMessage());
            }
        });
    }

    private void getEnd(){
        Log.i("Auth ",auth);
        //Instanciando a interface
        RetrofitServiceGrandson restService = RetrofitClientGrandson.getService();
        //Passando os dados para consulta
        Call<Cliente> call = restService.getPerfilCliente("Bearer "+auth);
        call.enqueue(new Callback<Cliente>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onResponse(Call<Cliente> call, Response<Cliente> response) {

                if(response.isSuccessful()){
                    Cliente cliente = response.body();

                    textInputCep.getEditText().setText(String.valueOf(cliente.getEndereco().getCep()));
                    textLogradouro.getEditText().setText(cliente.getEndereco().getEndereco());
                    textInputBairro.getEditText().setText(cliente.getEndereco().getCidade());
                    textInputNumero.getEditText().setText(String.valueOf(cliente.getEndereco().getNumero()));
                    textInputComplemento.getEditText().setText(cliente.getEndereco().getComplemento());


                }else {
                    Log.i("Erro Requisição ",response.message());
                }
            }

            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {

                Log.i("Erro Servidor",t.getMessage());

            }
        });


    }

}