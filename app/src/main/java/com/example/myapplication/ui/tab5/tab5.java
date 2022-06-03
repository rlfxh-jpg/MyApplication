package com.example.myapplication.ui.tab5;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Adapter.OverWatchAdapter;
import com.example.myapplication.R;
import com.example.myapplication.logic.model.OverWatch;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link tab5#newInstance} factory method to
 * create an instance of this fragment.
 */
public class tab5 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public tab5() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment tab5.
     */
    // TODO: Rename and change types and number of parameters
    public static tab5 newInstance(String param1, String param2) {
        tab5 fragment = new tab5();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        List<OverWatch> list=new ArrayList<>();
        OverWatch overWatch=new OverWatch("Top 100",R.drawable.toux,"http://www.loyalbooks.com/Top_100");
        list.add(overWatch);
        list.add(new OverWatch("Adventure",R.drawable.toux,"http://www.loyalbooks.com/genre/Adventure"));
        list.add(new OverWatch("Children",R.drawable.toux,"http://www.loyalbooks.com/genre/Children"));
        list.add(new OverWatch("Comedy",R.drawable.toux,"http://www.loyalbooks.com/genre/Comedy"));
        list.add(new OverWatch("Fairy tales",R.drawable.toux,"http://www.loyalbooks.com/genre/Fairy_tales"));
        list.add(new OverWatch("Fantasy",R.drawable.toux,"http://www.loyalbooks.com/genre/Fantasy"));
        list.add(new OverWatch("Fiction",R.drawable.toux,"http://www.loyalbooks.com/genre/Fiction"));
        list.add(new OverWatch("Historical Fiction",R.drawable.toux,"http://www.loyalbooks.com/genre/Historical_Fiction"));
        list.add(new OverWatch("History",R.drawable.toux,"http://www.loyalbooks.com/genre/History"));
        list.add(new OverWatch("Humor",R.drawable.toux,"http://www.loyalbooks.com/genre/Humor"));
        list.add(new OverWatch("Literature",R.drawable.toux,"http://www.loyalbooks.com/genre/Literature"));
        list.add(new OverWatch("Mystery",R.drawable.toux,"http://www.loyalbooks.com/genre/Mystery"));
        list.add(new OverWatch("Non-fiction",R.drawable.toux,"http://www.loyalbooks.com/genre/Non-fiction"));
        list.add(new OverWatch("Philosophy",R.drawable.toux,"http://www.loyalbooks.com/genre/Philosophy"));
        list.add(new OverWatch("Poetry",R.drawable.toux,"http://www.loyalbooks.com/genre/Poetry"));
        list.add(new OverWatch("Romance",R.drawable.toux,"http://www.loyalbooks.com/genre/Romance"));
        list.add(new OverWatch("Religion",R.drawable.toux,"http://www.loyalbooks.com/genre/Religion"));
        list.add(new OverWatch("Science fiction",R.drawable.toux,"http://www.loyalbooks.com/genre/Science_fiction"));
        list.add(new OverWatch("Short stories",R.drawable.toux,"http://www.loyalbooks.com/genre/Short_stories"));
        list.add(new OverWatch("Teen/Young adult",R.drawable.toux,"http://www.loyalbooks.com/genre/Teen_Young_adult"));

        RecyclerView recyclerView=getActivity().findViewById(R.id.rcyview2);
        GridLayoutManager layoutManager=new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(layoutManager);
        OverWatchAdapter overWatchAdapter=new OverWatchAdapter(list);
        recyclerView.setAdapter(overWatchAdapter);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab6, container, false);
    }
}