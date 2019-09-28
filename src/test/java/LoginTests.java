import com.nagarro.utils.Common;
import com.nagarro.utils.Log;
import org.testng.annotations.Test;

public class LoginTests {
	@Test(description = "Verify token generation", groups = {"Login"})
	public void test_tokenGeneration(){
		String accessToken = Common.getAccessToken();
		Log.info("Access Token: "+accessToken);
	}

	@Test(description = "Verify token retrival", groups = {"Login"})
	public void test_tokenRetrival(){
		String accessToken = Common.getAccessToken();
		Log.info("Access Token: "+accessToken);
	}
}
