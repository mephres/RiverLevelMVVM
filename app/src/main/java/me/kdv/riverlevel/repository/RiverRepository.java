package me.kdv.riverlevel.repository;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import me.kdv.riverlevel.model.River;
import me.kdv.riverlevel.model.RiverResponse;
import me.kdv.riverlevel.network.ApiFactory;
import me.kdv.riverlevel.network.ApiRequest;
import me.kdv.riverlevel.room.App;
import me.kdv.riverlevel.room.AppDatabase;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiverRepository {

    private ApiRequest apiRequest;
    private AppDatabase database;

    public RiverRepository() {
        apiRequest = ApiFactory.getInstance();
        database = App.getInstance().getDatabase();
    }

    public MutableLiveData<RiverResponse> loadRiverInfo() {
        MutableLiveData<RiverResponse> riverResponseMutableLiveData = new MutableLiveData<>();
        apiRequest.getRiverLevel()
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        if (response.isSuccessful() && response.body()  != null) {
                            Document document = new Document("");
                            document.append(response.body());

                            List<River> riverArrayList = parseRiverInfo(document);

                            RiverResponse riverResponse = new RiverResponse();
                            riverResponse.setRiverList(riverArrayList);

                            riverResponseMutableLiveData.setValue(riverResponse);

                            new InsertRiverListTask().execute(riverArrayList);

                        } else {

                            RiverResponse riverResponse = new RiverResponse();
                            riverResponse.setRiverList(database.riverDao().getRiverList());

                            riverResponseMutableLiveData.setValue(riverResponse);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                        RiverResponse riverResponse = new RiverResponse();
                        riverResponse.setRiverList(database.riverDao().getRiverList());

                        riverResponseMutableLiveData.setValue(riverResponse);
                    }
                });
        return riverResponseMutableLiveData;
    }

    private List<River> parseRiverInfo(@NonNull Document riverInfoDocument) {
        List<River> riverArrayList = new ArrayList<>();

        Elements table = riverInfoDocument.select("table");

        for (Element tableElement : table) {
            Log.i("[table]", String.format(" * %s: <%s>", tableElement.tagName(), tableElement.attr("abs:src")));

            Elements trElements = table.select("tr");
            for (Element tr : trElements) {
                Log.i("[table tr]", String.format(" * %s: <%s>", tr.tagName(), tr.attr("abs:src")));
                Elements tdElements = tr.select("td");
                if (!tdElements.isEmpty()) {
                    River river = new River();
                    river.setName(tdElements.get(0).text());
                    river.setStation(tdElements.get(1).text());
                    river.setOverflow(tdElements.get(2).text());
                    river.setWaterLevel(tdElements.get(3).text());
                    river.setLevelChange(tdElements.get(4).text());
                    river.setWaterTemperature(tdElements.get(5).text());

                    riverArrayList.add(river);
                }
            }
        }
        return riverArrayList;
    }

    @SuppressLint("StaticFieldLeak")
    private class InsertRiverListTask extends AsyncTask<List<River>, Void, Void>{

        @Override
        protected Void doInBackground(List<River>... lists) {
            database.riverDao().deleteAll();
            database.riverDao().insertRiverList(lists[0]);
            return null;
        }
    }
}
