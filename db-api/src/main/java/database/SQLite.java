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

    public static JSONObject getObject(String id) throws SQLException, ClassNotFoundException, IOException {
        JSONObject object = new JSONObject();
        JSONObject location = new JSONObject();
        JSONObject potter = new JSONObject();
        JSONObject form = new JSONObject();
        Class.forName(DBDRIVER);
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:" + DBFILE)) {
            try (Statement stmt = c.createStatement()) {
                String sql = "SELECT o.id AS o_id, o.img AS o_img, o.name AS o_name, o.database AS o_database, p.id AS p_id, p.name AS p_name, l.id AS l_id, l.name AS l_name, l.lat AS l_lat, l.lon AS l_lon, l.geonames AS l_geonames, f.id AS f_id, f.img AS f_img, f.ls AS f_ls FROM object AS o, location AS l, potter AS p, form AS f WHERE o.id = " + id + " AND o.fk_location=l.id AND o.fk_potter=p.id AND o.fk_form=f.id";
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        object.put("id", Integer.parseInt(rs.getString("o_id")));
                        object.put("name", rs.getString("o_name"));
                        object.put("img", rs.getString("o_img"));
                        object.put("database", rs.getString("o_database"));
                        location.put("id", Integer.parseInt(rs.getString("l_id")));
                        location.put("name", rs.getString("l_name"));
                        location.put("lat", Double.parseDouble(rs.getString("l_lat")));
                        location.put("lon", Double.parseDouble(rs.getString("l_lon")));
                        location.put("geonames", rs.getString("l_geonames"));
                        object.put("location", location);
                        potter.put("id", Integer.parseInt(rs.getString("p_id")));
                        potter.put("name", rs.getString("p_name"));
                        object.put("potter", potter);
                        form.put("id", Integer.parseInt(rs.getString("f_id")));
                        form.put("img", rs.getString("f_img"));
                        form.put("ls", rs.getString("f_ls"));
                        // TODO get info from labeling system
                        // resolve mapping
                        object.put("form", form);
                    }
                }
            }
        } catch (Exception e) {
            throw new NullPointerException("cannot get object. " + e.toString());
        }
        return object;
    }

    public static JSONArray getObjects() throws SQLException, ClassNotFoundException, IOException {
        JSONArray objects = new JSONArray();
        Class.forName(DBDRIVER);
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:" + DBFILE)) {
            try (Statement stmt = c.createStatement()) {
                String sql = "SELECT o.id AS o_id, o.img AS o_img, o.name AS o_name, o.database AS o_database, p.id AS p_id, p.name AS p_name, l.id AS l_id, l.name AS l_name, l.lat AS l_lat, l.lon AS l_lon, l.geonames AS l_geonames, f.id AS f_id, f.img AS f_img, f.ls AS f_ls FROM object AS o, location AS l, potter AS p, form AS f WHERE o.fk_location=l.id AND o.fk_potter=p.id AND o.fk_form=f.id";
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        JSONObject object = new JSONObject();
                        JSONObject location = new JSONObject();
                        JSONObject potter = new JSONObject();
                        JSONObject form = new JSONObject();
                        object.put("id", Integer.parseInt(rs.getString("o_id")));
                        object.put("name", rs.getString("o_name"));
                        object.put("img", rs.getString("o_img"));
                        object.put("database", rs.getString("o_database"));
                        location.put("id", Integer.parseInt(rs.getString("l_id")));
                        location.put("name", rs.getString("l_name"));
                        location.put("lat", Double.parseDouble(rs.getString("l_lat")));
                        location.put("lon", Double.parseDouble(rs.getString("l_lon")));
                        location.put("geonames", rs.getString("l_geonames"));
                        object.put("location", location);
                        potter.put("id", Integer.parseInt(rs.getString("p_id")));
                        potter.put("name", rs.getString("p_name"));
                        object.put("potter", potter);
                        form.put("id", Integer.parseInt(rs.getString("f_id")));
                        form.put("img", rs.getString("f_img"));
                        form.put("ls", rs.getString("f_ls"));
                        // TODO get info from labeling system
                        // resolve mapping
                        object.put("form", form);
                        objects.add(object);
                    }
                }
            }
        } catch (Exception e) {
            throw new NullPointerException("cannot get objects. " + e.toString());
        }
        return objects;
    }

    public static JSONArray getObjectsWithDatabase(String database) throws SQLException, ClassNotFoundException, IOException {
        JSONArray objects = new JSONArray();
        Class.forName(DBDRIVER);
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:" + DBFILE)) {
            try (Statement stmt = c.createStatement()) {
                String sql = "SELECT o.id AS o_id, o.img AS o_img, o.name AS o_name, o.database AS o_database, p.id AS p_id, p.name AS p_name, l.id AS l_id, l.name AS l_name, l.lat AS l_lat, l.lon AS l_lon, l.geonames AS l_geonames, f.id AS f_id, f.img AS f_img, f.ls AS f_ls FROM object AS o, location AS l, potter AS p, form AS f WHERE o.database = '" + database + "' AND o.fk_location=l.id AND o.fk_potter=p.id AND o.fk_form=f.id";
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        JSONObject object = new JSONObject();
                        JSONObject location = new JSONObject();
                        JSONObject potter = new JSONObject();
                        JSONObject form = new JSONObject();
                        object.put("id", Integer.parseInt(rs.getString("o_id")));
                        object.put("name", rs.getString("o_name"));
                        object.put("img", rs.getString("o_img"));
                        object.put("database", rs.getString("o_database"));
                        location.put("id", Integer.parseInt(rs.getString("l_id")));
                        location.put("name", rs.getString("l_name"));
                        location.put("lat", Double.parseDouble(rs.getString("l_lat")));
                        location.put("lon", Double.parseDouble(rs.getString("l_lon")));
                        location.put("geonames", rs.getString("l_geonames"));
                        object.put("location", location);
                        potter.put("id", Integer.parseInt(rs.getString("p_id")));
                        potter.put("name", rs.getString("p_name"));
                        object.put("potter", potter);
                        form.put("id", Integer.parseInt(rs.getString("f_id")));
                        form.put("img", rs.getString("f_img"));
                        form.put("ls", rs.getString("f_ls"));
                        // TODO get info from labeling system
                        // resolve mapping
                        object.put("form", form);
                        objects.add(object);
                    }
                }
            }
        } catch (Exception e) {
            throw new NullPointerException("cannot get objects. " + e.toString());
        }
        return objects;
    }

    public static JSONObject getPotter(String id) throws SQLException, ClassNotFoundException, IOException {
        JSONObject potter = new JSONObject();
        Class.forName(DBDRIVER);
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:" + DBFILE)) {
            try (Statement stmt = c.createStatement()) {
                String sql = "SELECT * FROM potter WHERE id = " + id;
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        potter.put("id", Integer.parseInt(rs.getString("id")));
                        potter.put("name", rs.getString("name"));
                    }
                }
            }
        } catch (Exception e) {
            throw new NullPointerException("cannot get potter. " + e.toString());
        }
        return potter;
    }

    public static JSONArray getPotters() throws SQLException, ClassNotFoundException, IOException {
        JSONArray potters = new JSONArray();
        Class.forName(DBDRIVER);
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:" + DBFILE)) {
            try (Statement stmt = c.createStatement()) {
                String sql = "SELECT * FROM potter";
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        JSONObject potter = new JSONObject();
                        potter.put("id", Integer.parseInt(rs.getString("id")));
                        potter.put("name", rs.getString("name"));
                        potters.add(potter);
                    }
                }
            }
        } catch (Exception e) {
            throw new NullPointerException("cannot get potters. " + e.toString());
        }
        return potters;
    }

    public static JSONObject getLocation(String id) throws SQLException, ClassNotFoundException, IOException {
        JSONObject location = new JSONObject();
        Class.forName(DBDRIVER);
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:" + DBFILE)) {
            try (Statement stmt = c.createStatement()) {
                String sql = "SELECT * FROM location WHERE id = " + id;
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        location.put("id", Integer.parseInt(rs.getString("id")));
                        location.put("name", rs.getString("name"));
                        location.put("lat", Double.parseDouble(rs.getString("lat")));
                        location.put("lon", Double.parseDouble(rs.getString("lon")));
                        location.put("geonames", rs.getString("geonames"));
                    }
                }
            }
        } catch (Exception e) {
            throw new NullPointerException("cannot get location. " + e.toString());
        }
        return location;
    }

    public static JSONArray getLocations() throws SQLException, ClassNotFoundException, IOException {
        JSONArray locations = new JSONArray();
        Class.forName(DBDRIVER);
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:" + DBFILE)) {
            try (Statement stmt = c.createStatement()) {
                String sql = "SELECT * FROM location";
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    while (rs.next()) {
                        JSONObject location = new JSONObject();
                        location.put("id", Integer.parseInt(rs.getString("id")));
                        location.put("name", rs.getString("name"));
                        location.put("lat", Double.parseDouble(rs.getString("lat")));
                        location.put("lon", Double.parseDouble(rs.getString("lon")));
                        location.put("geonames", rs.getString("geonames"));
                        locations.add(location);
                    }
                }
            }
        } catch (Exception e) {
            throw new NullPointerException("cannot get locations. " + e.toString());
        }
        return locations;
    }

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
