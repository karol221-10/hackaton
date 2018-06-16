package kielce.hackathon.pl.appka;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;



public class SondazeActivity extends AppCompatActivity {

    final Context context = this;
    private String phonenumber;
    final DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("users").getRef();
    private List<Kandydat> kandydaci = new ArrayList<Kandydat>();
    public  int sumaGlosow = 0;
    public int okreg = 2;

    private float mProcentPoparcia[] = { 30,30 ,20 ,20,20 };
    private String mKandydaci[] = {"K1", "K2", "K3", "K4","K5"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sondaze);
        ChildEventListener childListEventListener = new ChildEventListener() {
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
        database.addChildEventListener(childListEventListener);

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
        PieChart chart = (PieChart)findViewById(R.id.charts);
        chart.setData(data);
        chart.animateXY(2000,1000);
        chart.invalidate();
    }
}
