package com.tacademy.sadajo.search.searchlist;

/**
 * Created by woosuk on 2016-11-15.
 */



// 화면에 출력할 데이터를 담은 객체
public class ItemArrayList {


    int image;
    String itemname;

    public int getImage(){
        return image;

    }

    public String getitemname(){
        return itemname;
    }
    public ItemArrayList(int image, String itemname){
        this.image= image;
        this.itemname= itemname;
    }

/*
    private static ArrayList<Integer> imageResources;
    //  private static String GIRLS_IMAGE_URI= "http://192.168.0.196:5678/androidNetwork/girlsGeneration/";
    static {
        imageResources = new ArrayList<Integer>();
        imageResources.add(R.drawable.sample1);
        imageResources.add(R.drawable.sample2);
        imageResources.add(R.drawable.sample3);
        imageResources.add(R.drawable.sample4);
        imageResources.add(R.drawable.sample5);
    }
    private static HashMap<Integer, String> nameMaps;

    static {
        nameMaps = new HashMap<Integer, String>();
        nameMaps.put(R.drawable.sample1, "소녀시대멤버1");
        nameMaps.put(R.drawable.sample2, "효연1");
        nameMaps.put(R.drawable.sample3, "제시카1");
        nameMaps.put(R.drawable.sample4, "서현1");
        nameMaps.put(R.drawable.sample5, "써니1");
    }



    public static String  getGirlGroupName(int key) {
        return nameMaps.get(key);
    }*/
}

