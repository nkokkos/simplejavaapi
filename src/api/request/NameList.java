/*@author NikosK
* Performs a json request to receive names information 
*/

package api.request;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.DateUtils;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import api.utils.SignatureCreator;
import org.apache.http.impl.client.HttpClientBuilder;
import com.google.gson.*;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;

import java.util.*;

public class DeviceList {
    
final private String url    =    "";  // this is the base of the host address
final private String suburl =    "";  // points to the suburl of the api    

private String access_id;

// don't use secretkey for the time being
// private String secretKey = "[ server secret key ]";

public void getList() throws URISyntaxException, ClientProtocolException, IOException, JsonIOException,JsonParseException  {
	
  HttpGet getRequest = new HttpGet();   
  HttpClient httpclient = HttpClientBuilder.create().build();
  getRequest.setURI(new URI(url + suburl));
  setHeaders(getRequest);
  HttpResponse response = httpclient.execute(getRequest);
 
  String responseString = EntityUtils.toString(response.getEntity());
 
  JsonParser  parser = new JsonParser();
  JsonElement jelement = parser.parse(responseString);
  
  if (!jelement.isJsonArray()) { // this means we are getting a single json record
    System.out.println(responseString);
  }
  else {   
    JsonArray array = jelement.getAsJsonArray();
    //print json response:
    System.out.println(array.toString());
    System.out.println("----------------------------");
  
  // print data in pretty format:  
  // get the data from the JsonObject with jObject.get("id")
  if (array.size() > 0 && jelement.isJsonArray() ) {
    for(int x = 0; x < array.size(); x++) {
      JsonObject jObject = array.get(x).getAsJsonObject();
       System.out.println("id: " + jObject.get("id"));
       System.out.println("name: "+ jObject.get("name"));
       System.out.println("created_at: " + jObject.get("created_at"));
       System.out.println("updated_at: " + jObject.get("updated_at"));
       System.out.println("---");
    }  
  }
  }
}
public void setAccessId(String access_id_param) {
  access_id = access_id_param;  
}        

private void setHeaders(HttpUriRequest request) {
  
  // Don't use any signature building for the time being: 
  // SignatureCreator sc = new SignatureBuilder();
  // request.setHeader(new BasicHeader("Date", DateUtils.formatDate(new Date()).replaceFirst("[+]00:00$", "")));	
  request.setHeader(new BasicHeader("Content-Type", "application/json"));
  request.setHeader("Authorization", access_id); 
 }

public static void main(String[] args) throws URISyntaxException, ClientProtocolException, IOException {
  
  NameList nameList = new NameList();
  
  //set access_id for authentication
  String myAccessId = "accessidkey";
  
  nameList.setAccessId(myAccessId);
  nameList.getList();
 }

}

