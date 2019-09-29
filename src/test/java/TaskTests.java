import com.nagarro.controllers.ListController;
import com.nagarro.controllers.TaskController;
import com.nagarro.requests.ListRequest;
import com.nagarro.requests.TaskRequest;
import com.nagarro.utils.Assert;
import com.nagarro.utils.DataProviderHelper;
import com.nagarro.utils.JsonHandler;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

public class TaskTests {
	@Test(description = "Verify getting all tasks of a list",
			dataProvider = "TestDataProvider", groups = {"TASKS"},
			dataProviderClass = DataProviderHelper.class)
	public void test__getAllTasksOfList(String listName, String title, String dueDate, String starred) {
		ListController listController = new ListController();
		Response response = listController.create(listName);
		Assert.verifyEquals(response.statusCode(), 201);
		ListRequest listRequest = JsonHandler.getDtoFromResponse(response,"",new ListRequest());
		TaskController taskController = new TaskController();
		TaskRequest taskRequest = new TaskRequest();
		taskRequest.setTitle(title);
		taskRequest.setList_id(listRequest.getId());
		taskRequest.setDue_date(dueDate);
		taskRequest.setStarred(Boolean.parseBoolean(starred));
		response = taskController.create(taskRequest);
		Assert.verifyEquals(response.statusCode(), 201);
		response = taskController.getListTasks(listRequest.getId());
		List<TaskRequest> taskRequests = JsonHandler.getListDtoFromResponse(response,"",new TaskRequest());
		Assert.verifyEquals(taskRequests.size(),1);
		Assert.verifyEquals(taskRequests.get(0).getTitle(),title);
		response = listController.fetch(listRequest.getId());
		listRequest = JsonHandler.getDtoFromResponse(response,"",new ListRequest());
		response = listController.delete(listRequest.getId(),listRequest.getRevision());
		Assert.verifyEquals(response.statusCode(), 204);
	}

	@Test(description = "Verify Creating new task",
			dataProvider = "TestDataProvider", groups = {"TASKS"},
			dataProviderClass = DataProviderHelper.class)
	public void test__createTask(String listName, String title, String dueDate, String starred) {
		ListController listController = new ListController();
		Response response = listController.create(listName);
		Assert.verifyEquals(response.statusCode(), 201);
		ListRequest listRequest = JsonHandler.getDtoFromResponse(response,"",new ListRequest());
		TaskController taskController = new TaskController();
		TaskRequest taskRequest = new TaskRequest();
		taskRequest.setTitle(title);
		taskRequest.setList_id(listRequest.getId());
		taskRequest.setDue_date(dueDate);
		taskRequest.setStarred(Boolean.parseBoolean(starred));
		response = taskController.create(taskRequest);
		Assert.verifyEquals(response.statusCode(), 201);
		taskRequest = JsonHandler.getDtoFromResponse(response,"",new TaskRequest());
		Assert.verifyEquals(taskRequest.getTitle(),title);
		response = listController.fetch(listRequest.getId());
		listRequest = JsonHandler.getDtoFromResponse(response,"",new ListRequest());
		response = listController.delete(listRequest.getId(),listRequest.getRevision());
		Assert.verifyEquals(response.statusCode(), 204);
	}

	@Test(description = "Verify updating task",
			dataProvider = "TestDataProvider", groups = {"TASKS"},
			dataProviderClass = DataProviderHelper.class)
	public void test__updateTask(String listName, String title, String dueDate, String starred) {
		ListController listController = new ListController();
		Response response = listController.create(listName);
		Assert.verifyEquals(response.statusCode(), 201);
		ListRequest listRequest = JsonHandler.getDtoFromResponse(response,"",new ListRequest());
		TaskController taskController = new TaskController();
		TaskRequest taskRequest = new TaskRequest();
		taskRequest.setTitle(title);
		taskRequest.setList_id(listRequest.getId());
		taskRequest.setDue_date(dueDate);
		taskRequest.setStarred(Boolean.parseBoolean(starred));
		response = taskController.create(taskRequest);
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
		response = listController.fetch(listRequest.getId());
		listRequest = JsonHandler.getDtoFromResponse(response,"",new ListRequest());
		response = listController.delete(listRequest.getId(),listRequest.getRevision());
		Assert.verifyEquals(response.statusCode(), 204);
	}

	@Test(description = "Verify deleting task",
			dataProvider = "TestDataProvider", groups = {"TASKS"},
			dataProviderClass = DataProviderHelper.class)
	public void test__deleteTask(String listName, String title, String dueDate, String starred) {
		ListController listController = new ListController();
		Response response = listController.create(listName);
		Assert.verifyEquals(response.statusCode(), 201);
		ListRequest listRequest = JsonHandler.getDtoFromResponse(response,"",new ListRequest());
		TaskController taskController = new TaskController();
		TaskRequest taskRequest = new TaskRequest();
		taskRequest.setTitle(title);
		taskRequest.setList_id(listRequest.getId());
		taskRequest.setDue_date(dueDate);
		taskRequest.setStarred(Boolean.parseBoolean(starred));
		response = taskController.create(taskRequest);
		Assert.verifyEquals(response.statusCode(), 201);
		taskRequest = JsonHandler.getDtoFromResponse(response,"",new TaskRequest());
		Assert.verifyEquals(taskRequest.getTitle(),title);
		response = taskController.delete(taskRequest.getId(),taskRequest.getRevision());
		Assert.verifyEquals(response.statusCode(), 204);
		response = listController.fetch(listRequest.getId());
		listRequest = JsonHandler.getDtoFromResponse(response,"",new ListRequest());
		response = listController.delete(listRequest.getId(),listRequest.getRevision());
		Assert.verifyEquals(response.statusCode(), 204);
	}
}
