/**
* The program serves as an Repository.
* @author  Raghuram Maturi
* @version 1.0
* @since   2017-04-01 
*/
package com.application.springbootstart.repository;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.application.springbootstart.filedata.FileData;

public interface FileRepository extends CrudRepository<FileData,String> {
	
	FileData findByFileID(@Param("fileID") String fileID);	
	FileData findByCreationDt(@Param("creationDt") String creationDt);
	List<FileData> findByFileName(@Param("fileName") String fileName);
	/*FileData findByFileTag(@Param("fileTag") String fileTag);
	FileData findBycreationDt(@Param("creationDt") String creationDt);
	FileData findByFileOwnerName(@Param("fileOwnerName") String fileOwnerName);	*/

}
