package com.javacodegeeks.examples.boot.app;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javacodegeeks.examples.boot.app.model.School;

@Controller
public class SampleController {

	@Autowired
	private ResourceLoader resourceLoader;
	
    @RequestMapping("/")
    @ResponseBody
    public String home() {
    	//System.out.println("Hit me.");
        return "Hello World!";
    }
    
    @RequestMapping("/getschool/{schoolId}")
    @ResponseBody
    public School getSchool(@PathVariable("schoolId") String schoolId) {
    	School mySchool = null;
		ObjectMapper objectMapper = new ObjectMapper();
		//String fileStr = FileUtils.readFileToString("schoolSample.json");
		try {
			//InputStream myFile = ClassLoader.getSystemResourceAsStream("schoolSample.json");
			Resource myFile = resourceLoader.getResource("classpath:schoolsample.json");
			mySchool = objectMapper.readValue(myFile.getInputStream(), School.class);
			mySchool.setSchoolId(Integer.parseInt(schoolId));
			//System.out.println(objectMapper.writeValueAsString(mySchool));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mySchool;
    }
}
