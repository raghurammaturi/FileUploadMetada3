/**
* The program serves as an Entity.
* @author  Raghuram Maturi
* @version 1.0
* @since   2017-04-01 
*/
package com.application.springbootstart.filedata;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Filedata")
public class FileData {
	
	@Id
	private String fileID;
	
	@Column(name="fileName")
	private String fileName;
	
	@Column(name="creationDt")
	private Date creationDt;
	
	@Column(name="fileTitle")
	private String fileTitle;
	
	 
	public String getFileTitle() {
		return fileTitle;
	}
	public void setFileTitle(String fileTitle) {
		this.fileTitle = fileTitle;
	}
	public Date getCreationDt() {
		return creationDt;
	}
	public void setCreationDt(Date creationDt) {
		this.creationDt = creationDt;
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
