package database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class SQLite {

    private static final String DBDRIVER = "org.sqlite.JDBC";
    private static final String DBFILE = "C:/tmp/caa-hd.sqlite";
    private static final String LSHOST = "http://143.93.114.135";

    public static JSONObject getForm(String id) throws SQLException, ClassNotFoundException, IOException {
        JSONObject form = new JSONObject();
        Class.forName(DBDRIVER);
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:" + DBFILE)) {
            try (Statement stmt = c.createStatement()) {
                String sql = "SELECT * FROM form WHERE id = " + id;
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        form.put("id", Integer.parseInt(rs.getString("id")));
                        form.put("img", rs.getString("img"));
                        String ls = rs.getString("ls");
                        form.put("ls", ls);
                        String[] lsSplit = ls.split("/");
                        String lsID = lsSplit[lsSplit.length - 1];
                        String url_string = LSHOST + "/api/v1/labels/" + lsID + "?equalConcepts=true";
                        URL url = new URL(url_string);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestProperty("Accept", "application/json");
                        conn.setRequestProperty("Accept-Encoding", "*");
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        String inputLine;
                        StringBuilder response = new StringBuilder();
                        while ((inputLine = br.readLine()) != null) {
                            response.append(inputLine);
                        }
                        br.close();
                        JSONObject jsonObject = (JSONObject) new JSONParser().parse(response.toString());
                        JSONArray equalConcepts = (JSONArray) jsonObject.get("equalConcepts");
                        //form.put("ls-entry", jsonObject);
                        form.put("name", jsonObject.get("thumbnail"));
                        JSONArray equalNames = new JSONArray();
                        Map<String, String> map = new HashMap<String, String>();
                        for (Object item : equalConcepts) {
                            JSONObject obj = (JSONObject) item;
                            map.put(obj.get("id").toString(), obj.get("thumbnail").toString());
                        }
                        Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
                        while (iterator.hasNext()) {
                            Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();
                            JSONObject obj = new JSONObject();
                            obj.put("uri", LSHOST + "/item/label/" + entry.getKey());
                            obj.put("name", entry.getValue());
                            equalNames.add(obj);
                        }
                        form.put("equals", equalNames);
                    }
                }
            }
        } catch (Exception e) {
            throw new NullPointerException("cannot get form. " + e.toString());
        }
        return form;
    }

    public static JSONArray getForms() throws SQLException, ClassNotFoundException, IOException {
        JSONArray forms = new JSONArray();
        Class.forName(DBDRIVER);
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:" + DBFILE)) {
            try (Statement stmt = c.createStatement()) {
                String sql = "SELECT * FROM form";
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        JSONObject form = new JSONObject();
                        form.put("id", Integer.parseInt(rs.getString("id")));
                        form.put("img", rs.getString("img"));
                        String ls = rs.getString("ls");
                        form.put("ls", ls);
                        String[] lsSplit = ls.split("/");
                        String lsID = lsSplit[lsSplit.length - 1];
                        String url_string = LSHOST + "/api/v1/labels/" + lsID + "?equalConcepts=true";
                        URL url = new URL(url_string);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestProperty("Accept", "application/json");
                        conn.setRequestProperty("Accept-Encoding", "*");
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        String inputLine;
                        StringBuilder response = new StringBuilder();
                        while ((inputLine = br.readLine()) != null) {
                            response.append(inputLine);
                        }
                        br.close();
                        JSONObject jsonObject = (JSONObject) new JSONParser().parse(response.toString());
                        JSONArray equalConcepts = (JSONArray) jsonObject.get("equalConcepts");
                        //form.put("ls-entry", jsonObject);
                        form.put("name", jsonObject.get("thumbnail"));
                        JSONArray equalNames = new JSONArray();
                        Map<String, String> map = new HashMap<String, String>();
                        for (Object item : equalConcepts) {
                            JSONObject obj = (JSONObject) item;
                            map.put(obj.get("id").toString(), obj.get("thumbnail").toString());
                        }
                        Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
                        while (iterator.hasNext()) {
                            Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();
                            JSONObject obj = new JSONObject();
                            obj.put("uri", LSHOST + "/item/label/" + entry.getKey());
                            obj.put("name", entry.getValue());
                            equalNames.add(obj);
                        }
                        form.put("equals", equalNames);
                        forms.add(form);
                    }
                }
            }
        } catch (Exception e) {
            throw new NullPointerException("cannot get forms. " + e.toString());
        }
        return forms;
    }

}
