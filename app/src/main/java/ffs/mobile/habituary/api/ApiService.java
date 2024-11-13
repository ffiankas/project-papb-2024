package ffs.mobile.habituary.api;

import ffs.mobile.habituary.model.ApiResponse;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {
    @GET("habits.php")
    Call<ApiResponse> getHabits();

    @Multipart
    @POST("habits.php")
    Call<ApiResponse> addHabits(
            @Part("yourGoal") RequestBody yourGoal,
            @Part("habitName") RequestBody habitName,
            @Part("period") RequestBody period,
            @Part("habitType") RequestBody habitType
    );
}
