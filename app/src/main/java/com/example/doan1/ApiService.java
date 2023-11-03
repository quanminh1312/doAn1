package com.example.doan1;
import androidx.annotation.Nullable;

import retrofit2.http.Path;
import com.example.doan1.model.Chapter.Chapter;
import com.example.doan1.model.Manga.Manga;
import com.example.doan1.model.Tag.Tag;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
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
    Call<JsonObject>getMangaId(@Query("offset") int offset
            , @Query("title") String title
            , @Query("authorOrArtist") String author
            , @Query("year") int year
            , @Query("includedTags[]") ArrayList<Tag> included
            , @Query("excludedTags[]") ArrayList<Tag> excluded
            , @Query("status[]")ArrayList status
            , @Query("ids[]") ArrayList ids
            , @Query("includes[]") ArrayList includes);
    @GET("author/{id}")
    Call<JsonObject>getAuthor(@Path("id") String authorId);
}
