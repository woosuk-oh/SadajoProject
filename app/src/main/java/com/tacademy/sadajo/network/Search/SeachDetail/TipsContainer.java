package com.tacademy.sadajo.network.Search.SeachDetail;

import java.util.ArrayList;

/**
 * Created by Tacademy on 2016-12-08.
 */

public class TipsContainer {

    public ArrayList<TipsDB> tips = new ArrayList<>();

    public ArrayList<TipsDB> getTips() {
        return tips;
    }

    public void setTips(ArrayList<TipsDB> tips) {
        this.tips = tips;
    }
}
