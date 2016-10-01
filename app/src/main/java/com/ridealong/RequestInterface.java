package com.ridealong;

import com.ridealong.models.ServerRequest;
import com.ridealong.models.ServerResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
/**
 * Created by HP on 5/20/2016.
 */
public interface RequestInterface {

    @POST("http://www.ridealong.lewebev.com/")
    Call<ServerResponse> operation(@Body ServerRequest request);

}
