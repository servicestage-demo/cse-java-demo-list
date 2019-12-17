package org.apache.servicecomb.samples.face;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	public String face(MultipartFile file);

	public String addface(MultipartFile file, String external_image_id);

	public String delface(String id);
}
