package com.vmware.challenge.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;


@Configuration
public class AppConfig {


	@Lazy
	@Autowired
	ObjectMapper mapper;
	
	@Lazy
	@Autowired
	private ExecutorService executorService;
	
	@Value("${output.filepath}")
	private String filepath;
	
	@Value("${output.shouldpersist}")
	private boolean shouldPersistFile;
	
	@Value("${thread.shutdowntime}")
	private int shutdowntime;
	
	@Value("${thread.size.corepool}")
	private int corepoolSize;
	
	@Value("${thread.size.maxpool}")
	private int maxpoolSize;
	
	@Value("${thread.size.queue}")
	private int queueSize;
	
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public ExecutorService threadPool() {
		ArrayBlockingQueue<Runnable> boundedQueue = new ArrayBlockingQueue<Runnable>(queueSize);
		return new ThreadPoolExecutor(corepoolSize, maxpoolSize, 60, TimeUnit.SECONDS, boundedQueue, new AbortPolicy());

	}
	
	@Bean
	@PreDestroy
    public void shutdownExcecutor() throws IOException, InterruptedException {
        executorService.awaitTermination(shutdowntime, TimeUnit.SECONDS);
        if(!shouldPersistFile && Files.exists(Paths.get(filepath))) {
        	FileUtils.cleanDirectory(new File(filepath));
        }
    }
	
	
	@PostConstruct
	public ObjectMapper configureMapper() {
		return mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
	}
}
