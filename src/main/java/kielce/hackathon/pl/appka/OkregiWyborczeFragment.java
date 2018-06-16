package kielce.hackathon.pl.appka;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class OkregiWyborczeFragment extends Fragment {


    public OkregiWyborczeFragment() {
        // Required empty public constructor
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        Button btn = (Button)view.findViewById(R.id.open_map_btn2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent new_activity = new Intent(getActivity(),OkregiMaps.class);
                startActivity(new_activity);
            }
        });

        Button btn2 = (Button)view.findViewById(R.id.open_lokale_buton);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent new_activity = new Intent(getActivity(),MapsActivity.class);
                startActivity(new_activity);
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_okregi_wyborcze, container, false);
    }

}
