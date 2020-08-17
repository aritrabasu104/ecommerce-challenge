package com.tomtom.challenge.service;

//@RunWith(SpringRunner.class)
//@ActiveProfiles("test")
//@SpringBootTest
//public class TaskServiceTest {

//	@MockBean
//	private TaskRepository taskRepository;
//
//	@MockBean
//	private FileRepository fileRepository;
//
//	
//	@Value("${output.filepath}")
//	private String filePath;
//
//	
//	@Autowired
//	private TaskServiceImpl taskService;
//
//	@Captor
//	ArgumentCaptor<NumGenTask> acTask;
//
//	@Captor
//	ArgumentCaptor<UUID> acUuid;
//
//	private NumGenTask task;
//	private NumGenTask taskWithId;
//	private NumGenTask taskCompleted;
//	@Before
//	public void init() {
//		task = new NumGenTask(10L, 2L);
//		taskWithId = new NumGenTask(10L, 2L);
//		taskWithId.setId(UUID.randomUUID());
//		taskWithId.setStatus(STATUS.IN_PROGRESS);
//		taskCompleted = new NumGenTask(10L, 2L,taskWithId.getId(),STATUS.SUCCESS);
//	}
//	
//	@After
//	public void destroy() throws IOException{
//		FileUtils.cleanDirectory(new File(filePath));
//	}
//	
//	@Test
//	public void shouldCreateTaskWithIdAndStatus() throws InterruptedException {
//		given(taskRepository.save(task)).willReturn(taskWithId);
//		given(fileRepository.writeFile(taskWithId)).willReturn(taskCompleted);
//
//		NumGenTask result = taskService.createTask(task);
//		assertEquals(taskWithId, result);
//		assertEquals(taskWithId.getGoal(), result.getGoal());
//		assertEquals(taskWithId.getStep(), result.getStep());
//		assertEquals(STATUS.IN_PROGRESS, result.getStatus());
//		
//		Awaitility.await().until(()->Files.exists(Paths.get(filePath)));
//		verify(fileRepository, times(1)).writeFile(acTask.capture());
//		verify(taskRepository, times(2)).save(acTask.capture());
//	}
//
//	@Test
//	public void shouldReturnStatus() throws Exception {
//		NumGenTask taskError = new NumGenTask(10L, 2L, UUID.randomUUID(), STATUS.ERROR);
//		NumGenTask taskProgress = new NumGenTask(10L, 2L, UUID.randomUUID(), STATUS.IN_PROGRESS);
//
//		given(taskRepository.findById(taskProgress.getId())).willReturn(Optional.of(taskProgress));
//		given(taskRepository.findById(taskCompleted.getId())).willReturn(Optional.of(taskCompleted));
//		given(taskRepository.findById(taskError.getId())).willReturn(Optional.of(taskError));
//
//		assertEquals(STATUS.IN_PROGRESS, taskService.getStatus(taskProgress.getId()));
//		assertEquals(STATUS.SUCCESS, taskService.getStatus(taskCompleted.getId()));
//		assertEquals(STATUS.ERROR, taskService.getStatus(taskError.getId()));
//
//		verify(taskRepository, times(3)).findById(acUuid.capture());
//	}
//
//	@Test(expected = NoSuchElementException.class)
//	public void getStatusShouldThrowNoSuchElementException() throws Exception {
//
//		taskService.getStatus(taskWithId.getId());
//		verify(taskRepository, times(1)).findById(acUuid.capture());
//	}
//
//	
//	@Test(expected = NoSuchElementException.class)
//	public void shouldThrowNoSuchElementException() throws Exception {
//
//		taskService.readData(taskWithId.getId());
//		verify(taskRepository, times(1)).findById(acUuid.capture());
//	}
//
//	@Test
//	public void shouldReadData() throws Exception {
//		NumGenTask taskRead = new NumGenTask(10L, 2L,UUID.randomUUID(),STATUS.IN_PROGRESS);
//		String expected = "10,8,6,4,2,0";
//		
//		given(taskRepository.save(taskRead)).willReturn(taskRead);
//		given(taskRepository.findById(taskRead.getId())).willReturn(Optional.of(taskRead));
//		given(fileRepository.readFile(taskRead.getId())).willReturn(expected);
//		
//		taskRead = taskService.createTask(taskRead);
//		Awaitility.await().until(()->Files.exists(Paths.get(filePath)));
//		
//		String actual = taskService.readData(taskRead.getId());
//		assertEquals(expected, actual);
//		
//		verify(taskRepository, times(2)).save(acTask.capture());
//		verify(taskRepository, times(1)).findById(acUuid.capture());
//		verify(fileRepository, times(1)).readFile(acUuid.capture());
//		
//	}

	

//}
