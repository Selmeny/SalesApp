package com.vertilogic.paul.vertipos.dummy_data;

import com.vertilogic.paul.vertipos.model.ItemModel;
import java.util.ArrayList;
import java.util.List;

public class DummyItems {
    private static String[][] dummyItems = {
            {"Laptop A", "13455000", "http://static.bmdstatic.com/pk/product/medium/5bfe0c1950f3a.jpg","0","0","0","0","0","High price"},
            {"Laptop B", "8500000", "http://static.bmdstatic.com/pk/product/medium/5c457f931521f.jpg","0","0","0","0","0","Low price"},
            {"Laptop C", "9850000", "http://static.bmdstatic.com/pk/product/medium/5a6565692c87d.jpg","0","0","0","0","0","Low price"},
            {"Laptop D", "7700000", "http://static.bmdstatic.com/pk/product/medium/5a026b9ab3eba.jpg","0","0","0","0","0","Low price"},
            {"Laptop E", "15500000", "http://static.bmdstatic.com/pk/product/medium/5a5425323ba7a.jpg","0","0","0","0","0","High price"},
            {"Laptop F", "12000000", "http://static.bmdstatic.com/pk/product/medium/5c2c2d5986522.jpg","0","0","0","0","0","High price"},
            {"Laptop G", "6430000", "http://static.bmdstatic.com/pk/product/medium/5a81002a0c9ab.jpg","0","0","0","0","0","Low price"},
            {"Laptop H", "6705000", "http://static.bmdstatic.com/pk/product/medium/5b0e6dcc0977e.jpg","0","0","0","0","0","Low price"},
            {"Laptop I", "34000000", "http://static.bmdstatic.com/pk/product/medium/5c6f6f3f4a555.jpg","0","0","0","0","0","Low price"},
            {"Laptop J", "5400000", "http://static.bmdstatic.com/pk/product/medium/59a5100867bf3.jpg","0","0","0","0","0","Low price"}
    };

    public static List<ItemModel> getDummyItems() {
        ItemModel itemModel;
        List<ItemModel> itemModelList = new ArrayList<>();

        for (String[] item :dummyItems) {
            itemModel = new ItemModel();
            itemModel.setItem(item[0]);
            itemModel.setPrice(Integer.parseInt(item[1]));
            itemModel.setPicture(item[2]);
            itemModel.setQuantity(Integer.parseInt(item[3]));
            itemModel.setDiscount(Integer.parseInt(item[4]));
            itemModel.setPriceBeforeDiscount(Integer.parseInt(item[5]));
            itemModel.setPriceDiscount(Integer.parseInt(item[6]));
            itemModel.setTotalPrice(Integer.parseInt(item[7]));
            itemModel.setCategory(item[8]);

            itemModelList.add(itemModel);
        }

        return itemModelList;
    }
}
