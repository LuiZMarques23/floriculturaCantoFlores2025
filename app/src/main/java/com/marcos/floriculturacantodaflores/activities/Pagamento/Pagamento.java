package com.marcos.floriculturacantodaflores.activities.Pagamento;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;


import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import com.marcos.floriculturacantodaflores.databinding.ActivityPagamentoBinding;
import com.marcos.floriculturacantodaflores.interfaceMercadoPago.ComunicacaoServidorMP;
import com.marcos.floriculturacantodaflores.model.DB;
import com.mercadopago.android.px.configuration.AdvancedConfiguration;
import com.mercadopago.android.px.core.MercadoPagoCheckout;
import com.mercadopago.android.px.model.Payment;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Pagamento extends AppCompatActivity {

    ActivityPagamentoBinding binding;
    private String tamanho_calcado;
    private String nome;
    private String preco;

    private final String PUBLIC_KEY = "APP_USR-fc235ba5-c2b6-4cf6-a943-876a68755f5f";
    private final String ACCESS_TOKEN = "APP_USR-3179623366446893-070414-4883123c4ef6893e6ff765efa26d2fa7-363385476";

    DB db = new DB();
    private String usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private String pedidoID = UUID.randomUUID().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPagamentoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tamanho_calcado = getIntent().getExtras().getString("tamanho_calcado");
        nome = getIntent().getExtras().getString("nome");
        preco = getIntent().getExtras().getString("preco");


        binding.btFazerPagamento.setOnClickListener(v -> {

            String bairro = binding.editBairro.getText().toString();
            String rua_numero = binding.editRuaNumero.getText().toString();
            String cidade_estado = binding.editCidadeEstado.getText().toString();
            String celular = binding.editCelular.getText().toString();

            if (bairro.isEmpty() || rua_numero.isEmpty() || cidade_estado.isEmpty() || celular.isEmpty()){
                Snackbar snackbar = Snackbar.make(v,"Preencha todos os campos!",Snackbar.LENGTH_SHORT);
                snackbar.setBackgroundTint(Color.RED);
                snackbar.setTextColor(Color.WHITE);
                snackbar.show();
            }else {
                criarJsonObject();
            }
        });
    }

    private void criarJsonObject(){

        JsonObject dados = new JsonObject();

        //Primeiro Item
        JsonArray item_lista = new JsonArray();
        JsonObject item;

        //Segundo Item
        JsonObject email = new JsonObject();

        //Terceiro Item - Excluir formas de pagamento - nesse caso vai ser o boleto
        /*JsonObject excluir_tipo_pagamento = new JsonObject();
        JsonArray ids = new JsonArray();
        JsonObject removerBoleto = new JsonObject();*/

        item = new JsonObject();
        item.addProperty("title",nome);
        item.addProperty("quantity",1);
        item.addProperty("currency_id","BRL");
        item.addProperty("unit_price",Double.parseDouble(preco));
        item_lista.add(item);

        dados.add("items",item_lista);

        String emailUsuario = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        email.addProperty("email",emailUsuario);
        dados.add("payer",email);

        /*removerBoleto.addProperty("id","ticket");
        ids.add(removerBoleto);
        excluir_tipo_pagamento.add("excluded_payment_types",ids);
        excluir_tipo_pagamento.addProperty("installments",2);

        dados.add("payment_methods",excluir_tipo_pagamento);*/

        Log.d("j",dados.toString());
        criarPreferenciaPagamento(dados);
    }

    private void criarPreferenciaPagamento(JsonObject dados){

        String site = "https://api.mercadopago.com";
        String url = "/checkout/preferences?access_token=" + ACCESS_TOKEN;

        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(site)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ComunicacaoServidorMP conexao_pagamento = retrofit.create(ComunicacaoServidorMP.class);
        Call<JsonObject> request = conexao_pagamento.enviarPagamento(url,dados);
        request.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                  String preferenceId = response.body().get("id").getAsString();
                  criarPagamento(preferenceId);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    private void criarPagamento(String preferenceId){

        final AdvancedConfiguration advancedConfiguration =
                new AdvancedConfiguration.Builder().setBankDealsEnabled(false).build();
        new MercadoPagoCheckout
                .Builder(PUBLIC_KEY, preferenceId)
                .setAdvancedConfiguration(advancedConfiguration).build()
                .startPayment(this, 123);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 123) {
            if (resultCode == MercadoPagoCheckout.PAYMENT_RESULT_CODE) {

               final Payment pagamento = (Payment) data.getSerializableExtra(MercadoPagoCheckout.EXTRA_PAYMENT_RESULT);
               respostaMercadoPago(pagamento);

            } else if (resultCode == RESULT_CANCELED) {

            } else {

            }
        }
    }

    private void respostaMercadoPago(Payment pagamento){

        String status = pagamento.getPaymentStatus();

        String bairro = binding.editBairro.getText().toString();
        String rua_numero = binding.editRuaNumero.getText().toString();
        String cidade_estado = binding.editCidadeEstado.getText().toString();
        String celular = binding.editCelular.getText().toString();

        String endereco = "Bairro: " + bairro + " " + " Rua e Número: " + " " + rua_numero + " Cidade e Estado: " + " " + cidade_estado;
        String status_pagamento = "Status de Pagamento: " + " " + "Pagamento Aprovado";
        String status_entrega = "Status de Entrega: " + " " + "Em andamento";

        String nomeProduto = "Nome: " + " " + nome;
        String precoProduto = "Preço: " + " " + preco;
        String tamanho = "Seu Código: " + " " + tamanho_calcado;
        String celular_usuario = "Celular: " + " " + celular;

        if (status.equalsIgnoreCase("approved")){
            Snackbar snackbar = Snackbar.make(binding.container,"Sucesso ao fazer o pagamento",Snackbar.LENGTH_SHORT);
            snackbar.setBackgroundTint(Color.GREEN);
            snackbar.setTextColor(Color.WHITE);
            snackbar.show();
            db.salvarDadosPedidosUsuario(endereco,celular_usuario,nomeProduto,precoProduto,tamanho,status_pagamento,status_entrega,pedidoID,usuarioID);
            db.salvarDadosPedidosAdmin(endereco,celular_usuario,nomeProduto,precoProduto,tamanho,status_pagamento,status_entrega,pedidoID,usuarioID);

        }else if (status.equalsIgnoreCase("rejected")){
            Snackbar snackbar = Snackbar.make(binding.container,"Erro ao fazer o pagamento",Snackbar.LENGTH_SHORT);
            snackbar.setBackgroundTint(Color.RED);
            snackbar.setTextColor(Color.WHITE);
            snackbar.show();
        }
    }
}