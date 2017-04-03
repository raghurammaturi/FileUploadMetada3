/**
* The program serves as an DTO Access Layers
* @author  Raghuram Maturi
* @version 1.0
* @since   2017-04-01 
*/
package com.application.springbootstart.dto;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileDto {
	
	private String fileID;
	private String fileName;
	private String  creationDt;
	private File file;
	private String fileTitle;
	
	public String getFileTitle() {
		return fileTitle;
	}
	public void setFileTitle(String fileTitle) {
		this.fileTitle = fileTitle;
	}
	public String getCreationDt() {
		return creationDt;
	}
	public void setCreationDt(String creationDt) {
		this.creationDt = creationDt;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getFileID() {
		return fileID;
	}
	public void setFileID(String fileID) {
		this.fileID = fileID;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
