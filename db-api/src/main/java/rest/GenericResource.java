package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import database.SQLite;
import utils.Logging;
import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.jdom.JDOMException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@Path("/")
public class GenericResource {

    @GET
    @Path("/objects")
    public Response getObjects(@HeaderParam("Accept") String acceptHeader, @QueryParam("database") String database) throws IOException, JDOMException, ParserConfigurationException, TransformerException {
        try {
            JSONArray objects;
            if (database == null) {
                objects = SQLite.getObjects();
            } else {
                objects = SQLite.getObjectsWithDatabase(database);
            }
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return Response.ok(gson.toJson(objects)).header("Access-Control-Allow-Origin", "*").header("Content-Type", "application/json;charset=UTF-8").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Logging.getMessageJSON(e, "rest.GenericResource")).
                    header("Access-Control-Allow-Origin", "*").header("Content-Type", "application/json;charset=UTF-8").build();
        }
    }

    @GET
    @Path("/objects/{id}")
    public Response getObject(@HeaderParam("Accept") String acceptHeader,
            @PathParam("id") String itemID) throws IOException, JDOMException, ParserConfigurationException, TransformerException {
        try {
            JSONObject object = SQLite.getObject(itemID);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return Response.ok(gson.toJson(object)).header("Access-Control-Allow-Origin", "*").header("Content-Type", "application/json;charset=UTF-8").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Logging.getMessageJSON(e, "rest.GenericResource")).
                    header("Access-Control-Allow-Origin", "*").header("Content-Type", "application/json;charset=UTF-8").build();
        }
    }

    @GET
    @Path("/locations")
    public Response getLocations(@HeaderParam("Accept") String acceptHeader) throws IOException, JDOMException, ParserConfigurationException, TransformerException {
        try {
            JSONArray locations = SQLite.getLocations();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return Response.ok(gson.toJson(locations)).header("Access-Control-Allow-Origin", "*").header("Content-Type", "application/json;charset=UTF-8").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Logging.getMessageJSON(e, "rest.GenericResource")).
                    header("Access-Control-Allow-Origin", "*").header("Content-Type", "application/json;charset=UTF-8").build();
        }
    }

    @GET
    @Path("/locations/{id}")
    public Response getLocation(@HeaderParam("Accept") String acceptHeader,
            @PathParam("id") String itemID) throws IOException, JDOMException, ParserConfigurationException, TransformerException {
        try {
            JSONObject location = SQLite.getLocation(itemID);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return Response.ok(gson.toJson(location)).header("Access-Control-Allow-Origin", "*").header("Content-Type", "application/json;charset=UTF-8").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Logging.getMessageJSON(e, "rest.GenericResource")).
                    header("Access-Control-Allow-Origin", "*").header("Content-Type", "application/json;charset=UTF-8").build();
        }
    }

    @GET
    @Path("/potters")
    public Response getPotters(@HeaderParam("Accept") String acceptHeader) throws IOException, JDOMException, ParserConfigurationException, TransformerException {
        try {
            JSONArray potters = SQLite.getPotters();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return Response.ok(gson.toJson(potters)).header("Access-Control-Allow-Origin", "*").header("Content-Type", "application/json;charset=UTF-8").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Logging.getMessageJSON(e, "rest.GenericResource")).
                    header("Access-Control-Allow-Origin", "*").header("Content-Type", "application/json;charset=UTF-8").build();
        }
    }

    @GET
    @Path("/potters/{id}")
    public Response getPotter(@HeaderParam("Accept") String acceptHeader,
            @PathParam("id") String itemID) throws IOException, JDOMException, ParserConfigurationException, TransformerException {
        try {
            JSONObject potter = SQLite.getPotter(itemID);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return Response.ok(gson.toJson(potter)).header("Access-Control-Allow-Origin", "*").header("Content-Type", "application/json;charset=UTF-8").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Logging.getMessageJSON(e, "rest.GenericResource")).
                    header("Access-Control-Allow-Origin", "*").header("Content-Type", "application/json;charset=UTF-8").build();
        }
    }

    @GET
    @Path("/forms")
    public Response getForms(@HeaderParam("Accept") String acceptHeader) throws IOException, JDOMException, ParserConfigurationException, TransformerException {
        try {
            JSONArray forms = SQLite.getForms();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return Response.ok(gson.toJson(forms)).header("Access-Control-Allow-Origin", "*").header("Content-Type", "application/json;charset=UTF-8").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Logging.getMessageJSON(e, "rest.GenericResource")).
                    header("Access-Control-Allow-Origin", "*").header("Content-Type", "application/json;charset=UTF-8").build();
        }
    }

    @GET
    @Path("/forms/{id}")
    public Response getForms(@HeaderParam("Accept") String acceptHeader,
            @PathParam("id") String itemID) throws IOException, JDOMException, ParserConfigurationException, TransformerException {
        try {
            JSONObject form = SQLite.getForm(itemID);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return Response.ok(gson.toJson(form)).header("Access-Control-Allow-Origin", "*").header("Content-Type", "application/json;charset=UTF-8").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Logging.getMessageJSON(e, "rest.GenericResource")).
                    header("Access-Control-Allow-Origin", "*").header("Content-Type", "application/json;charset=UTF-8").build();
        }
    }

}
