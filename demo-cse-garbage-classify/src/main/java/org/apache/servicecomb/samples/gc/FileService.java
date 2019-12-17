package org.apache.servicecomb.samples.gc;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	public String uploadFile(MultipartFile f);
}
