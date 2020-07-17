package com.ddvader44.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonApi {
    @GET("posts")
    Call<List<Post>> getPosts(@Query("userId") int userId,
                              @Query("_sort") String sort,
                              @Query("_order") String order
                              );

    @GET("posts/{id}/comments")
    Call<List<Comment>> getComments(@Path("id") int postId);
    //http
    @POST("posts")
    Call<Post> createPost(@Body Post post);

    // another method to send data to server mainly for xml
    @FormUrlEncoded
    @POST("posts")
    Call<Post> createPost(
            @Field("userId") int userId,
            @Field("title") String title,
            @Field("body") String text
    );
}
