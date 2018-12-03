package testautomation;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class SampleTest {

	public static String containerId;
	public static String versionId ;

	@org.testng.annotations.BeforeClass
	public static void setUp() throws FileNotFoundException {

		RestAssured.baseURI = "https://hcmkishorej11-ms1.csacloud.local:8444/ara/api/codar";
		PrintStream fileOutPutStream = new PrintStream(new File("C:/somelogfile.txt"));
		RestAssured.config = RestAssuredConfig.config().logConfig(new LogConfig().defaultStream(fileOutPutStream));
		

		
	}
	
	
	
	@Test(description = "Test List all applications.")
	
	public void test1() throws FileNotFoundException {

		// RestAssured.authentication = authScheme;
		given().log();
		Response response = given().relaxedHTTPSValidation().auth().preemptive().basic("admin", "Cloud_123").when()
				.get("/applications");
		response.then().log().all();
		System.out.println("Status code: " + response.getStatusCode());
		System.out.println("Status message " + response.body().asString());
		System.out.println("Time for list APplication" + response.time());
		//response.then().assertThat().statusCode(200);
		response.then().assertThat().body("applications.name", hasSize(10)).time(lessThan(4000L));

	}

	@Test(description = "Test Create application.")
	public void test2() {

		// RestAssured.authentication = authScheme;
		String json = "{\"name\":\"AppTest\",\"description\":\"testdescription\",\"applicationVersion\":\"1.0.0\",\"icon\":\"/ara/api/blobstore/applications.png?tag=library\",\"modelContainerId\":\"\",\"modelType\":\"DESIGN_LESS\",\"apiConfigured\":false,\"tags\":[],\"applicationContainerId\":\"\",\"providerId\":\"\"}";
		Response response = given().relaxedHTTPSValidation().auth().preemptive().basic("admin", "Cloud_123")
				.contentType(ContentType.JSON).body(json).when().post("/applications");
		
		containerId = response.body().jsonPath().getString("applicationContainerId");
		versionId = response.body().jsonPath().getString("applicaitonId");

		given().log();
		response.then().log().all();
		response.then().assertThat().statusCode(200);

	}

	@Test(description = "Test List all applications after create.")
	public void test3() {

		RestAssured.baseURI = "https://hcmkishorej11-ms1.csacloud.local:8444/ara/api/codar";

		// RestAssured.authentication = authScheme;

		Response response = given().relaxedHTTPSValidation().auth().preemptive().basic("admin", "Cloud_123").when()
				.get("/applications");

		given().log();
		response.then().assertThat().statusCode(200);
		response.then().log().all();

		response.then().assertThat().body("applications.name", hasSize(11));
		//response.then().assertThat().body("applications.name", contains(equalTo("AppTest")));

	}

	@Test(description = "Test get applications.")
	public void test4() {

		RestAssured.baseURI = "https://hcmkishorej11-ms1.csacloud.local:8444/ara/api/codar";

		// RestAssured.authentication = authScheme;
		
		Response response = given().relaxedHTTPSValidation().auth().preemptive().basic("admin", "Cloud_123").when()
				.get("/applications/" + containerId);

		given().log();
		response.then().log().all();
		response.then().assertThat().statusCode(200);
		response.then().assertThat().body("name", equalTo("AppTest")).body("description", equalTo("testdescription"))
				.body("modelType", equalTo("DESIGN_LESS")).body("createdOn", notNullValue())
				.body("icon", notNullValue()).body("versions", hasSize(1))
				.body("versions[0].versionDescription", equalTo("testdescription"))
				.body("versions[0].applicationVersion", equalTo("1.0.0")).body("versions[0].createdOn", notNullValue())
				.body("versions[0].createdBy", equalTo("admin"));

	}
	
	@Test(description = "Test Delete application.")
	public void test5() {

		RestAssured.baseURI = "https://hcmkishorej11-ms1.csacloud.local:8444/ara/api/codar";

		// RestAssured.authentication = authScheme;
		
		Response response = given().relaxedHTTPSValidation().auth().preemptive().basic("admin", "Cloud_123").when()
				.delete("/applications/"+containerId+"/version/"+versionId);

		given().log();
		response.then().log().all();
		response.then().assertThat().statusCode(204);
		

	}
	
	@Test(description = "Test List all applications after delete.")
	public void test6() {

		RestAssured.baseURI = "https://hcmkishorej11-ms1.csacloud.local:8444/ara/api/codar";

		// RestAssured.authentication = authScheme;

		Response response = given().relaxedHTTPSValidation().auth().preemptive().basic("admin", "Cloud_123").when()
				.get("/applications");

		given().log();
		response.then().assertThat().statusCode(200);
		response.then().log().all();

		response.then().assertThat().body("applications.name", hasSize(10));
		

	}

}
