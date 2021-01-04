package com.company;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String x[]) throws IOException {

        String review = "C:\\piotr\\Studia\\Magisterka\\Data Warehouses\\Project\\databases\\yelp\\yelp_academic_dataset_review.json";
        String checkin = "C:\\piotr\\Studia\\Magisterka\\Data Warehouses\\Project\\databases\\yelp\\yelp_academic_dataset_checkin.json";
        String user = "C:\\piotr\\Studia\\Magisterka\\Data Warehouses\\Project\\databases\\yelp\\yelp_academic_dataset_user.json";
        String business = "C:\\piotr\\Studia\\Magisterka\\Data Warehouses\\Project\\databases\\yelp\\yelp_academic_dataset_business.json";

        FileWriter writer1 = new FileWriter("Review.json");
        JSONArray jsonReviews = null;
        try {
            jsonReviews = readReview(new File(review), "UTF-8");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONArray.writeJSONString(jsonReviews, writer1);
        writer1.close();

//        FileWriter writer2 = new FileWriter("Checkin.json");
//        JSONArray jsonCheckin = null;
//        try {
//            jsonCheckin = read(new File(checkin), "UTF-8");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        JSONArray.writeJSONString(jsonCheckin, writer2);
//        writer2.close();
//        System.gc();
//
//        FileWriter writer3 = new FileWriter("User.json");
//        JSONArray jsonUser = null;
//        try {
//            jsonUser = read(new File(user), "UTF-8");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        FileWriter writer4 = new FileWriter("Business.json");
//        JSONArray.writeJSONString(jsonUser, writer3);
//        writer3.close();
//        System.gc();
//
//        JSONArray jsonBuisness = null;
//        try {
//            jsonBuisness = read(new File(business), "UTF-8");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        System.gc();
//        JSONArray.writeJSONString(jsonBuisness, writer4);
//        writer4.close();
    }

    public static JSONArray readReview(File MyFile, String Encoding) throws FileNotFoundException, ParseException {
        Scanner scn = new Scanner(MyFile, Encoding);
        JSONArray json = new JSONArray();
        int i = 0;
        while (scn.hasNext() && i < 2000000) {
            JSONObject obj = (JSONObject) new JSONParser().parse(scn.nextLine());
            if (obj.get("text").toString().contains(";")) {
                obj.put("text", obj.get("text").toString().replaceAll(";", ","));
            }
            json.add(obj);

            if (i % 10000 == 0)
                System.out.println(MyFile.getName() + " " + i + " ");
            i++;
        }
        return json;
    }

    public static JSONArray read(File MyFile, String Encoding) throws FileNotFoundException, ParseException {
        Scanner scn = new Scanner(MyFile, Encoding);
        JSONArray json = new JSONArray();
        int i = 0;
        while (scn.hasNext()) {
            JSONObject obj = (JSONObject) new JSONParser().parse(scn.nextLine());
            json.add(obj);

            if (i % 10000 == 0)
                System.out.println(MyFile.getName() + " " + i);
            i++;
        }
        return json;
    }

}
