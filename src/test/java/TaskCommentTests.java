import com.nagarro.controllers.ListController;
import com.nagarro.controllers.NoteController;
import com.nagarro.controllers.TaskCommentController;
import com.nagarro.controllers.TaskController;
import com.nagarro.requests.ListRequest;
import com.nagarro.requests.NoteRequest;
import com.nagarro.requests.TaskCommentRequest;
import com.nagarro.requests.TaskRequest;
import com.nagarro.utils.Assert;
import com.nagarro.utils.DataProviderHelper;
import com.nagarro.utils.JsonHandler;
import com.nagarro.utils.Log;
import io.restassured.response.Response;
import org.testng.annotations.*;

import java.util.List;

public class TaskCommentTests {
	private static ListRequest listRequest = new ListRequest();
	private static TaskRequest taskRequest = new TaskRequest();

	@BeforeTest(alwaysRun = true)
	public void initMethod() {
		ListController listController = new ListController();
		Response response = listController.create("Newly created list");
		Assert.verifyEquals(response.statusCode(), 201);
		listRequest = JsonHandler.getDtoFromResponse(response,"",new ListRequest());

	}

	@BeforeMethod(alwaysRun = true)
	public void createIndividual() {
		TaskController taskController = new TaskController();
		taskRequest.setTitle("Newly created task");
		taskRequest.setList_id(listRequest.getId());
		Response response = taskController.create(taskRequest);
		Assert.verifyEquals(response.statusCode(), 201);
		taskRequest = JsonHandler.getDtoFromResponse(response,"",new TaskRequest());
	}

	@AfterMethod(alwaysRun = true)
	public void cleanupIndividual() {
		TaskController taskController = new TaskController();
		Response response = taskController.fetch(taskRequest.getId());
		taskRequest = JsonHandler.getDtoFromResponse(response,"",new TaskRequest());
		response = taskController.delete(taskRequest.getId(),taskRequest.getRevision());
		Assert.verifyEquals(response.statusCode(), 204);
	}

	@AfterTest(alwaysRun = true)
	public void cleanup() {
		ListController listController = new ListController();
		Response response = listController.fetch(listRequest.getId());
		listRequest = JsonHandler.getDtoFromResponse(response,"",new ListRequest());
		response = listController.delete(listRequest.getId(),listRequest.getRevision());
		Assert.verifyEquals(response.statusCode(), 204);
	}

	@Test(description = "Verify getting all comments of a list",
			dataProvider = "TestDataProvider", groups = {"TASK_COMMENTS"},
			dataProviderClass = DataProviderHelper.class)
	public void test__getAllCommentsOfList(String text) {
		TaskCommentController taskCommentController = new TaskCommentController();
		Response response = taskCommentController.create(taskRequest.getId(),text);
		Assert.verifyEquals(response.statusCode(), 201);
		response = taskCommentController.getAll(listRequest.getId(),"LIST");
		Assert.verifyEquals(response.statusCode(), 200);
		List<TaskCommentRequest> taskCommentRequests = JsonHandler.getListDtoFromResponse(response,"",new TaskCommentRequest());
		Log.info("List "+listRequest.getTitle()+" has following comments: ");
		for (TaskCommentRequest taskCommentRequest1:taskCommentRequests) {
			Log.info(taskCommentRequest1.getText());
		}
	}

	@Test(description = "Verify Creating new comment",
			dataProvider = "TestDataProvider", groups = {"TASK_COMMENTS"},
			dataProviderClass = DataProviderHelper.class)
	public void test__createComment(String text) {
		TaskCommentController taskCommentController = new TaskCommentController();
		Response response = taskCommentController.create(taskRequest.getId(),text);
		Assert.verifyEquals(response.statusCode(), 201);
		TaskCommentRequest taskCommentRequest = JsonHandler.getDtoFromResponse(response,"",new TaskCommentRequest());
		Assert.verifyEquals(taskCommentRequest.getText(),text);
	}

	@Test(description = "Verify fetching comment",
			dataProvider = "TestDataProvider", groups = {"TASK_COMMENTS"},
			dataProviderClass = DataProviderHelper.class)
	public void test__fetchComment(String text) {
		TaskCommentController taskCommentController = new TaskCommentController();
		Response response = taskCommentController.create(taskRequest.getId(),text);
		Assert.verifyEquals(response.statusCode(), 201);
		TaskCommentRequest taskCommentRequest = JsonHandler.getDtoFromResponse(response,"",new TaskCommentRequest());
		response = taskCommentController.fetch(taskCommentRequest.getId());
		Assert.verifyEquals(response.statusCode(), 200);
		taskCommentRequest = JsonHandler.getDtoFromResponse(response,"",new TaskCommentRequest());
		Assert.verifyEquals(taskCommentRequest.getText(),text);
	}
}
