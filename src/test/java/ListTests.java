import com.nagarro.controllers.*;
import com.nagarro.requests.ListRequest;
import com.nagarro.utils.Assert;
import com.nagarro.utils.DataProviderHelper;
import com.nagarro.utils.JsonHandler;
import com.nagarro.utils.Log;
import io.restassured.response.Response;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class ListTests {
	private static List<ListRequest> listsToBeDeleted = new ArrayList<>();

	@AfterTest(alwaysRun = true)
	public void cleanup() {
		ListController listController = new ListController();
		for (ListRequest listRequest:listsToBeDeleted) {
			Response response = listController.fetch(listRequest.getId());
			listRequest = JsonHandler.getDtoFromResponse(response,"",new ListRequest());
			response = listController.delete(listRequest.getId(),listRequest.getRevision());
			Assert.verifyEquals(response.statusCode(), 204);
		}
	}

	@Test(description = "Get all Lists a user has permission to",groups = {"LISTS"})
	public void test_getAllUserLists(){
		ListController listController = new ListController();
		Response response = listController.getAll();
		Assert.verifyEquals(response.statusCode(),200);
		Log.info("Current lists:");
		Log.info(response.body().jsonPath().prettify());
	}

	@Test(description = "Create list",groups = {"LISTS"}, dataProvider = "TestDataProvider",
			dataProviderClass = DataProviderHelper.class)
	public void test_createList(String title) {
		ListController listController = new ListController();
		Response response = listController.create(title);
		Assert.verifyEquals(response.statusCode(), 201);
		ListRequest listRequest = JsonHandler.getDtoFromResponse(response,"",new ListRequest());
		Assert.verifyEquals(listRequest.getTitle(), title);
		listsToBeDeleted.add(listRequest);
	}

	@Test(description = "Update list",groups = {"LISTS"}, dataProvider = "TestDataProvider",
			dataProviderClass = DataProviderHelper.class)
	public void test_updateList(String title) {
		ListController listController = new ListController();
		Response response = listController.create(title);
		Assert.verifyEquals(response.statusCode(), 201);
		ListRequest listRequest = JsonHandler.getDtoFromResponse(response,"",new ListRequest());
		Assert.verifyEquals(listRequest.getTitle(), title);
		response = listController.update(listRequest.getId(),listRequest.getRevision(),"Updated title");
		Assert.verifyEquals(response.statusCode(), 200);
		response = listController.fetch(listRequest.getId());
		Assert.verifyEquals(response.statusCode(), 200);
		listRequest = JsonHandler.getDtoFromResponse(response,"",new ListRequest());
		Assert.verifyEquals(listRequest.getTitle(),"Updated title");
		listsToBeDeleted.add(listRequest);
	}

	@Test(description = "Fetch list",groups = {"LISTS"}, dataProvider = "TestDataProvider",
			dataProviderClass = DataProviderHelper.class)
	public void test_fetchList(String title) {
		ListController listController = new ListController();
			Response response = listController.create(title);
			Assert.verifyEquals(response.statusCode(), 201);
			ListRequest listRequest = JsonHandler.getDtoFromResponse(response,"", new ListRequest());
			Assert.verifyEquals(listRequest.getTitle(), title);
			response = listController.fetch(listRequest.getId());
			Assert.verifyEquals(response.statusCode(), 200);
			listsToBeDeleted.add(listRequest);
	}

	@Test(description = "delete list",groups = {"LISTS"}, dataProvider = "TestDataProvider",
			dataProviderClass = DataProviderHelper.class)
	public void test_deleteList(String title) {
		ListController listController = new ListController();
		Response response = listController.create(title);
		Assert.verifyEquals(response.statusCode(), 201);
		ListRequest listRequest = JsonHandler.getDtoFromResponse(response,"",new ListRequest());
		Assert.verifyEquals(listRequest.getTitle(), title);
		response = listController.delete(listRequest.getId(),listRequest.getRevision());
		Assert.verifyEquals(response.statusCode(), 204);
	}
}
