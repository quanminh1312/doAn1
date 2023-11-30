package com.example.doan1;

import android.content.Context;

import com.example.doan1.ApiService;
import com.example.doan1.MyCallBack;
import com.example.doan1.model.Author.Author;
import com.example.doan1.model.Author.AuthorAttributes;
import com.example.doan1.model.Chapter.Chapter;
import com.example.doan1.model.Manga.Manga;
import com.example.doan1.model.Manga.MangaAttributes;
import com.example.doan1.Mangamodel;
import com.example.doan1.model.Relationship;
import com.example.doan1.model.Tag.Tag;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Bus {
    private Context context;
    private Gson gson = new Gson();
    public Bus(Context context) {
        this.context = context;
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
    public void getTag(MyCallBack<List<Tag>> callback) {
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
                            tag.setId(object.get("id").getAsString());
                            tag.setName(object.get("attributes").getAsJsonObject().get("name").getAsJsonObject().get("en").getAsString());
                            tagList.add(tag);
                        }
                        callback.onSuccess(tagList);
                    }
                }
                else {
                    callback.onFailure(null);
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onFailure(null);
            }
        });
    }
    public void getAuthor(String authorId, MyCallBack<Author> callBack) {
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
                        callBack.onSuccess(author);
                    }
                }
                else {
                    // Xử lý lỗi HTTP response
                    callBack.onFailure(null);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callBack.onFailure(null);
            }
        });
    }
    public void getMangaRandom(MyCallBack<Manga> callBack) {
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

                        List<Tag> tags = manga.getAttributes().getTags();
                        JsonArray tagsName= attribute.get("tags").getAsJsonArray();
                        for (int i=0; i<tagsName.size();i++)
                        {
                            String name = tagsName.get(i).getAsJsonObject().get("attributes").getAsJsonObject().get("name").getAsJsonObject().get("en").getAsString();
                            tags.get(i).setName(name);
                        }
                        manga.getAttributes().setTags(tags);


                        callBack.onSuccess(manga);
                    }
                }
                else {
                    // Xử lý lỗi HTTP response
                    callBack.onFailure(null);
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callBack.onFailure(null);
            }
        });
    }
    public void getMangaById(String mangaId, MyCallBack<Manga> callBack) {
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


                        List<Tag> tags = manga.getAttributes().getTags();
                        JsonArray tagsName= attribute.get("tags").getAsJsonArray();
                        for (int i=0; i<tagsName.size();i++)
                        {
                            String name = tagsName.get(i).getAsJsonObject().get("attributes").getAsJsonObject().get("name").getAsJsonObject().get("en").getAsString();
                            tags.get(i).setName(name);
                        }
                        manga.getAttributes().setTags(tags);

                        //get cover
                        for (int j = 0; j < relationShip.size(); j++) {
                            JsonObject object = relationShip.get(j).getAsJsonObject();
                            if (object.get("type").getAsString().equals("cover_art"))
                            {
                                manga.setCover(object.get("id").getAsString());
                                break;
                            }
                        }
                        //get name
                        try {
                            String name = attribute.get("title").getAsJsonObject().get("en").getAsString();
                            manga.setName(name);
                        } catch (Exception e){}

                        callBack.onSuccess(manga);
                    }
                }
                else {
                    // Xử lý lỗi HTTP response
                    callBack.onFailure(null);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callBack.onFailure(null);
            }
        });
    }
    public void getCover(String mangaId, MyCallBack<String> callBack) {
        ApiService.apiservice.getCover(mangaId).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    if (response.body().isJsonObject()) {
                        JsonObject jsonObject = response.body().getAsJsonObject().get("data").getAsJsonObject().get("attributes").getAsJsonObject();
                        String filename = jsonObject.get("fileName").getAsString();
                        callBack.onSuccess(filename);
                    }
                }
                else {
                    callBack.onFailure(null);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                    callBack.onFailure(null);
            }
        });
    }
    public void getMangaChapter(String mangaId, MyCallBack<Map<String, List<com.example.doan1.model.Chapter.Chapter>>> callBack) {
        ApiService.apiservice.getMangaAggregate(mangaId).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    if (response.body().isJsonObject()) {
                        try {
                            JsonObject jsonObject = response.body().getAsJsonObject().get("volumes").getAsJsonObject();
                            Iterator<String> keys = jsonObject.keySet().iterator();
                            Map<String, List<com.example.doan1.model.Chapter.Chapter>> stringListMap = new HashMap<>();
                            while (keys.hasNext()) {
                                String key = keys.next();
                                JsonObject jsonObject1 = jsonObject.get(key).getAsJsonObject().get("chapters").getAsJsonObject();
                                Iterator<String> keys2 = jsonObject1.keySet().iterator();
                                List<Chapter> list = new ArrayList<>();
                                while (keys2.hasNext()) {
                                    String key2 = keys2.next();
                                    String id =  jsonObject1.get(key2).getAsJsonObject().get("id").getAsString();
                                    list.add(new Chapter(id,key2));
                                }
                                stringListMap.put(key, list);
                            }
                            callBack.onSuccess(stringListMap);
                        }
                        catch (Exception e)
                        {
                            callBack.onFailure(null);
                        }
                    }
                }
                else {
                    // Xử lý lỗi HTTP response
                    callBack.onFailure(null);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callBack.onFailure(null);
            }
        });
    }
    public void getChapterImage(String chapterId, MyCallBack<List<String>> callback) {
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
                            callback.onSuccess(temp);
                        }
                        catch (Exception e) {callback.onFailure(null);}
                    }
                }
                else {
                    // Xử lý lỗi HTTP response
                    callback.onFailure(null);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onFailure(null);
            }
        });
    }
    Callback<JsonObject> callbackMangaList(MyCallBack<List<Manga>> callBack)
    {
        Callback<JsonObject> temp = new Callback<JsonObject>() {
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

                                //get cover
                                for (int j = 0; j < relationShip.size(); j++) {
                                    JsonObject object = relationShip.get(j).getAsJsonObject();
                                    if (object.get("type").getAsString().equals("cover_art"))
                                    {
                                        manga.setCover(object.get("id").getAsString());
                                        break;
                                    }
                                }

                                //get tag name
                                List<Tag> tags = manga.getAttributes().getTags();
                                JsonArray tagsName= attribute.get("tags").getAsJsonArray();
                                for (int j=0; j<tagsName.size();j++)
                                {
                                    String name = tagsName.get(j).getAsJsonObject().get("attributes").getAsJsonObject().get("name").getAsJsonObject().get("en").getAsString();
                                    tags.get(j).setName(name);
                                }
                                manga.getAttributes().setTags(tags);


                                //get name
                                try {
                                    String name = attribute.get("title").getAsJsonObject().get("en").getAsString();
                                    manga.setName(name);
                                } catch (Exception e){}
                                mangaList11.add(manga);
                            }
                            callBack.onSuccess(mangaList11);
                        }
                        catch (Exception e)
                        {
                            System.out.println(e);
                        }
                    }
                    else{
                        callBack.onFailure(null);
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callBack.onFailure(null);
            }
        };
        return temp;
    }
    public void getMangaListByPage(Integer offset
//            , String title, String author, Integer year
//            , ArrayList<Tag> included, ArrayList<Tag> excluded
//            , ArrayList status, ArrayList ids, ArrayList includes
            , MyCallBack<List<Manga>> callBack) {
        ApiService.apiservice.getMangaByPage(offset).enqueue(callbackMangaList(callBack));
    }
    public void getMangaListRank(Map<String,Object> order
            , MyCallBack<List<Manga>> callBack) {
        ApiService.apiservice.getMangaByOrder(order).enqueue(callbackMangaList(callBack));
    }
    public void getMangaTagInclude(Integer offset, String includedTags
            , MyCallBack<List<Manga>> callBack) {
        ApiService.apiservice.getMangaByTag(offset,includedTags).enqueue(callbackMangaList(callBack));
    }
}
