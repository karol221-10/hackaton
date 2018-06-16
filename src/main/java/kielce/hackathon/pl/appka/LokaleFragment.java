package kielce.hackathon.pl.appka;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
class MetodaKogosTam
{
    public String komitet;
    public int poparcie;
}
class PoparcieKandydatow
{
    final DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("users").getRef();
    private List<Kandydat> kandydaci = new ArrayList<Kandydat>();
    public  int sumaGlosow = 0;
    private int okreg = 0;
    private PieChart chart;
    private ChildEventListener childListEventListener;
    private ChildEventListener drugiEventListener;
    private int komitetjastrzebi = 0;
    private int komitetGolebi = 0;
    private int komitetSamorzadowy = 0;
    private int mandatyjastrzebi = 0;
    private int mandatygolebi = 0;
    private int mandatysamorzadowy = 0;
    private int mandaty = 3;
    List<MetodaKogosTam> lista_glosow;
    PoparcieKandydatow(PieChart chart)
    {
        this.chart = chart;
        lista_glosow = new ArrayList<MetodaKogosTam>();
        childListEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot datasnapshot, String previousChildName) {
                Log.d("DEB", "OnChildAdded: " + datasnapshot.getKey());
                Kandydat kandydat = datasnapshot.getValue(Kandydat.class);
                if((kandydat.okreg.charAt(0)-48)==okreg)
                {
                    kandydaci.add(kandydat);
                }
                sumaGlosow+= Integer.parseInt(kandydat.poparcie);
                setupPieChart();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot datasnapshot, @Nullable String s) {
                Log.d("DEB", "OnChildAdded: ");
                Kandydat kandydat = datasnapshot.getValue(Kandydat.class);
                if((kandydat.okreg.charAt(0)-48)==okreg)
                {
                    for(Kandydat kk : kandydaci)
                    {
                        if(kk.surname.equals(kandydat.surname))
                        {
                            kk.poparcie = kandydat.poparcie;
                        }
                    }
                }
                sumaGlosow+= Integer.parseInt(kandydat.poparcie);
                setupPieChart();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Log.d("DEB", "OnChildAdded: ");

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("DEB", "OnChildAdded: ");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("DEB", "OnChildAdded: ");

            }
        };
        drugiEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Kandydat kandydat = dataSnapshot.getValue(Kandydat.class);
                if(kandydat.komitet.equals("Jastrzebi") && (kandydat.okreg.charAt(0)-48)==okreg)  komitetjastrzebi+=Integer.parseInt(kandydat.poparcie);
                else if(kandydat.komitet.equals("Golebi") && (kandydat.okreg.charAt(0)-48)==okreg) komitetGolebi+=Integer.parseInt(kandydat.poparcie);
                else if(kandydat.komitet.equals("Zwiazek Samorzadowy") && (kandydat.okreg.charAt(0)-48)==okreg) komitetSamorzadowy+=Integer.parseInt(kandydat.poparcie);
                sumaGlosow++;
                oblicz_dhoinea();
                setupkomitetowypichart();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        database.addChildEventListener(childListEventListener);
    }
    public void ustawTrybZwykly()
    {
        database.removeEventListener(childListEventListener);
        database.removeEventListener(drugiEventListener);
        database.addChildEventListener(childListEventListener);
    }
    public void ustawTrybKomitetow()
    {
        database.removeEventListener(childListEventListener);
        database.removeEventListener(drugiEventListener);
        sumaGlosow = 0;
        komitetjastrzebi = 0;
        komitetGolebi = 0;
        komitetSamorzadowy = 0;
        mandatygolebi = 0;
        mandatyjastrzebi = 0;
        mandatysamorzadowy = 0;
        database.addChildEventListener(drugiEventListener);
    }
    private void setupPieChart() {
        List<PieEntry> pieEntries = new ArrayList<>();
        //  for (int i = 0; i < mProcentPoparcia.length; i++)
        //    pieEntries.add(new PieEntry(mProcentPoparcia[i],mKandydaci[i]));
        for(int i = 0;i<kandydaci.size();i++)
        {
            pieEntries.add(new PieEntry(Integer.parseInt(kandydaci.get(i).poparcie),kandydaci.get(i).name));
        }
        PieDataSet dataSet = new PieDataSet(pieEntries,"poparcie kandydatow ");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData data = new PieData(dataSet);

        //get the chart
        chart.setData(data);
        chart.animateXY(2000,1000);
        chart.invalidate();
    }
    private void setupkomitetowypichart() {
        List<PieEntry> pieEntries = new ArrayList<>();
        //  for (int i = 0; i < mProcentPoparcia.length; i++)
        //    pieEntries.add(new PieEntry(mProcentPoparcia[i],mKandydaci[i]));
        if(mandatyjastrzebi>0) pieEntries.add(new PieEntry(mandatyjastrzebi,"Komitet Jastrzebi"));
        if(mandatygolebi>0) pieEntries.add(new PieEntry(mandatygolebi,"Komitet Gołębi"));
        if(mandatysamorzadowy>0) pieEntries.add(new PieEntry(mandatysamorzadowy,"Komitet Samorządowy"));
        PieDataSet dataSet = new PieDataSet(pieEntries,"poparcie kandydatow ");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData data = new PieData(dataSet);

        //get the chart
        chart.setData(data);
        chart.animateXY(2000,1000);
        chart.invalidate();
        mandatysamorzadowy = 0;
        mandatygolebi = 0;
        mandatyjastrzebi = 0;
    }
    void setOkreg(int okreg)
    {
        kandydaci.clear();
        this.okreg = okreg;
   //     database.addChildEventListener(childListEventListener);
    }
    void oblicz_dhoinea()
    {
        lista_glosow.clear();
        for(int i = 1;i<=mandaty;i++)
        {
            MetodaKogosTam jastrzebi = new MetodaKogosTam();
            MetodaKogosTam Golebi = new MetodaKogosTam();
            MetodaKogosTam Samorzadowy = new MetodaKogosTam();
            jastrzebi.komitet = "Jastrzebi";
            jastrzebi.poparcie = komitetjastrzebi/i;
            Golebi.komitet = "Golebi";
            Golebi.poparcie = komitetGolebi/i;
            Samorzadowy.komitet = "Samorzadowy";
            Samorzadowy.poparcie = komitetSamorzadowy/i;
            lista_glosow.add(jastrzebi);
            lista_glosow.add(Golebi);
            lista_glosow.add(Samorzadowy);
        }
        lista_glosow.sort(new Comparator<MetodaKogosTam>() {
            @Override
            public int compare(MetodaKogosTam o1, MetodaKogosTam o2) {
                if(o1.poparcie>o2.poparcie) return -1;
                else if(o1.poparcie<o2.poparcie) return 1;
                else return 0;
            }
        });
        for(int i = 0;i<mandaty;i++)
        {
            if(lista_glosow.get(i).komitet.equals("Jastrzebi")) mandatyjastrzebi++;
            else if(lista_glosow.get(i).komitet.equals("Golebi")) mandatygolebi++;
            else if(lista_glosow.get(i).komitet.equals("Samorzadowy")) mandatysamorzadowy++;
        }
    }
}
public class LokaleFragment extends Fragment {
    private PoparcieKandydatow pk;
    public LokaleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        Button ogolne_poparcie_1 = (Button)getView().findViewById(R.id.button4);
        Button ogolne_poparcie_2 = (Button)getView().findViewById(R.id.button7);
        Button ogolne_poparcie_3 = (Button)getView().findViewById(R.id.button8);
        Button ogolne_poparcie_4 = (Button)getView().findViewById(R.id.button10);
        Button ogolne_poparcie_5 = (Button)getView().findViewById(R.id.button12);
        Button podzial_mandatow_1 = (Button)getView().findViewById(R.id.button5);
        Button podzial_mandatow_2 = (Button)getView().findViewById(R.id.button6);
        Button podzial_mandatow_3 = (Button)getView().findViewById(R.id.button9);
        Button podzial_mandatow_4 = (Button)getView().findViewById(R.id.button11);
        Button podzial_mandatow_5 = (Button)getView().findViewById(R.id.button13);
        pk = new PoparcieKandydatow((PieChart)getActivity().findViewById(R.id.charts));
        ogolne_poparcie_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pk.ustawTrybZwykly();
                pk.setOkreg(1);
            }
        });
        ogolne_poparcie_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                pk.ustawTrybZwykly();
                pk.setOkreg(2);
            }
        });
        ogolne_poparcie_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pk.ustawTrybZwykly();
                pk.setOkreg(3);
            }
        });
        ogolne_poparcie_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pk.ustawTrybZwykly();
                pk.setOkreg(4);
            }
        });
        ogolne_poparcie_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pk.ustawTrybZwykly();
                pk.setOkreg(5);
            }
        });
        podzial_mandatow_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pk.ustawTrybKomitetow();
                pk.setOkreg(1);
            }
        });
        podzial_mandatow_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pk.ustawTrybKomitetow();
                pk.setOkreg(2);
            }
        });
        podzial_mandatow_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pk.ustawTrybKomitetow();
                pk.setOkreg(3);
            }
        });
        podzial_mandatow_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pk.ustawTrybKomitetow();
                pk.setOkreg(4);
            }
        });
        podzial_mandatow_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pk.ustawTrybKomitetow();
                pk.setOkreg(5);
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lokale, container, false);
    }

}
