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
	public void test__getAllTasksOfList(String listName, String title, String dueDate, boolean starred) {
		ListController listController = new ListController();
		Response response = listController.create(listName);
		Assert.verifyEquals(response.statusCode(), 201);
		ListRequest listRequest = JsonHandler.getDtoFromResponse(response,"",new ListRequest());
		TaskController taskController = new TaskController();
		TaskRequest taskRequest = new TaskRequest();
		taskRequest.setTitle(title);
		taskRequest.setList_id(listRequest.getId());
		taskRequest.setDue_date(dueDate);
		taskRequest.setStarred(starred);
		response = taskController.create(taskRequest);
		Assert.verifyEquals(response.statusCode(), 201);
		response = taskController.getListTasks(listRequest.getId());
		List<TaskRequest> taskRequests = JsonHandler.getListDtoFromResponse(response,"",new TaskRequest());
		Assert.verifyEquals(taskRequests.size(),1);
		Assert.verifyEquals(taskRequests.get(0).getTitle(),title);
		Assert.verifyEquals(taskRequests.get(0).getDue_date(),dueDate);
	}
}
