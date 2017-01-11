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
    private static final String IMGFOLDER = "C:/tmp/forms/";
    private static final String LSHOST = "http://143.93.114.135";

    public static JSONObject getForm(String id) throws SQLException, ClassNotFoundException, IOException {
        JSONObject form = new JSONObject();
        Class.forName(DBDRIVER);
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:" + DBFILE)) {
            try (Statement stmt = c.createStatement()) {
                String sql = "SELECT * FROM form WHERE id = " + id;
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        if (rs.getString("img").contains("null")) {
                            form.put("img", "");
                        } else {
                            String[] imgsplit = rs.getString("img").split(";");
                            if (imgsplit.length == 2) {
                                form.put("img", IMGFOLDER + imgsplit[0] + ";" + IMGFOLDER + imgsplit[1]);
                            } else {
                                form.put("img", IMGFOLDER + rs.getString("img"));
                            }
                        }
                        form.put("localname", rs.getString("localname"));
                        String ls = rs.getString("ls");
                        form.put("concept", ls);
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
                        form.put("label", jsonObject.get("thumbnail"));
                        if (jsonObject.get("description") != null) {
                            form.put("description", jsonObject.get("description"));
                        } else {
                            form.put("description", "");
                        }
                        JSONArray equalNames = new JSONArray();
                        Map<String, String> map = new HashMap<String, String>();
                        for (Object item : equalConcepts) {
                            JSONObject obj = (JSONObject) item;
                            map.put(obj.get("id").toString(), obj.get("thumbnail").toString() + ";" + form.get("description"));
                        }
                        Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
                        while (iterator.hasNext()) {
                            Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();
                            JSONObject obj = new JSONObject();
                            String[] valuesplit = entry.getValue().split(";");
                            obj.put("concept", LSHOST + "/item/label/" + entry.getKey());
                            if (valuesplit.length == 1) {
                                obj.put("label", valuesplit[0]);
                                obj.put("description", "");
                            } else {
                                obj.put("label", valuesplit[0]);
                                obj.put("description", valuesplit[1]);
                            }
                            // get data from database
                            String sql2 = "SELECT * FROM form WHERE ls LIKE \"%" + entry.getKey() + "%\"";
                            try (ResultSet rs2 = stmt.executeQuery(sql2)) {
                                while (rs2.next()) {
                                    if (rs.getString("img").contains("null")) {
                                        obj.put("img", "");
                                    } else {
                                        obj.put("img", IMGFOLDER + rs.getString("img"));
                                    }
                                    obj.put("localname", rs.getString("localname"));
                                }
                            } catch (Exception e) {
                                throw new NullPointerException("cannot get form. " + e.toString());
                            }
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
                        if (rs.getString("img").contains("null")) {
                            form.put("img", "");
                        } else {
                            form.put("img", IMGFOLDER + rs.getString("img"));
                        }
                        form.put("localname", rs.getString("localname"));
                        form.put("concept", rs.getString("ls"));
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
