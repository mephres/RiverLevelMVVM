package me.kdv.riverlevel.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import me.kdv.riverlevel.model.RiverResponse;
import me.kdv.riverlevel.repository.RiverRepository;

public class MainActivityViewModel extends AndroidViewModel {

    private RiverRepository riverRepository;

    private MutableLiveData<RiverResponse> riverResponseMutableLiveData;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);

    }

    public void init(){
        riverRepository = new RiverRepository();
        riverResponseMutableLiveData = riverRepository.loadRiverInfo();
    }

    public LiveData<RiverResponse> getRiverLevel() {
        return riverResponseMutableLiveData;
    }

}
