/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.servicecomb.samples.gc;

import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * {@link GCRestEndpoint} provides the rest implementation of
 * {@link GCEndpoint}. The rest endpoint is accessed by /gc with HTTP POST.
 */
@RestSchema(schemaId = "GCRestEndpoint")
@RequestMapping(path = "/")
public class GCRestEndpoint implements GCEndpoint {
	private final FileService fileService;

	@Autowired
	public GCRestEndpoint(FileService fileService) {
		this.fileService = fileService;
	}

	@PostMapping(path = "/gc", produces = MediaType.TEXT_PLAIN_VALUE)
	public String gc(@RequestPart(name = "file") MultipartFile file) {
		return fileService.uploadFile(file);
	}
}
