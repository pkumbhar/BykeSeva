package com.abstractsupport;

import android.support.v7.widget.RecyclerView;

import java.io.InputStream;

/**
 * Created by Admin on 27-October-27-2016.
 */
public interface ProductProcessable  {

    public String processDefaultCategory(InputStream inputStream);
    public String processAccessories(String inputStream);


}
