package com.listAdapters.bikeAdapters;

import android.content.Context;
import android.database.Cursor;

import com.db_adapter.DBAdapter;
import com.table_base.TableBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Admin on 11-January-11-2017.
 */

public class ExpandableListData {
    public  LinkedHashMap<String,List<String>> getData(Context mContext){
        //HashMap<String,List<String>> listDetails=new HashMap<String,List<String>>();
          LinkedHashMap<String,List<String>> listDetails=new LinkedHashMap<String,List<String>>();

        DBAdapter adapter=new DBAdapter(mContext);
        try{
            Cursor mCursor=adapter.getTableInfo(TableBase.Table.PRODUCT_CATEGORIES.PRODUCT_CATEGORIES);
            if(mCursor!=null){
                mCursor.moveToFirst();
                if(mCursor.getCount()>0){

                    listDetails.put("Default",new ArrayList<String>());
                    while (mCursor.isAfterLast()==false){
                        String catageory="";
                        String catId="";
                        catId=mCursor.getString(mCursor.getColumnIndex(TableBase.Table.PRODUCT_CATEGORIES.ID));
                        catageory=mCursor.getString(mCursor.getColumnIndex(TableBase.Table.PRODUCT_CATEGORIES.NAME));
                        listDetails.put(catageory,childItems(catId,mContext,adapter));
                        mCursor.moveToNext();
                    }

                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return listDetails;
    }
    private synchronized List<String>childItems(String catId,Context mContext,DBAdapter adapter){
        List<String> childs=new ArrayList<String>();
        try{
            Cursor mCursor=null;
            mCursor=adapter.getSubCategorys(catId);
            if(mCursor!=null){
                mCursor.moveToFirst();
                while(mCursor.isAfterLast()==false){
                    childs.add(mCursor.getString(mCursor.getColumnIndex(TableBase.Table.PRODUCT_SUB_CATEGORIES.SUB_CATEGORIES_NAME)));
                    mCursor.moveToNext();
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return childs;
    }

}
