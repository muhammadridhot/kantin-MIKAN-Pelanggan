package com.example.ridho.tugasakhir_mikan.Remote;

import com.example.ridho.tugasakhir_mikan.Model.Response;
import com.example.ridho.tugasakhir_mikan.Model.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAcJdTk2s:APA91bGNGbSXttaaVgVhVXg61KQi3GxYQOKY3VuiGtXU36Hw9NcBZKaPQ2up4zp7P4V5caE0_p1FKwlo9gSqKAggwKvkLqKN44GElMDzK9mC5dL6ypFEueG5ZyNqpvV5c_iJ1lT2cPlj"
            }
    )
    @POST("fcm/send")
    Call<Response> sendNotification(@Body Sender body);
}
