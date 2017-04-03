/**
* The program implements an application that
* provides various services like uploading, downloading the files and inserting the meta data information of the file
*  to the database postgresSQL. 
* @author  Raghuram Maturi
* @version 1.0
* @since   2017-04-01 
*/

package com.application.springbootstart.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.application.springbootstart.dto.FileDto;
import com.application.springbootstart.mail.SmtpMailSender;
import com.application.springbootstart.service.FileService;


@EnableScheduling
@RestController
@EnableAutoConfiguration
public class FileController {

	@Autowired
	FileService fileService;
	
	@Autowired
	SmtpMailSender smtpMailSender;
	
	private Response response = null;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss"); 
	private final Logger logger = LoggerFactory.getLogger(this.getClass());	
	/**
	 * Upload Service.
	 * This addMetaDataFile method will add the JSON meta data into the file_data table.
	 * This addMetaDataFile method will add file to the shared location under the folder FileID.FileID folder will be created if doesn't exists.
	 * Input JSON format ex { "fileID" : "102","fileName" : "john","creationDt" : "2017-01-04 14:50:00.00","file": "C://Users//rmaturi//Downloads//Jboss classpath.txt"}  
	 * @param  upload  
	 * @return response which contains the Row entity which is added.
	 */
	@RequestMapping(method=RequestMethod.POST, value="/upload", consumes = "application/*")
	public Response addMetaDataFile(@RequestBody FileDto filedto) {
		logger.info("Adding meta data fields and file Service called:");
		response = fileService.addMetaDataFile(filedto);
		return response;
	}
	
	
	/**
	 * metadataInfo/{fileId} Service.
	 * This method will get the meta data information in the JSON format.
	 * This method will take the input parameter as fileID.
	 * @param  Filedata/{fileId}  
	 * @return response which contains the meta data entity of a fileID.
	 */
	 @RequestMapping(method=RequestMethod.GET, value="/metadataInfo/{fileId}")     	    	    	
	 public Response getFileInfo( @PathVariable("fileId") String fileId) {
		 logger.info("Get meta data fields of File ID Service called:");	
		 logger.info("fileID --------------   "+fileId);
		 return fileService.getFileInfo(fileId);    		       
	 } 
	 
	 /**
		 * download Service.
		 * This method will get download the file with a particular fileID.
		 * This method will take the input parameter as fileID.
		 * @param  /files/{fileId}  
		 * @return ResponseEntity  which contains the content stream of the fileID.
	 */
	 @RequestMapping(value = "/download/{fileId}", method = RequestMethod.GET)	   
     public ResponseEntity<ByteArrayResource> getFile(@PathVariable("fileId") String fileId) throws IOException {
		 logger.info("Download file Service called:");
		 logger.info("fileID --------------   "+fileId);
		 return fileService.getFile(fileId);
		 //return  fileService.getFile(fileId);   	     
     }
   	 
	 
	 /**
		 * reportLastHourFileIDS Service.
		 * This method will poll the last hour fileIDs that are newly added.
		 * This method will send the Email Notification of all the fileID's  that are newly added in last hour.
		 * @return Response list of fileID's.
	 */
	 @Scheduled(fixedRate = 36000)
	 public Response reportLastHourFileIDS() {  
		 System.out.println("reportCurrentTime");
		 return fileService.getLastHourFileIds();
	 }	
	 
	 /**
		 * searchFileID Service.
		 * This method will search for the FileID's with a search Criteria. 
		 * @return Response list of fileID's.
	 */	 
	 @RequestMapping(value = "/search/{fileName}", method = RequestMethod.GET)	
	 public Response searchFileID(@PathVariable("fileName") String  fileName) {
		 logger.info("search FileIDS serivce called");
		 return fileService.searchFileIDList(fileName);
	 }

	 
}
