package models;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("endpoint/ejemplo")
    Call<List<producto>> getDatos();
}
