package com.tcc.helpinghand;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputLayout;
import com.tcc.helpinghand.services.DictionaryService;
import com.tcc.helpinghand.services.RetrofitConfig;

import java.util.List;

public class DictionaryFragment extends Fragment {

    public DictionaryService dictionaryService;
    public List<String> signs;

    private ListView lvSigns;
    private TextInputLayout tilSearchBar;

    CircularProgressIndicator progressCircle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dictionary, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        initializeComponents();

        progressCircle.show();
        dictionaryService.getDictionary().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    signs = response.body();
                    setSignsOnList();
                    progressCircle.hide();
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    void setSignsOnList() {
        ArrayAdapter adapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_list_item_1,
                signs
        );
        lvSigns.setAdapter(adapter);
        lvSigns.setOnItemClickListener((adapterView, view, i, l) -> {
            //TODO: show vlibras animation
            //TODO: fix when pressing back button
            String signToTranslate = signs.get(i);

            FragmentTransaction transaction = getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction();

            UnityPlayerFragment fragment = UnityPlayerFragment.newInstance(
                    signToTranslate, false
            );

            transaction.replace(R.id.fl_fragment_container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });
        setSearchBarListener(adapter);
    }

    private void setSearchBarListener(ArrayAdapter listAdapter) {
        tilSearchBar.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                listAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initializeComponents() {
        RetrofitConfig retrofit = new RetrofitConfig();
        dictionaryService = retrofit.getDictionaryService();

        View view = getView();
        lvSigns = view.findViewById(R.id.lv_signs);
        tilSearchBar = view.findViewById(R.id.til_search_bar);
        progressCircle = view.findViewById(R.id.cpi_loading);
    }
}