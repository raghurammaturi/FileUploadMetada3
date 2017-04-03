
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.sun.jersey.core.util.Base64;

public class MyClient {


    public static void main(String[] args)  
    {
        MyClient my_client = new MyClient();
        File file_upload = new File("C://Users//rmaturi//Downloads//bashscript.sh");
        //my_client.sendFileJSON(file_upload);
        try {
			//my_client.sendFileJSON();
        	//my_client.getFileMetadata("112");
			//my_client.getFile("112");
			my_client.searchFile("Sequence_Listing.raw");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }


    private void sendFileJSON() throws Exception{

    	Client client = Client.create();
        WebResource webResource = client.resource("http://127.0.0.1:8080/upload");
        
        
        JSONObject data_file = new JSONObject();
      /*  FileDto filedto = new FileDto();
        filedto.setFileId("101");
        filedto.setFileTag("elmer");
        filedto.setFileOwnerName("john");
        filedto.setFileName("smith");
        filedto.setCreationDt("3312017");   
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(filedto);
        System.out.println(" json ===  "+json);*/
        
        
       /* data_file.put("fileId", "Something about my file....");
        data_file.put("fileTag", "elmer");
        data_file.put("fileOwnerName", "john");
        data_file.put("fileName", "smith");
        data_file.put("creationDt", "3312017");*/
        
        data_file.put("fileID","112");
        data_file.put("file","C://Users//rmaturi//Downloads//Sequence_Listing.raw");
        data_file.put("fileTitle","tags");  
        
        //data_file.put("file", convertFileToString(file_upload));

        ClientResponse client_response = webResource.type("application/json").header("content_type", "{Content-Type:application/json}").accept(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, data_file.toString());

        System.out.println("Status: "+client_response.getStatus());

        client.destroy();

    }


    private void getFileMetadata(String fileId) throws JSONException, IOException{

        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        client.addFilter(new LoggingFilter());
        WebResource service = client.resource("http://127.0.0.1:8080/metadataInfo/103");
        ClientResponse client_response = service.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        System.out.println("Status: "+client_response.getStatus());
        client.destroy();

    }
    
    
    private void getFile(String fileId) throws JSONException, IOException{

        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        client.addFilter(new LoggingFilter());
        WebResource service = client.resource("http://127.0.0.1:8080/download/112");        

        ClientResponse client_response = service.accept(MediaType.APPLICATION_OCTET_STREAM).get(ClientResponse.class);

        System.out.println("Status: "+client_response.getStatus());

       // client.destroy();

    }
    
    
    private void searchFile(String fileId) throws JSONException, IOException{

        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        client.addFilter(new LoggingFilter());
        WebResource service = client.resource("http://127.0.0.1:8080/search/Sequence_Listing.raw");        

        ClientResponse client_response = service.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

        System.out.println("Status: "+client_response.getStatus());

       // client.destroy();

    }
    

}