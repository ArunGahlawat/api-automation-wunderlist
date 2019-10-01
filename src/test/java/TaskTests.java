import com.nagarro.controllers.ListController;
import com.nagarro.controllers.TaskController;
import com.nagarro.requests.ListRequest;
import com.nagarro.requests.TaskRequest;
import com.nagarro.utils.Assert;
import com.nagarro.utils.DataProviderHelper;
import com.nagarro.utils.JsonHandler;
import com.nagarro.utils.Log;
import io.restassured.response.Response;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.List;

public class TaskTests {
	private static List<TaskRequest> tasksToBeDeleted = new ArrayList<>();
	private static ListRequest listRequest = new ListRequest();

	@BeforeTest(alwaysRun = true)
	public void initMethod() {
		ListController listController = new ListController();
		Response response = listController.create("Newly created list");
		Assert.verifyEquals(response.statusCode(), 201);
		listRequest = JsonHandler.getDtoFromResponse(response,"",new ListRequest());
	}

	@AfterTest(alwaysRun = true)
	public void cleanup() {
		TaskController taskController = new TaskController();
		for (TaskRequest taskRequest : tasksToBeDeleted) {
			Response response = taskController.fetch(taskRequest.getId());
			taskRequest = JsonHandler.getDtoFromResponse(response,"",new TaskRequest());
			response = taskController.delete(taskRequest.getId(),taskRequest.getRevision());
			Assert.verifyEquals(response.statusCode(), 204);
		}
		ListController listController = new ListController();
		Response response = listController.fetch(listRequest.getId());
		listRequest = JsonHandler.getDtoFromResponse(response,"",new ListRequest());
		response = listController.delete(listRequest.getId(),listRequest.getRevision());
		Assert.verifyEquals(response.statusCode(), 204);
	}

	@Test(description = "Verify getting all tasks of a list",
			dataProvider = "TestDataProvider", groups = {"TASKS"},
			dataProviderClass = DataProviderHelper.class)
	public void test__getAllTasksOfList(String title, String dueDate, String starred) {
		TaskController taskController = new TaskController();
		TaskRequest taskRequest = new TaskRequest();
		taskRequest.setTitle(title);
		taskRequest.setList_id(listRequest.getId());
		taskRequest.setDue_date(dueDate);
		taskRequest.setStarred(Boolean.parseBoolean(starred));
		Response response = taskController.create(taskRequest);
		Assert.verifyEquals(response.statusCode(), 201);
		taskRequest = JsonHandler.getDtoFromResponse(response,"",new TaskRequest());
		response = taskController.getListTasks(listRequest.getId());
		Assert.verifyEquals(response.statusCode(), 200);
		List<TaskRequest> taskRequests = JsonHandler.getListDtoFromResponse(response,"",new TaskRequest());
		Log.info("List "+listRequest.getTitle()+" has following tasks: ");
		for (TaskRequest taskRequest1 : taskRequests) {
			Log.info(taskRequest1.getTitle());
		}
		tasksToBeDeleted.add(taskRequest);
	}

	@Test(description = "Verify Creating new task",
			dataProvider = "TestDataProvider", groups = {"TASKS"},
			dataProviderClass = DataProviderHelper.class)
	public void test__createTask(String title, String dueDate, String starred) {
		TaskController taskController = new TaskController();
		TaskRequest taskRequest = new TaskRequest();
		taskRequest.setTitle(title);
		taskRequest.setList_id(listRequest.getId());
		taskRequest.setDue_date(dueDate);
		taskRequest.setStarred(Boolean.parseBoolean(starred));
		Response response = taskController.create(taskRequest);
		Assert.verifyEquals(response.statusCode(), 201);
		taskRequest = JsonHandler.getDtoFromResponse(response,"",new TaskRequest());
		Assert.verifyEquals(taskRequest.getTitle(),title);
		tasksToBeDeleted.add(taskRequest);
	}

	@Test(description = "Verify fetching specific task",
			dataProvider = "TestDataProvider", groups = {"TASKS"},
			dataProviderClass = DataProviderHelper.class)
	public void test__fetchTask(String title, String dueDate, String starred) {
		TaskController taskController = new TaskController();
		TaskRequest taskRequest = new TaskRequest();
		taskRequest.setTitle(title);
		taskRequest.setList_id(listRequest.getId());
		taskRequest.setDue_date(dueDate);
		taskRequest.setStarred(Boolean.parseBoolean(starred));
		Response response = taskController.create(taskRequest);
		Assert.verifyEquals(response.statusCode(), 201);
		taskRequest = JsonHandler.getDtoFromResponse(response,"",new TaskRequest());
		response = taskController.fetch(taskRequest.getId());
		Assert.verifyEquals(response.statusCode(), 200);
		taskRequest = JsonHandler.getDtoFromResponse(response,"",new TaskRequest());
		Assert.verifyEquals(taskRequest.getTitle(),title);
		tasksToBeDeleted.add(taskRequest);
	}

	@Test(description = "Verify updating task",
			dataProvider = "TestDataProvider", groups = {"TASKS"},
			dataProviderClass = DataProviderHelper.class)
	public void test__updateTask(String title, String dueDate, String starred) {
		TaskController taskController = new TaskController();
		TaskRequest taskRequest = new TaskRequest();
		taskRequest.setTitle(title);
		taskRequest.setList_id(listRequest.getId());
		taskRequest.setDue_date(dueDate);
		taskRequest.setStarred(Boolean.parseBoolean(starred));
		Response response = taskController.create(taskRequest);
		Assert.verifyEquals(response.statusCode(), 201);
		taskRequest = JsonHandler.getDtoFromResponse(response,"",new TaskRequest());
		Assert.verifyEquals(taskRequest.getTitle(),title);
		TaskRequest updateRequest = new TaskRequest();
		updateRequest.setTitle("Updated title");
		updateRequest.setRevision(taskRequest.getRevision());
		response = taskController.update(taskRequest.getId(),updateRequest);
		Assert.verifyEquals(response.statusCode(), 200);
		TaskRequest updateRequestResponse = JsonHandler.getDtoFromResponse(response,"",new TaskRequest());
		Assert.verifyEquals(updateRequestResponse.getTitle(),updateRequest.getTitle());
		tasksToBeDeleted.add(updateRequestResponse);
	}

	@Test(description = "Verify deleting task",
			dataProvider = "TestDataProvider", groups = {"TASKS"},
			dataProviderClass = DataProviderHelper.class)
	public void test__deleteTask(String title, String dueDate, String starred) {
		TaskController taskController = new TaskController();
		TaskRequest taskRequest = new TaskRequest();
		taskRequest.setTitle(title);
		taskRequest.setList_id(listRequest.getId());
		taskRequest.setDue_date(dueDate);
		taskRequest.setStarred(Boolean.parseBoolean(starred));
		Response response = taskController.create(taskRequest);
		Assert.verifyEquals(response.statusCode(), 201);
		taskRequest = JsonHandler.getDtoFromResponse(response,"",new TaskRequest());
		Assert.verifyEquals(taskRequest.getTitle(),title);
		response = taskController.delete(taskRequest.getId(),taskRequest.getRevision());
		Assert.verifyEquals(response.statusCode(), 204);
	}
}
