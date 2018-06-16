package kielce.hackathon.pl.appka;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class JakGlosowacFragment extends Fragment {

    private TextView mHeader;
    private TextView mInfo;
    private TextView mInfo2;
    private TextView mInfo3;

    public JakGlosowacFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mHeader = (TextView)view.findViewById(R.id.jakGlosFrag_header_tv);
        mInfo = (TextView)view.findViewById(R.id.jakGlosFrag_info_tv);
        mInfo2 = (TextView)view.findViewById(R.id.jakGlosFrag_info2_tv);
        mInfo3 = (TextView)view.findViewById(R.id.jakGlosFrag_info3_tv);

        mHeader.setText("Kto może zagłosować");
        mInfo.setText("\u2022   obywatel polski, który najpóźniej w dniu głosowania kończy 18 lat, oraz stale zamieszkuje na obszarze, odpowiednio, tego powiatu i województwa\n");
        mInfo2.setText("\u2022   Na wójta, burmistrza czy prezydenta danego miasta głosować mogą stale zamieszkujący dany obszar. Kryterium wieku nie ulega zmianie.\n");
        mInfo3.setText("\u2022   Jednakże istnieją także osoby, które nie mogą głosować pomimo ukończonych 18 lat. Są to ludzie którym odebrano prawa publiczne i/lub ubezwłasnowolniono na skutek prawomocnego orzeczenia sądu oraz ci bez praw wyborczych, o czym zadecydował Trybunał Stanu.\n");
}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_jak_glosowac, container, false);
    }

}
