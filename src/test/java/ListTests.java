import com.nagarro.controllers.ListController;
import com.nagarro.utils.Assert;
import com.nagarro.utils.DataProviderHelper;
import com.nagarro.utils.Log;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class ListTests {
	public int dataSet = -1;

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
	public void test_createList(String Title, String Update){
		/*ListController listController = new ListController();
		Response response = listController.getAll();
		Assert.verifyEquals(response.statusCode(),200);
		Log.info("Current lists:");
		Log.info(response.body().jsonPath().prettify());*/
		Log.info("==================");
		Log.info("Title: "+Title);
		Log.info("Update: "+Update);
	}
}
