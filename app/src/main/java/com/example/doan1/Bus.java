package com.example.doan1;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.doan1.model.Author.Author;
import com.example.doan1.model.Author.AuthorAttributes;
import com.example.doan1.model.Cover.Cover;
import com.example.doan1.model.Cover.CoverAttributes;
import com.example.doan1.model.Manga.Manga;
import com.example.doan1.model.Manga.MangaAttributes;
import com.example.doan1.model.Mangamodel;
import com.example.doan1.model.Relationship;
import com.example.doan1.model.Tag.Tag;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Query;

public class Bus {
    private Context context;
    private Gson gson = new Gson();
    public Bus(Context context) {
        this.context = context;
    }

    //mulTable
    private MutableLiveData<Manga> mangaLiveData = new MutableLiveData<>();

    public MutableLiveData<Manga> getMangaLiveData() {
        return mangaLiveData;
    }
    private  MutableLiveData<List<Tag>> listMutableLiveDataTag = new MutableLiveData<>();

    public MutableLiveData<List<Tag>> getlistMutableLiveDataTag() {
        return listMutableLiveDataTag;
    }
    private MutableLiveData<Cover> coverMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<Cover> getCoverMutableLiveData() {
        return coverMutableLiveData;
    }
    private MutableLiveData<Map<String,List<Map<String,String>>>> chapterListLiveData = new MutableLiveData<>();

    public MutableLiveData<Map<String,List<Map<String,String>>>> getChapterListLiveData() {
        return chapterListLiveData;
    }
    private MutableLiveData<Manga> mangaById = new MutableLiveData<>();

    public MutableLiveData<Manga> getMangaById() {
        return mangaById;
    }
    private MutableLiveData<Author> authorMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<Author> getAuthorMutableLiveData() {
        return authorMutableLiveData;
    }

    private MutableLiveData<List<Manga>> mangaList = new MutableLiveData<>();

    public MutableLiveData<List<Manga>> getMangaList() {
        return mangaList;
    }
    private  MutableLiveData<List<String>> imagechapter = new MutableLiveData<>();

    public MutableLiveData<List<String>> getImagechapter() {
        return imagechapter;
    }

    private void ArrayToList(List<JsonObject> jsonList, JsonArray jsonArray) {
        for (int i = 0; i < jsonArray.size(); i++)
        {
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
            jsonList.add(jsonObject);
        }
    }
    private void setRelationShip(JsonArray relationship, Mangamodel input) {
        List<JsonObject> relationships = new ArrayList<>();
        ArrayToList(relationships,relationship);
        List<Relationship> relationshipList = new ArrayList<>();
        for (JsonObject re : relationships) {
            try
            {
                relationshipList.add( gson.fromJson(re,Relationship.class));
            }
            catch (Exception e){};
        }
        input.setRelationships(relationshipList);
    }

    //hàm chức năng gọi api
    public void getTag() {
        ApiService.apiservice.getTag().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    if (response.body().isJsonObject()) {
                        List<Tag> tagList = new ArrayList<>();
                        JsonObject jsonObject = response.body().getAsJsonObject();
                        JsonArray itemsArray = jsonObject.getAsJsonArray("data");
                        List<JsonObject> list = new ArrayList<>();
                        ArrayToList(list,itemsArray);
                        for (JsonObject object : list)
                        {
                            Tag tag = new Tag();
                            tag.setId(object.get("id").toString());
                            tag.setName(object.get("attributes").getAsJsonObject().get("name").getAsJsonObject().get("en").toString());
                            tagList.add(tag);
                        }
                        listMutableLiveDataTag.postValue(tagList);
                    }
                }
                else {
                    listMutableLiveDataTag.postValue(null);
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                listMutableLiveDataTag.postValue(null);
            }
        });
    }
    public void getAuthor(String authorId) {
        ApiService.apiservice.getAuthor(authorId).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    if (response.body().isJsonObject()) {
                        JsonObject jsonObject = response.body().getAsJsonObject().get("data").getAsJsonObject();
                        Author author = gson.fromJson(jsonObject,Author.class);
                        JsonObject attribute = jsonObject.get("attributes").getAsJsonObject();
                        AuthorAttributes attributes = gson.fromJson(attribute,AuthorAttributes.class);
                        JsonArray relationShip = jsonObject.get("relationships").getAsJsonArray();
                        setRelationShip(relationShip,author);
                        author.setAttributes(attributes);
                        authorMutableLiveData.postValue(author);
                    }
                }
                else {
                    // Xử lý lỗi HTTP response
                    authorMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                authorMutableLiveData.postValue(null);
            }
        });
    }
    public void getMangaRandom() {
        ApiService.apiservice.getRandomManga().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    if (response.body().isJsonObject()) {
                        JsonObject jsonObject = response.body().getAsJsonObject().get("data").getAsJsonObject();
                        Manga manga = gson.fromJson(jsonObject,Manga.class);
                        JsonObject attribute = jsonObject.get("attributes").getAsJsonObject();
                        MangaAttributes attributes = gson.fromJson(attribute,MangaAttributes.class);
                        JsonArray relationShip = jsonObject.get("relationships").getAsJsonArray();
                        setRelationShip(relationShip,manga);
                        manga.setAttributes(attributes);
                        mangaLiveData.postValue(manga);
                    }
                }
                else {
                    // Xử lý lỗi HTTP response
                    mangaLiveData.postValue(null);
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                mangaLiveData.postValue(null);
            }
        });
    }
    public void getMangaById(String mangaId) {
        ApiService.apiservice.getManga(mangaId).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    if (response.body().isJsonObject()) {
                        JsonObject jsonObject = response.body().getAsJsonObject().get("data").getAsJsonObject();
                        Manga manga = gson.fromJson(jsonObject,Manga.class);
                        JsonObject attribute = jsonObject.get("attributes").getAsJsonObject();
                        MangaAttributes attributes = gson.fromJson(attribute,MangaAttributes.class);
                        JsonArray relationShip = jsonObject.get("relationships").getAsJsonArray();
                        setRelationShip(relationShip,manga);
                        manga.setAttributes(attributes);
                        mangaById.postValue(manga);
                    }
                }
                else {
                    // Xử lý lỗi HTTP response
                    mangaById.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                mangaById.postValue(null);
            }
        });
    }
    public void getCover(String mangaId) {
        ApiService.apiservice.getCover(mangaId).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    if (response.body().isJsonObject()) {
                        JsonObject jsonObject = response.body().getAsJsonObject().get("data").getAsJsonObject();
                        JsonObject atribute = jsonObject.get("attributes").getAsJsonObject();
                        JsonArray relationship = jsonObject.get("relationships").getAsJsonArray();
                        Cover cover = gson.fromJson(jsonObject,Cover.class);
                        cover.setAttributes(gson.fromJson(atribute,CoverAttributes.class));
                        setRelationShip(relationship,cover);
                        coverMutableLiveData.postValue(cover);
                    }
                }
                else {
                    coverMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                    coverMutableLiveData.postValue(null);
            }
        });
    }
    public void getMangaChapter(String mangaId) {
        ApiService.apiservice.getMangaAggregate(mangaId).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    if (response.body().isJsonObject()) {
                        try {
                            JsonObject jsonObject = response.body().getAsJsonObject().get("volumes").getAsJsonObject();
                            Iterator<String> keys = jsonObject.keySet().iterator();
                            Map<String, List<Map<String, String>>> stringListMap = new HashMap<>();
                            while (keys.hasNext()) {
                                String key = keys.next();
                                JsonObject jsonObject1 = jsonObject.get(key).getAsJsonObject().get("chapters").getAsJsonObject();
                                Iterator<String> keys2 = jsonObject1.keySet().iterator();
                                List<Map<String, String>> list = new ArrayList<>();
                                while (keys2.hasNext()) {
                                    String key2 = keys2.next();
                                    JsonObject object = jsonObject1.get(key2).getAsJsonObject();
                                    Map<String, String> map = new HashMap<>();
                                    map.put(object.get("chapter").toString(), object.get("id").toString());
                                    list.add(map);
                                }
                                stringListMap.put(key, list);
                            }
                            chapterListLiveData.postValue(stringListMap);
                        }
                        catch (Exception e){chapterListLiveData.postValue(null);}
                    }
                }
                else {
                    // Xử lý lỗi HTTP response
                    chapterListLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                chapterListLiveData.postValue(null);
            }
        });
    }
    public void getchapterimage(String chapterId) {
        ApiService.apiservice.getimagechapter(chapterId).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    if (response.body().isJsonObject()) {
                        try {
                            List<String> temp = new ArrayList<>();
                            JsonObject jsonObject = response.body().getAsJsonObject().get("chapter").getAsJsonObject();
                            temp.add(jsonObject.get("hash").getAsString());
                            JsonArray image = jsonObject.get("data").getAsJsonArray();
                            for (int i = 0; i < image.size(); i++) {
                                temp.add(image.get(i).getAsString());
                            }
                            imagechapter.postValue(temp);
                        }
                        catch (Exception e) {}
                    }
                }
                else {
                    // Xử lý lỗi HTTP response
                    imagechapter.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                imagechapter.postValue(null);
            }
        });
    }
    public void getManga_list(int offset, String title, String author, int year, ArrayList<Tag> included, ArrayList<Tag> excluded, ArrayList status, ArrayList ids, ArrayList includes) {
        ApiService.apiservice.getMangaId(offset, title, author, year, included, excluded, status, ids, includes).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    if (response.body().isJsonObject()) {
                        try {
                            JsonArray jsonArray = response.body().getAsJsonObject().get("data").getAsJsonArray();
                            List<Manga> mangaList11 = new ArrayList<>();
                            for (int i = 0; i < jsonArray.size(); i++) {
                                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                                Manga manga = gson.fromJson(jsonObject, Manga.class);
                                JsonObject attribute = jsonObject.get("attributes").getAsJsonObject();
                                MangaAttributes attributes = gson.fromJson(attribute, MangaAttributes.class);
                                JsonArray relationShip = jsonObject.get("relationships").getAsJsonArray();
                                setRelationShip(relationShip, manga);
                                manga.setAttributes(attributes);
                                mangaList11.add(manga);
                            }
                            mangaList.postValue(mangaList11);
                        }
                        catch (Exception e){}
                    }
                    else{
                        mangaList.postValue(null);
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                mangaList.postValue(null);
            }
        });
    }
}
