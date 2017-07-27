package com.vecta.nuclearwheels;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.abstractsupport.ProductProcessable;
import com.background.DoProductDownload;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.listAdapters.AccessoriesBeen.AccessoriesBeen;
import com.listAdapters.bikeAdapters.AdapterProduct;
import com.requestUrl.ServerUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
public class FragmentAccessories extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_PARAM1 = "param1";
    public static final String ARG_PARAM2 = "param2";
    private RecyclerView rVAccessories;
    public AdapterProduct product;
    private LinearLayout linearLayout;
    private Button btnRefresh;
    private Bundle bundle=new Bundle();
    private ArrayList<AccessoriesBeen> dataState=new ArrayList<AccessoriesBeen>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public FragmentAccessories() {
        // Required empty public constructor
    }
    public static FragmentAccessories newInstance(String param1) {
        FragmentAccessories fragment = new FragmentAccessories();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        bundle.putParcelableArrayList("dataSet",dataState);
        Log.i("**SAVED STATE","**");
        super.onSaveInstanceState(bundle);
    }
    @Override
    public void onResume() {
        try{
            if(bundle!=null){
                ArrayList<AccessoriesBeen> bn=bundle.getParcelableArrayList("dataSet");
                if(bn!=null){
                    product=new AdapterProduct(getActivity(),getActivity(),bn);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                    rVAccessories.setLayoutManager(mLayoutManager);
                    rVAccessories.setItemAnimator(new DefaultItemAnimator());
                    rVAccessories.setAdapter(product);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.i("Resume Start--","--");
        super.onResume();
    }

    @Override
    public void setRetainInstance(boolean retain) {
        Log.i("RETAIL","--"+retain);
        super.setRetainInstance(retain);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("**ONCERATE","**");
        if(savedInstanceState==null){
            if (getArguments() != null) {
                mParam1 = getArguments().getString(ARG_PARAM1);
                mParam2 = getArguments().getString(ARG_PARAM2);
                Log.i("RECIES ARGS",""+mParam1);
            }
            new DoProductDownload(getActivity(),getActivity(),mHandler,mParam1).execute("");
        }else{
        }
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        ArrayList<AccessoriesBeen> bn;
        Log.i("RESToring view--","--");
       try{
             bn=savedInstanceState.getParcelableArrayList("dataSet");
        }catch (Exception e){
            bn=new ArrayList<AccessoriesBeen>();
        }
        product=new AdapterProduct(getActivity(),getActivity(),bn);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rVAccessories.setLayoutManager(mLayoutManager);
        rVAccessories.setItemAnimator(new DefaultItemAnimator());
        rVAccessories.setAdapter(product);

    }

    @Override
    public void onStart() {
        super.onStart();
        ArrayList<AccessoriesBeen> bin=new ArrayList<AccessoriesBeen>();
        product=new AdapterProduct(getActivity(),getActivity(),bin);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rVAccessories.setLayoutManager(mLayoutManager);
        rVAccessories.setItemAnimator(new DefaultItemAnimator());
        rVAccessories.setAdapter(product);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DoProductDownload(getActivity(),getActivity(),mHandler,"Default").execute("");
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_fragment_accessories, container, false);
        rVAccessories=(RecyclerView)view.findViewById(R.id.recycler_accessories_id);
        linearLayout=(LinearLayout)view.findViewById(R.id.lin_accessories_ID);
        btnRefresh=(Button) view.findViewById(R.id.btn_refreshAccessoriesID);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }
    @Override
    public void onDetach() {
        super.onDetach();
    }
    public Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    /************************PRODUCT ACYNCTASK****************************************/
    public class DoProductDownload extends AsyncTask<String,Void,String> implements ProductProcessable {

        private Context mContext;
        private Activity mActivity;
        private Handler mHandler;
        private ProgressDialog progressDialog;
        private String caterotyType="";


        public DoProductDownload(Context mContext, Activity mActivity, Handler mHandler, String caterotyType) {
            this.mContext = mContext;
            this.mActivity = mActivity;
            this.mHandler = mHandler;

            this.caterotyType = caterotyType;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(mActivity);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading Items");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            InputStream stream=null;
            URL url=null;
            HttpURLConnection urlConnection=null;
            BufferedReader reader=null;
            StringBuilder builder=new StringBuilder();
            try{
                if(mParam2.equals("1")){
                    if(caterotyType.equalsIgnoreCase("Default")){
                        url=new URL(ServerUrl.serverurl+"accessories/topAccessories");
                    }else{
                        url=new URL(ServerUrl.serverurl+"accessories/typeAccessories?type="+caterotyType);
                    }
                }else if(mParam2.equals("0")){
                    if(caterotyType.equals("Default")){
                        url=new URL(ServerUrl.serverurl+"accessories/topAccessories");
                    }else{
                        url=new URL(ServerUrl.serverurl+"accessories/subcategoriesAccessories?categories="+caterotyType);
                    }

                }
                Log.i("calling",""+url.toString());
                urlConnection=(HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.setRequestProperty("content-type","application/json");
                urlConnection.setRequestProperty("dataType","json");
                urlConnection.connect();

            }catch (Exception e){
                e.printStackTrace();
            }
            try{
                int rsCode=urlConnection.getResponseCode();
                if(rsCode==200){
                     stream=urlConnection.getInputStream();
                    reader=new BufferedReader(new InputStreamReader(stream));
                    String line="";
                    try{
                        while ((line=reader.readLine())!=null){
                            builder.append(line);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    progressDialog.dismiss();
                    return builder.toString();
                }else if(rsCode>400){
                     stream=urlConnection.getErrorStream();
                    progressDialog.dismiss();
                    Log.i("RESPONCE CODE"," "+rsCode);
                }
            }catch (IOException e){
                e.printStackTrace();
            }
            return builder.toString();
        }

        @Override
        protected void onPostExecute(String stream) {
            super.onPostExecute(stream);
            if(stream!=null){
                rVAccessories.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.GONE);
              String result=processAccessories(stream);
                if (result.equals("200")){

                }else if(result.equals("201")){
                    rVAccessories.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);
                }else {
                    rVAccessories.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);
                }
            }
        }
        @Override
        public String processDefaultCategory(InputStream inputStream) {
            return null;
        }

        @Override
        public String processAccessories(String inputStream) {
            String status="";
            if(inputStream.isEmpty()){

            }else{
                try {
                    JsonParser jsonParser = new JsonParser();
                    JsonElement jsonElement = jsonParser.parse(inputStream);
                    if (jsonElement.isJsonArray()) {
                    JSONArray jsonArray = new JSONArray(inputStream);
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        if (jsonObject != null) {
                            if (jsonObject.length() > 0) {
                                AccessoriesBeen asbin = new AccessoriesBeen();
                                asbin.setID(jsonObject.getString("ID"));
                                asbin.setPTYPE(jsonObject.getString("PTYPE"));
                                asbin.setCATEGORIESSUBNAME(jsonObject.getString("CATEGORIESSUBNAME"));
                                asbin.setVAT(jsonObject.getString("VAT"));
                                asbin.setPRESENT(jsonObject.getString("PRESENT"));
                                asbin.setMRP(jsonObject.getString("MRP"));
                                asbin.setCLOSE(jsonObject.getString("CLOSE"));
                                asbin.setPRODUCTID(jsonObject.getString("PRODUCTID"));
                                asbin.setLIVEOFFERS(jsonObject.getString("LIVEOFFERS"));
                                JSONArray delArray = jsonObject.getJSONArray("DELIVERY");
                                if (delArray != null) {
                                    for (int a = 0; a < delArray.length(); a++) {
                                        JSONObject delvryObj = delArray.getJSONObject(a);
                                        if (delvryObj.length() > 0) {
                                            asbin.setDelveryPROCESS(delvryObj.getString("PROCESS"));
                                            asbin.setDelveryPRESENT(delvryObj.getString("PRESENT"));
                                            asbin.setDelveryID(delvryObj.getString("ID"));
                                            asbin.setDelveryPINS(delvryObj.getString("PINS"));
                                            asbin.setDelveryCHARGE(delvryObj.getString("CHARGE"));
                                            asbin.setDelveryDESC(delvryObj.getString("DESC"));
                                            asbin.setDelveryCHARGEBLE(delvryObj.getString("CHARGEBLE"));
                                        }

                                    }
                                }
                                asbin.setCATEGORIESNAME(jsonObject.getString("CATEGORIESNAME"));
                                JSONArray ofrJarry = jsonObject.getJSONArray("OFFERES");
                                if (ofrJarry != null) {
                                    if (ofrJarry.length() > 0) {
                                        for (int a = 0; a < ofrJarry.length(); a++) {
                                            JSONObject ofrObject = ofrJarry.getJSONObject(a);
                                            asbin.setOfrLIMITED(ofrObject.getString("LIMITED"));
                                            asbin.setOfrSDATE(ofrObject.getString("SDATE"));
                                            asbin.setOfrID(ofrObject.getString("ID"));
                                            asbin.setOfrCLOSE(ofrObject.getString("CLOSE"));
                                            asbin.setOfrOFFERS(ofrObject.getString("OFFERS"));
                                            asbin.setOfrendEDATE(ofrObject.getString("EDATE"));
                                            asbin.setOfrDISCOUNT(ofrObject.getString("DISCOUNT"));
                                        }
                                    }
                                }
                                asbin.setSTATUS(jsonObject.getString("STATUS"));
                                asbin.setDESCRIPTION(jsonObject.getString("DESCRIPTION"));
                                asbin.setCOLOR(jsonObject.getString("COLOR"));
                                asbin.setID(jsonObject.getString("ID"));
                                asbin.setDOE(jsonObject.getString("DOE"));
                                asbin.setWEIGHT(jsonObject.getString("WEIGHT"));

                                JSONArray posterary = jsonObject.getJSONArray("POSTERS");
                                StringBuilder poster = new StringBuilder();
                                for (int p = 0; p < posterary.length(); p++) {
                                    poster.append((String) posterary.get(p)).append(";");
                                }
                                String p = poster.toString();
                                String a = p.replace(System.getProperty("line.separator"), "");
                                asbin.setPoster(a);

                                asbin.setType(jsonObject.getString("TYPE"));
                                asbin.setPUSEDFOR(jsonObject.getString("PUSEDFOR"));
                                asbin.setPRODUCTNAME(jsonObject.getString("PRODUCTNAME"));

                                JSONObject usrobj = jsonObject.getJSONObject("USER");
                                if (usrobj != null) {
                                    asbin.setUid(usrobj.getString("UID"));
                                    asbin.setUidrmdl(usrobj.getString("ID"));
                                }
                                asbin.setUPRICE(jsonObject.getString("UPRICE"));
                                asbin.setNAME(jsonObject.getString("NAME"));
                                asbin.setDISCOUNT(jsonObject.getString("DISCOUNT"));
                                asbin.setBRAND(jsonObject.getString("BRAND"));
                                asbin.setTPRICE(jsonObject.getString("TPRICE"));
                                asbin.setUNAME(jsonObject.getString("UNAME"));
                                asbin.setVEHICLEUSED(jsonObject.getString("VEHICLEUSED"));
                                asbin.setWARRANTY(jsonObject.getString("WARRANTY"));
                                asbin.setVERSION(jsonObject.getString("VERSION"));
                                asbin.setMANU(jsonObject.getString("MANU"));
                                JSONArray fetarryObj = jsonObject.getJSONArray("FEATURE");

                                for (int f = 0; f < fetarryObj.length(); f++) {
                                    JSONObject fetrobj = fetarryObj.getJSONObject(f);
                                    asbin.setFetrFEATURE(fetrobj.getString("FEATURE"));
                                    asbin.setFetrID(fetrobj.getString("ID"));
                                    asbin.setTetrTYPE(fetrobj.getString("TYPE"));
                                }
                                JSONArray revArry = jsonObject.getJSONArray("REVIEW");
                                if (revArry.length() > 0) {
                                    for (int r = 0; r < revArry.length(); r++) {
                                        JSONObject revJobj = revArry.getJSONObject(r);
                                    }
                                }
                                status="200";
                                product.addProductNotify(asbin);
                                dataState.add(asbin);
                            }
                        }
                    }
                }else if(jsonElement.isJsonObject()){
                        JSONObject object=new JSONObject(inputStream);
                        if(object.has("MSG")){
                           status=object.getString("MSG");
                        }if(object.has("STATUS")){
                            status=object.getString("STATUS");
                        }
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
            return status;
        }
    }
}
