import com.nagarro.controllers.ListController;
import com.nagarro.controllers.NoteController;
import com.nagarro.controllers.TaskController;
import com.nagarro.requests.ListRequest;
import com.nagarro.requests.NoteRequest;
import com.nagarro.requests.TaskRequest;
import com.nagarro.utils.Assert;
import com.nagarro.utils.DataProviderHelper;
import com.nagarro.utils.JsonHandler;
import com.nagarro.utils.Log;
import io.restassured.response.Response;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.List;

public class NoteTests {
	private static List<NoteRequest> notesToBeDeleted = new ArrayList<>();
	private static ListRequest listRequest = new ListRequest();
	private static TaskRequest taskRequest = new TaskRequest();

	@BeforeTest(alwaysRun = true)
	public void initTest() {
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

	/*@AfterMethod(alwaysRun = true)
	public void cleanupIndividual() {
		NoteController noteController = new NoteController();
		for (NoteRequest noteRequest:notesToBeDeleted) {
			Response response = noteController.fetch(noteRequest.getId());
			noteRequest = JsonHandler.getDtoFromResponse(response,"",new NoteRequest());
			response = noteController.delete(noteRequest.getId(),noteRequest.getRevision());
			Assert.verifyEquals(response.statusCode(), 204);
		}
	}*/

	@AfterTest(alwaysRun = true)
	public void cleanup() {
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

	@Test(description = "Verify getting all notes of a list",
			dataProvider = "TestDataProvider", groups = {"NOTES"},
			dataProviderClass = DataProviderHelper.class)
	public void test__getAllNotesOfList(String title) {
		NoteController noteController = new NoteController();
		Response response = noteController.create(taskRequest.getId(),title);
		Assert.verifyEquals(response.statusCode(), 201);
		NoteRequest noteRequest = JsonHandler.getDtoFromResponse(response,"",new NoteRequest());
		response = noteController.getTaskNotes(listRequest.getId(),"LIST");
		Assert.verifyEquals(response.statusCode(), 200);
		List<NoteRequest> noteRequests = JsonHandler.getListDtoFromResponse(response,"",new NoteRequest());
		Log.info("List "+listRequest.getTitle()+" has following notes: ");
		for (NoteRequest noteRequest1:noteRequests) {
			Log.info(noteRequest1.getContent());
		}
		response = noteController.delete(noteRequest.getId(),noteRequest.getRevision());
		Assert.verifyEquals(response.statusCode(), 204);
	}

	@Test(description = "Verify Creating new note",
			dataProvider = "TestDataProvider", groups = {"NOTES"},
			dataProviderClass = DataProviderHelper.class)
	public void test__createNote(String title) {
		NoteController noteController = new NoteController();
		Response response = noteController.create(taskRequest.getId(),title);
		Assert.verifyEquals(response.statusCode(), 201);
		NoteRequest noteRequest = JsonHandler.getDtoFromResponse(response,"",new NoteRequest());
		Assert.verifyEquals(noteRequest.getContent(),title);
		response = noteController.delete(noteRequest.getId(),noteRequest.getRevision());
		Assert.verifyEquals(response.statusCode(), 204);
	}

	@Test(description = "Verify fetching notes",
			dataProvider = "TestDataProvider", groups = {"NOTES"},
			dataProviderClass = DataProviderHelper.class)
	public void test__fetchNote(String title) {
		NoteController noteController = new NoteController();
		Response response = noteController.create(taskRequest.getId(),title);
		Assert.verifyEquals(response.statusCode(), 201);
		NoteRequest noteRequest = JsonHandler.getDtoFromResponse(response,"",new NoteRequest());
		response = noteController.fetch(noteRequest.getId());
		Assert.verifyEquals(response.statusCode(), 200);
		noteRequest = JsonHandler.getDtoFromResponse(response,"",new NoteRequest());
		Assert.verifyEquals(noteRequest.getContent(),title);
		response = noteController.delete(noteRequest.getId(),noteRequest.getRevision());
		Assert.verifyEquals(response.statusCode(), 204);
	}

	@Test(description = "Verify updating note",
			dataProvider = "TestDataProvider", groups = {"NOTES"},
			dataProviderClass = DataProviderHelper.class)
	public void test__updateNote(String title) {
		NoteController noteController = new NoteController();
		Response response = noteController.create(taskRequest.getId(),title);
		Assert.verifyEquals(response.statusCode(), 201);
		NoteRequest noteRequest = JsonHandler.getDtoFromResponse(response,"",new NoteRequest());
		Assert.verifyEquals(noteRequest.getContent(),title);
		response = noteController.update(noteRequest.getId(),noteRequest.getRevision(),"Updated title");
		Assert.verifyEquals(response.statusCode(), 200);
		NoteRequest updatedNoteRequest = JsonHandler.getDtoFromResponse(response,"",new NoteRequest());
		Assert.verifyEquals(updatedNoteRequest.getContent(),"Updated title");
		response = noteController.delete(updatedNoteRequest.getId(),updatedNoteRequest.getRevision());
		Assert.verifyEquals(response.statusCode(), 204);
	}

	@Test(description = "Verify deleting note",
			dataProvider = "TestDataProvider", groups = {"NOTES"},
			dataProviderClass = DataProviderHelper.class)
	public void test__deleteNote(String title) {
		NoteController noteController = new NoteController();
		Response response = noteController.create(taskRequest.getId(),title);
		Assert.verifyEquals(response.statusCode(), 201);
		NoteRequest noteRequest = JsonHandler.getDtoFromResponse(response,"",new NoteRequest());
		response = noteController.delete(noteRequest.getId(),noteRequest.getRevision());
		Assert.verifyEquals(response.statusCode(), 204);
	}

}
