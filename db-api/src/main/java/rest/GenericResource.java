package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import database.SQLite;
import utils.Logging;
import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
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
    @Path("/forms")
    public Response getForms(@HeaderParam("Accept") String acceptHeader) throws IOException, JDOMException, ParserConfigurationException, TransformerException {
        try {
            JSONArray forms = SQLite.getForms();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return Response.ok(gson.toJson(forms)).header("Content-Type", "application/json;charset=UTF-8").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Logging.getMessageJSON(e, "rest.GenericResource")).
                    header("Content-Type", "application/json;charset=UTF-8").build();
        }
    }

    @GET
    @Path("/forms/{id}")
    public Response getForms(@HeaderParam("Accept") String acceptHeader,
            @PathParam("id") String itemID) throws IOException, JDOMException, ParserConfigurationException, TransformerException {
        try {
            JSONObject form = SQLite.getForm(itemID);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return Response.ok(gson.toJson(form)).header("Content-Type", "application/json;charset=UTF-8").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Logging.getMessageJSON(e, "rest.GenericResource")).
                    header("Content-Type", "application/json;charset=UTF-8").build();
        }
    }

}
