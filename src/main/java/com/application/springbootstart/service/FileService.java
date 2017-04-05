/**
* The program provides different services.
* @author  Raghuram Maturi
* @version 1.0
* @since   2017-04-01 
*/

package com.application.springbootstart.service;


import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.ws.rs.core.Response;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.application.springbootstart.dto.FileDto;
import com.application.springbootstart.dto.FileDtoOutput;
import com.application.springbootstart.filedata.FileData;
import com.application.springbootstart.mail.SmtpMailSender;
import com.application.springbootstart.properties.ApplicationProperties;
import com.application.springbootstart.repository.FileRepository;

@Service
public class FileService {				
    //private static String UPLOADED_FOLDER = "C://temp//";    
    private SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");		
    private Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("EST"));
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
	@Autowired
	FileRepository fileRepository;	
	
	@Autowired
	SmtpMailSender sendMail;
	
	FileDtoOutput fileDataOutput = new FileDtoOutput();
	
	ApplicationProperties applicationProp;
		
	@Autowired
	public void setApplicationProp(ApplicationProperties applicationProp) {
		this.applicationProp = applicationProp;
	}

	/**
	 * This method inserts meta data fields of a file to the Entity table and also
	 * uploaded the file to the provided shared location under the folder with name fileID.
	 * @param fileDto
	 * @return	filedata with the meta data information file which is uploaded.
	 */
	public Response addMetaDataFile(FileDto fileDto) {
		Calendar calendar1 = Calendar.getInstance(TimeZone.getTimeZone("EST"));
		FileData filedata = new FileData();
				
		logger.info(" Adding Json request to the File Entity Table......  "+fileDto.getFileID());
		logger.info("application properties .............. "+applicationProp.getSharedLocation());
		try {
			if( (fileDto.getFileID()!= null)|| (fileDto.getFileID()!="null")  && ("".equals( fileDto.getFile() ))) {
			
			File folderPath = new File(applicationProp.getSharedLocation() +fileDto.getFileID());
			
	      	if (folderPath.exists()) {
	      		if (folderPath.isFile()) {
	      			logger.info(folderPath+" Exists and it is a file");
	      		} else if (folderPath.isDirectory()) {
	      			logger.info(folderPath+" Exists and it is a directory");
	      		}
	      	}else if(!folderPath.exists()) {
	      		logger.info(" Creating Directory: " + folderPath.getName());
	      		boolean result = false;
	
	      		try{
	      			folderPath.mkdir();
	      			result = true;
	      		} 
	      		catch(Exception ex){
	      			logger.info("Exception in Creating Directory: "+ex);
	      		}        
	      		if(result) {    
	      			logger.info("DIR created");  
	      		}
	      	}
	      	
	      	byte[] bytes = Files.readAllBytes(new File(fileDto.getFile().toString()).toPath());
	      	Path p = Paths.get(fileDto.getFile().toString().toString());
	      	String fileName = p.getFileName().toString();
	      	
	      	Path path = Paths.get(folderPath+"//"+ fileName);
	      	logger.info("Destination Path is:    "+path);
	        Files.write(path, bytes);
	        Date date = calendar1.getTime();	        
			filedata.setFileID(fileDto.getFileID());
			filedata.setFileName(fileName);
			filedata.setFileTitle(fileDto.getFileTitle());
			
			try {
				filedata.setCreationDt(date);
			} catch (Exception e) {			
				logger.info("Exception in Parsing the create Date.."+e);
			}			
			fileRepository.save(filedata);
		}else {
			return Response.status(400).entity("Please enter all the meta data fields of the file").build();
		}
	    } catch (IOException ie) {
	        System.out.println("Exception if adding meta data or uploading file.."+ie);
	        return Response.status(400).entity("Please enter all the meta data fields of the file").build();
	    } catch (NullPointerException ne) {
	        System.out.println("Exception if adding meta data or uploading file.."+ne);
	        return Response.status(400).entity("Please enter all the meta data fields of the file").build();
	    } catch (Exception e) {
	        System.out.println("Exception if adding meta data or uploading file.."+e);
	        return Response.status(400).entity("Please enter all the meta data fields of the file").build();
	    }  
		
		 return Response.status(201).entity(filedata).build();		
	}
	
	/**
	 * This method provides the meta data information of the fileID.
	 * 
	 * @param fileID	file ID. 
	 * @return			meta data information of the fileID.
	 */
	public Response getFileInfo(String fileID) {
		try {
	        FileData filedata = fileRepository.findByFileID(fileID);
	        if(filedata.getFileName()!=null) {
	        logger.info("File Name:  "+filedata.getFileName());        
	        	return Response.status(200).entity(filedata).build();
	        } else {
	        	return Response.status(400).entity("FileID Does not exists. Please enter valid FileID.").build();
	        }
		}catch (Exception e) {
			logger.info("Exception in getFileInfo() ...     "+e);
			return Response.status(400).entity("FileID Does not exists. Please enter valid FileID.").build();
		}
	}

	/**
	 * This method will download the file with the provided fileID.
	 * @param fileID
	 * @return	file of the fileID with the status.
	 */
	public ResponseEntity<ByteArrayResource> getFile(String fileID) {
		ByteArrayResource resource = null;
		FileData filedata = fileRepository.findByFileID(fileID);
		try {
			if (filedata.getFileName() != null) {
				String src = applicationProp.getSharedLocation() + fileID.concat("//" + filedata.getFileName());
				Path path = Paths.get(src);
				resource = new ByteArrayResource(Files.readAllBytes(path));
				return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/octet-stream"))
						.body(resource);
			} else {
				return ResponseEntity.badRequest().contentType(MediaType.parseMediaType("application/octet-stream"))
						.body(resource);
			}
		} catch (IOException e1) {
			logger.info(" Exception in reading bytes from the files..." + e1);
			return ResponseEntity.badRequest().contentType(MediaType.parseMediaType("application/octet-stream"))
					.body(resource);
		} catch (NullPointerException ne) {
			return ResponseEntity.badRequest().contentType(MediaType.parseMediaType("application/octet-stream"))
					.body(resource);
		} catch (Exception ex) {
			return ResponseEntity.badRequest().contentType(MediaType.parseMediaType("application/octet-stream"))
					.body(resource);
		}
		// ResponseEntity.ok().contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
	}
	
	/**
	 * This method will provide list of fileID's that are newly added in the last hour and also
	 *  will send email notification with the file ID's that are newly added in the last hour.
	 * @return JSON object with the fileID's.
	 */
	public Response getLastHourFileIds() {
		List<String> fileList = new ArrayList<String>();
		JSONObject obj = new JSONObject();
		JSONArray list = new JSONArray();
		
		for(FileData filedata: fileRepository.findAll()) {      	            

			logger.info("  filedata.getCreationDt()  "+filedata.getCreationDt());
			Date currentDate = getLastHourDate();
			try {
				logger.info("  sdf.format(currentDate ======   "  + sdf.format(currentDate));
				logger.info("  currentDate ======   "  + currentDate);
			} catch (Exception e) {				
				logger.info("Exception in parsing the currentDate.."+e);
			}
			
			logger.info("  fileCreated Date ===  " +filedata.getCreationDt());
			if(filedata.getCreationDt().after(currentDate)){				
				logger.info("getCreationDt" + filedata.getCreationDt() + " is after lasthour FileId's are  ....  "+filedata.getFileID());
                fileDataOutput.setFileID(filedata.getFileID());
                list.add(filedata.getFileID());
                fileList.add(filedata.getFileID());
            }else {
            	logger.info("Date1 is before Date");
            }
		}
		
        obj.put("fileID's", list);
        
        logger.info("String array elements are:    "+obj);
				
		try {
			sendMail.sendEmailNotification("test@gmail.com", "test", obj.toJSONString());
		} catch (Exception e) {
			logger.info("Exception in sending email.."+e);
		}
		return Response.status(201).entity(obj.toJSONString()).build();		
	}
	
	/**
	 * This method will provide the last hour date stamp from the current date time stamp.
	 * @return date last hour datetime.
	 */
	public Date getLastHourDate() {		
		calendar.add(Calendar.HOUR_OF_DAY, -1);
		Date date = calendar.getTime();		
		return date;
	}
	

	/**
	 * This method will search for the fileIDs with the search criteria.
	 * 	
	 * @param fileName   provides the fileName to be used in the search criteria.
	 */
	public Response searchFileIDList(String fileName) {
		logger.info(" ........................Searching fileid's by File Name...................."+fileName);
		List<String> fileList = new ArrayList<String>();
		JSONObject obj = new JSONObject();
		JSONArray list = new JSONArray();
		
		List<FileData> filedata = fileRepository.findByFileName(fileName);		
		for(FileData f: filedata) { 			
			logger.info("FileNames and FileID's are : "+f.getFileName()+ " , "+f.getFileID());
			list.add(f.getFileID());			 
		}
		obj.put("fileID's", list);
		return Response.status(201).entity(obj.toJSONString()).build();	
	}
	
}
