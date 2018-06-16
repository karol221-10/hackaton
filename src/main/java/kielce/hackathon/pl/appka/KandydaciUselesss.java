package kielce.hackathon.pl.appka;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class KandydaciUselesss extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kandydaci_uselesss);
        Bundle bundle = getIntent().getExtras();
        WebView wb = (WebView)findViewById(R.id.fajny_webview);
        if(bundle!=null)
        {
            int okrag = bundle.getInt("okreg");
            String adres = "http:\\192.168.0.113:3000\\okreg-";
            adres +=okrag;
            adres +="\\";
            wb.getSettings().setJavaScriptEnabled(true);
            wb.loadUrl(adres);
        }
    }
}
