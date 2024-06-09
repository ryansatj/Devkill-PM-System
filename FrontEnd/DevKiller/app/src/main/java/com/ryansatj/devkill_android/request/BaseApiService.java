package com.ryansatj.devkill_android.request;

import com.ryansatj.devkill_android.model.Project;
import com.ryansatj.devkill_android.model.Section;
import com.ryansatj.devkill_android.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BaseApiService {
    @FormUrlEncoded
    @POST("account/signup")
    Call<User> createUser(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("username") String username
            );
    @FormUrlEncoded
    @POST("account/login")
    Call<User> getUser(
            @Field("email") String email,
            @Field("password") String password
    );

    @GET("project/getbyuser/{userid}")
    Call<List<Project>> getAllProject (
            @Path("userid") int id
    );

    @FormUrlEncoded
    @POST("project/create/{userid}")
    Call<Project> createProject(
            @Path("userid") int userid,
            @Field("title") String title,
            @Field("descriptions") String descriptions,
            @Field("repository") String repository
    );

    @GET("section/getall/{projectrepo}")
    Call<List<Section>> getAllSection (
            @Path("projectrepo") String projectrepo
    );

    @GET("project/getuser/{repository}")
    Call<List<User>> getAllUserOnProject(
            @Path("repository") String repository
    );

    @FormUrlEncoded
    @POST("project/addUser/{repository}")
    Call<User> addToProject(
            @Path("repository") String repository,
            @Field("username") String username
    );

    @FormUrlEncoded
    @POST("section/create/{projectrepo}")
    Call<Section> createSection(
            @Path("projectrepo") String projectrepo,
            @Field("title") String title,
            @Field("description") String description,
            @Field("deadline") String deadline,
            @Field("resources") String resources,
            @Field("alerts") String alerts
    );

    @FormUrlEncoded
    @PUT("account/update/{id}")
    Call<User> editUser(
            @Path("id") int id,
            @Field("username") String username,
            @Field("name") String name,
            @Field("email") String email
    );
}
