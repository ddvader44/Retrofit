package com.ddvader44.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;
    private JsonApi jsonApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.text_view_result);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public okhttp3.Response intercept(@NotNull Chain chain) throws IOException {
                        Request originalRequest = chain.request();
                        Request newRequest = originalRequest.newBuilder()
                                .header("Interceptor-Header","xyz")
                                .build();
                        return chain.proceed(newRequest);
                    }
                })
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        jsonApi = retrofit.create(JsonApi.class);

        getPosts();
        getComments();
        createPosts();
        updatePost();
        deletePost();
    }

    private void deletePost() {
        Call<Void> call = jsonApi.deletePost(5);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                textViewResult.setText("Code:- "+response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void updatePost() {
        Post post = new Post(12,null,"New");
        Map<String,String> headers = new HashMap<>();
        headers.put("YO","ddvader44");
        headers.put("HEY","ddvader44");
        Call<Post> call = jsonApi.patchPost(headers,5,post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(!response.isSuccessful())
                {
                    textViewResult.setText("Code: "+ response.code());
                    return;
                }

                Post postResponse = response.body();


                String content="";
                content += "Code: "+response.code()+"\n";
                content += "ID: "+postResponse.getId()+"\n";
                content += "User ID: "+postResponse.getUserId()+"\n";
                content += "Title: "+postResponse.getTitle()+"\n";
                content += "Text: "+postResponse.getText()+"\n\n";

                textViewResult.setText(content);


            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void createPosts() {
        Post post = new Post(44,"ddvader","Android Rocks!");

        Call<Post> call = jsonApi.createPost(44,"ddvader44","Android Trials");
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(!response.isSuccessful())
                {
                    textViewResult.setText("Code: "+ response.code());
                    return;
                }

                Post postResponse = response.body();


                    String content="";
                    content += "Code: "+response.code()+"\n";
                    content += "ID: "+postResponse.getId()+"\n";
                    content += "User ID: "+postResponse.getUserId()+"\n";
                    content += "Title: "+postResponse.getTitle()+"\n";
                    content += "Text: "+postResponse.getText()+"\n\n";

                    textViewResult.setText(content);


            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void getPosts() {
        Call<List<Post>> call = jsonApi.getPosts(4,"id","desc");

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful())
                {
                    textViewResult.setText("Code: "+ response.code());
                    return;
                }

                List<Post> posts = response.body();
                for(Post post: posts)
                {
                    String content="";
                    content += "ID: "+post.getId()+"\n";
                    content += "User ID: "+post.getUserId()+"\n";
                    content += "Title: "+post.getTitle()+"\n";
                    content += "Text: "+post.getText()+"\n\n";

                    textViewResult.append(content);
                }

            }


            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void getComments(){
        Call<List<Comment>> call = jsonApi.getComments(3);

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if(!response.isSuccessful())
                {
                    textViewResult.setText("Code: "+ response.code());
                    return;
                }

                List<Comment> comments = response.body();
                for(Comment comment: comments)
                {
                    String content="";
                    content += "ID: "+comment.getId()+"\n";
                    content += "Post ID: "+comment.getPostId()+"\n";
                    content += "Name: "+comment.getName()+"\n";
                    content += "Email: "+comment.getEmail() +"\n";
                    content += "Text: "+comment.getText()+"\n\n";

                    textViewResult.append(content);
                }

            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
}
