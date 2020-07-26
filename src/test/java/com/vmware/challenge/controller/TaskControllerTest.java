package com.vmware.challenge.controller;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.NoSuchElementException;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.vmware.challenge.model.NumGenTask;
import com.vmware.challenge.model.NumGenTask.STATUS;
import com.vmware.challenge.service.TaskService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Captor
	ArgumentCaptor<NumGenTask> acTask;

    @Captor
	ArgumentCaptor<UUID> acUuid;

    @MockBean
    private TaskService taskService;
    
    private NumGenTask task;
    private NumGenTask taskWithId;
    private final String taskJsonValid = "{\"Goal\":\"10\",\"Step\":\"2\"}";
    private final String resultForValidTask = "10,8,6,4,2,0";
    @Before
    public void init() {
    	task = new NumGenTask(10L, 2L);
        taskWithId = new NumGenTask(10L, 2l, UUID.randomUUID(), STATUS.IN_PROGRESS);
        
    }

//    /*
//        {
//            "timestamp":"2019-03-05T09:34:13.280+0000",
//            "status":400,
//            "errors":["Author is not allowed.","Please provide a price","Please provide a author"]
//        }
//     */
  
    @Test
    public void save_emptyStep_emptyGoal_400() throws Exception {

        String taskJson = "{}";

        mockMvc.perform(post("/api/generate")
                .content(taskJson)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp", is(notNullValue())))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors", hasSize(2)))
                .andExpect(jsonPath("$.errors", hasItem("Please provide a Step")))
                .andExpect(jsonPath("$.errors", hasItem("Please provide a Goal")));
                
        verify(taskService, times(0)).createTask(acTask.capture());

    }
    @Test
    public void save_invalidGoal_invlidStep_400() throws Exception {

        String taskJson = "{\"Goal\":\"m\",\"Step\":\"m\"}";

        mockMvc.perform(post("/api/generate")
                .content(taskJson)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp", is(notNullValue())))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.error").isString())
                .andExpect(jsonPath("$.error", containsString("ModelMapper mapping errors")));
                
        verify(taskService, times(0)).createTask(acTask.capture());

    }
    @Test
    public void save_invalidGoal_400() throws Exception {

        String taskJson = "{\"Goal\":\"m\",\"Step\":\"2\"}";

        mockMvc.perform(post("/api/generate")
                .content(taskJson)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp", is(notNullValue())))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.error").isString())
                .andExpect(jsonPath("$.error", containsString("NumberFormatException: For input string")));
                
        verify(taskService, times(0)).createTask(acTask.capture());

    }
    @Test
    public void save_invalidStep_400() throws Exception {

        String taskJson = "{\"Goal\":\"10\",\"Step\":\"n\"}";

        mockMvc.perform(post("/api/generate")
                .content(taskJson)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp", is(notNullValue())))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.error").isString())
                .andExpect(jsonPath("$.error", containsString("NumberFormatException: For input string")));
                
        verify(taskService, times(0)).createTask(acTask.capture());

    }
    @Test
    public void save_validTask_202() throws Exception {

        given(taskService.createTask(task)).willReturn(taskWithId);
        mockMvc.perform(post("/api/generate")
                .content(taskJsonValid)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(202))
                .andExpect(jsonPath("$.task", is(taskWithId.getId().toString())));
                
        verify(taskService, times(1)).createTask(acTask.capture());

    }
    
    @Test
    public void get_status_invalidTask_404() throws Exception {

        UUID id = UUID.randomUUID();
        given(taskService.getStatus(id)).willThrow(NoSuchElementException.class);
        mockMvc.perform(get("/api/tasks/{UUID of the task}/status",id)
                .content(taskJsonValid)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.timestamp", is(notNullValue())))
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.error").isString())
                .andExpect(jsonPath("$.error", containsString("NoSuchElementException")));
                
                
        verify(taskService, times(1)).getStatus(acUuid.capture());

    }
    
   
    @Test
    public void get_status_valid_task_progress_200() throws Exception {
    	UUID id = UUID.randomUUID();
        
        given(taskService.getStatus(id)).willReturn(STATUS.IN_PROGRESS);
        mockMvc.perform(get("/api/tasks/{UUID of the task}/status",id)
                .content(taskJsonValid)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is("IN_PROGRESS")));
                
                
        verify(taskService, times(1)).getStatus(acUuid.capture());

    }
    
    @Test
    public void get_status_valid_task_success_200() throws Exception {
    	UUID id = UUID.randomUUID();
        
        given(taskService.getStatus(id)).willReturn(STATUS.SUCCESS);
        mockMvc.perform(get("/api/tasks/{UUID of the task}/status",id)
                .content(taskJsonValid)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is("SUCCESS")));
                
                
        verify(taskService, times(1)).getStatus(acUuid.capture());

    }
    
    @Test
    public void get_status_valid_task_error_200() throws Exception {
    	UUID id = UUID.randomUUID();
        
        given(taskService.getStatus(id)).willReturn(STATUS.ERROR);
        mockMvc.perform(get("/api/tasks/{UUID of the task}/status",id)
                .content(taskJsonValid)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is("ERROR")));
                
                
        verify(taskService, times(1)).getStatus(acUuid.capture());

    }
    
    @Test
    public void read_data_invalidTask_404() throws Exception {

        UUID id = UUID.randomUUID();
        given(taskService.readData(id)).willThrow(NoSuchElementException.class);
        mockMvc.perform(get("/api/tasks/{UUID of the task}",id)
        		.param("action","get_numlist")
                .content(taskJsonValid)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.timestamp", is(notNullValue())))
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.error").isString())
                .andExpect(jsonPath("$.error", containsString("NoSuchElementException")));
                
                
        verify(taskService, times(1)).readData(acUuid.capture());

    }
    @Test
    public void read_data_invalid_req_param_400() throws Exception {

        UUID id = UUID.randomUUID();
        given(taskService.readData(id)).willReturn(resultForValidTask);
        mockMvc.perform(get("/api/tasks/{UUID of the task}",id)
        		.param("action","*")
                .content(taskJsonValid)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp", is(notNullValue())))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.error").isString())
                .andExpect(jsonPath("$.error", containsString("get_numlist")));
                
                
        verify(taskService, times(0)).readData(acUuid.capture());

    }
    
    @Test
    public void read_data_valid_task_valid_req_param_200() throws Exception {

    	UUID id = UUID.randomUUID();
        given(taskService.readData(id)).willReturn(resultForValidTask);
        mockMvc.perform(get("/api/tasks/{UUID of the task}",id)
        		.param("action","get_numlist")
                .content(taskJsonValid)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", is(resultForValidTask)));
                
                
        verify(taskService, times(1)).readData(acUuid.capture());

    }
//
//    /*
//        {
//            "timestamp":"2019-03-05T09:34:13.207+0000",
//            "status":400,
//            "errors":["Author is not allowed."]
//        }
//     */
//    @Test
//    public void save_invalidAuthor_400() throws Exception {
//
//        String bookInJson = "{\"name\":\" Spring REST tutorials\", \"author\":\"abc\",\"price\":\"9.99\"}";
//
//        mockMvc.perform(post("/books")
//                .content(bookInJson)
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.timestamp", is(notNullValue())))
//                .andExpect(jsonPath("$.status", is(400)))
//                .andExpect(jsonPath("$.errors").isArray())
//                .andExpect(jsonPath("$.errors", hasSize(1)))
//                .andExpect(jsonPath("$.errors", hasItem("Author is not allowed.")));
//
//        verify(mockRepository, times(0)).save(any(Book.class));
//
//    }

}
