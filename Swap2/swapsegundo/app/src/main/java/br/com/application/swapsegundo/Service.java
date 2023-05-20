package br.com.application.swapsegundo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Service {

    public static final String URL_BASE = "https://economia.awesomeapi.com.br/";

    //CONSULTAR TODAS AS CONVERSÕES
    @GET("last/BRL-USD,BRL-EUR,USD-BRL,USD-EUR,EUR-BRL,EUR-USD")
    Call<Conversao> GetConversao();

    //CONSULTAR A PARTIR DA PREFERÊNCIA DO USUÁRIO
    //SERÁ NECESSÁRIO CONSTRUIR A STRING PARA REALIZAR A CONSULTA
    @GET("last/{consulta}")
    Call<Conversao> GetAPI(@Path("consulta") String consulta);

}
