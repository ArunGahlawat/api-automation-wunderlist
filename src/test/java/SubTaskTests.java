import com.nagarro.controllers.ListController;
import com.nagarro.controllers.SubTaskController;
import com.nagarro.controllers.TaskController;
import com.nagarro.requests.ListRequest;
import com.nagarro.requests.SubTaskRequest;
import com.nagarro.requests.TaskRequest;
import com.nagarro.utils.Assert;
import com.nagarro.utils.DataProviderHelper;
import com.nagarro.utils.JsonHandler;
import com.nagarro.utils.Log;
import io.restassured.response.Response;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.List;

public class SubTaskTests {
	private static List<SubTaskRequest> subTasksToBeDeleted = new ArrayList<>();
	private static ListRequest listRequest = new ListRequest();
	private static TaskRequest taskRequest = new TaskRequest();

	@BeforeTest(alwaysRun = true)
	public void initMethod() {
		ListController listController = new ListController();
		Response response = listController.create("Newly created list");
		Assert.verifyEquals(response.statusCode(), 201);
		listRequest = JsonHandler.getDtoFromResponse(response,"",new ListRequest());
		TaskController taskController = new TaskController();
		taskRequest.setTitle("Newly created task");
		taskRequest.setList_id(listRequest.getId());
		response = taskController.create(taskRequest);
		Assert.verifyEquals(response.statusCode(), 201);
		taskRequest = JsonHandler.getDtoFromResponse(response,"",new TaskRequest());
	}

	@AfterTest(alwaysRun = true)
	public void cleanup() {
		SubTaskController subTaskController = new SubTaskController();
		for (SubTaskRequest subTaskRequest:subTasksToBeDeleted) {
			Response response = subTaskController.fetch(subTaskRequest.getId());
			subTaskRequest = JsonHandler.getDtoFromResponse(response,"",new SubTaskRequest());
			response = subTaskController.delete(subTaskRequest.getId(),subTaskRequest.getRevision());
			Assert.verifyEquals(response.statusCode(), 204);
		}
		TaskController taskController = new TaskController();
		Response response = taskController.fetch(taskRequest.getId());
		taskRequest = JsonHandler.getDtoFromResponse(response,"",new TaskRequest());
		response = taskController.delete(taskRequest.getId(),taskRequest.getRevision());
		Assert.verifyEquals(response.statusCode(), 204);
		ListController listController = new ListController();
		response = listController.fetch(listRequest.getId());
		listRequest = JsonHandler.getDtoFromResponse(response,"",new ListRequest());
		response = listController.delete(listRequest.getId(),listRequest.getRevision());
		Assert.verifyEquals(response.statusCode(), 204);
	}

	@Test(description = "Verify getting all subtask of a list",
			dataProvider = "TestDataProvider", groups = {"SUBTASKS"},
			dataProviderClass = DataProviderHelper.class)
	public void test__getAllSubTasksOfList(String title) {
		SubTaskController subTaskController = new SubTaskController();
		Response response = subTaskController.create(taskRequest.getId(),title,false);
		Assert.verifyEquals(response.statusCode(), 201);
		SubTaskRequest subTaskRequest = JsonHandler.getDtoFromResponse(response,"",new SubTaskRequest());
		response = subTaskController.getSubTasks(listRequest.getId(),"LIST");
		Assert.verifyEquals(response.statusCode(), 200);
		List<SubTaskRequest> subTaskRequests = JsonHandler.getListDtoFromResponse(response,"",new SubTaskRequest());
		Log.info("List "+listRequest.getTitle()+" has following subtasks: ");
		for (SubTaskRequest subTaskRequest1:subTaskRequests) {
			Log.info(subTaskRequest1.getTitle());
		}
		subTasksToBeDeleted.add(subTaskRequest);
	}

	@Test(description = "Verify Creating new subtask",
			dataProvider = "TestDataProvider", groups = {"SUBTASKS"},
			dataProviderClass = DataProviderHelper.class)
	public void test__createSubTask(String title) {
		SubTaskController subTaskController = new SubTaskController();
		Response response = subTaskController.create(taskRequest.getId(),title,false);
		Assert.verifyEquals(response.statusCode(), 201);
		SubTaskRequest subTaskRequest = JsonHandler.getDtoFromResponse(response,"",new SubTaskRequest());
		Assert.verifyEquals(subTaskRequest.getTitle(),title);
		subTasksToBeDeleted.add(subTaskRequest);
	}

	@Test(description = "Verify fetching subtask",
			dataProvider = "TestDataProvider", groups = {"SUBTASKS"},
			dataProviderClass = DataProviderHelper.class)
	public void test__fetchSubTask(String title) {
		SubTaskController subTaskController = new SubTaskController();
		Response response = subTaskController.create(taskRequest.getId(),title,false);
		Assert.verifyEquals(response.statusCode(), 201);
		SubTaskRequest subTaskRequest = JsonHandler.getDtoFromResponse(response,"",new SubTaskRequest());
		response = subTaskController.fetch(subTaskRequest.getId());
		Assert.verifyEquals(response.statusCode(), 200);
		subTaskRequest = JsonHandler.getDtoFromResponse(response,"",new SubTaskRequest());
		Assert.verifyEquals(subTaskRequest.getTitle(),title);
		subTasksToBeDeleted.add(subTaskRequest);
	}

	@Test(description = "Verify updating subtask",
			dataProvider = "TestDataProvider", groups = {"SUBTASKS"},
			dataProviderClass = DataProviderHelper.class)
	public void test__updateSubTask(String title) {
		SubTaskController subTaskController = new SubTaskController();
		Response response = subTaskController.create(taskRequest.getId(),title,false);
		Assert.verifyEquals(response.statusCode(), 201);
		SubTaskRequest subTaskRequest = JsonHandler.getDtoFromResponse(response,"",new SubTaskRequest());
		Assert.verifyEquals(subTaskRequest.getTitle(),title);
		response = subTaskController.update(subTaskRequest.getId(),subTaskRequest.getRevision(),"Updated title",false);
		Assert.verifyEquals(response.statusCode(), 200);
		SubTaskRequest updatedSubTaskRequest = JsonHandler.getDtoFromResponse(response,"",new SubTaskRequest());
		Assert.verifyEquals(updatedSubTaskRequest.getTitle(),"Updated title");
		subTasksToBeDeleted.add(updatedSubTaskRequest);
	}

	@Test(description = "Verify deleting subtask",
			dataProvider = "TestDataProvider", groups = {"SUBTASKS"},
			dataProviderClass = DataProviderHelper.class)
	public void test__deleteTask(String title) {
		SubTaskController subTaskController = new SubTaskController();
		Response response = subTaskController.create(taskRequest.getId(),title,false);
		Assert.verifyEquals(response.statusCode(), 201);
		SubTaskRequest subTaskRequest = JsonHandler.getDtoFromResponse(response,"",new SubTaskRequest());
		response = subTaskController.delete(subTaskRequest.getId(),subTaskRequest.getRevision());
		Assert.verifyEquals(response.statusCode(), 204);
	}
}
