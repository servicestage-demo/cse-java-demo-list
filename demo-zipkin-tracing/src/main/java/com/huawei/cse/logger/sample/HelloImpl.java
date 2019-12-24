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

package com.huawei.cse.logger.sample;

import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.apache.servicecomb.tracing.Span;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestSchema(schemaId = "hello")
@RequestMapping(path = "/")
public class HelloImpl {

	@Span
    @GetMapping(path = "/hello")
    public String hello() {
        return "Hello World!";
    }
	
	@Span(spanName = "say_method", callPath = "hello_say_path")
    @GetMapping(path = "/say")
    public String say() {
        return "Say Hello World!";
    }
	
}