package com.example.doan1;

import retrofit2.http.Path;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiService {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiService apiservice = new Retrofit.Builder()
            .baseUrl("https://api.mangadex.org/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);
    @GET("manga/tag")
    Call<JsonObject> getTag();

    @GET("manga/{id}")
    Call<JsonObject> getManga(@Path("id") String mangaId);

    @GET("manga/{id}/aggregate")
    Call<JsonObject> getMangaAggregate(@Path("id") String mangaId);

    @GET("manga/random")
    Call<JsonObject>getRandomManga();
    @GET("cover/{Id}")
    Call<JsonObject>getCover(@Path("Id") String mangaId);
    @GET("at-home/server/{id}")
    Call<JsonObject>getimagechapter(@Path("id") String mangaId);
    @GET("manga")
    Call<JsonObject>getMangaByPage(@Query("offset") Integer offset
//            , @Query("title") String title
//            , @Query("authorOrArtist") String author
//            , @Query("year") Integer year
//            , @Query("includedTags[]") ArrayList<Tag> included
//            , @Query("excludedTags[]") ArrayList<Tag> excluded
//            , @Query("status[]")ArrayList status
//            , @Query("ids[]") ArrayList ids
//            , @Query("includes[]") ArrayList includes
                );
    @GET("manga")
    Call<JsonObject>getMangaByOrder(@QueryMap Map<String, Object> queryParams);
    @GET("manga")
    Call<JsonObject>getMangaByTag (@Query("offset") Integer offset
                ,@Query("includedTags[]") String includedTags);
    @GET("author/{id}")
    Call<JsonObject>getAuthor(@Path("id") String authorId);
}
