package kielce.hackathon.pl.appka;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;


/**
 * A simple {@link Fragment} subclass.
 */
public class KandydaciFragment extends Fragment {


    public KandydaciFragment() {
        // Required empty public constructor
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        WebView wb = (WebView)view.findViewById(R.id.webview);
        wb.getSettings().setJavaScriptEnabled(true);
        if (savedInstanceState != null)
            wb.restoreState(savedInstanceState);
        else
            wb.loadUrl("http://192.168.0.113:3000/"); }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kandydaci, container, false);
    }

}
