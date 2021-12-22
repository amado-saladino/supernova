import helpers.Faker;
import helpers.PropertyReader;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import models.User;
import models.UserReqRes;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import helpers.FileReader;
import helpers.RestUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestRest {
    private RestUtils requests;
    private Faker faker;
    private String jsonAPI = PropertyReader.getProperty("REST-JSON");

    @BeforeClass
    void setup() {
        requests = new RestUtils();
        RestAssured.baseURI = "https://reqres.in/";
        faker = new Faker();
    }

    @Test
    public void test_get_users() {
        Map params = new HashMap();
        params.put("per_page", 20);
        Response res = requests.sendWithQueryParams("api/users","",Method.GET,params);
        List<Map> users = requests.getJsonPath(res.body().asString(), "data");
        users.stream().forEach(user-> System.out.println(user.get("email")));
        List<String> names = users.stream()
                .map(user->user.get("first_name") + " " + user.get("last_name"))
                .collect(Collectors.toList());

        names.stream().forEach(name-> System.out.println(name));
        System.out.println(res.body().asString());
        res.prettyPrint();
    }

    @Test(description = "map response to model")
    void test_get_user_array() {
        Map params = new HashMap();
        params.put("per_page", 20);

        Response res = requests.sendWithQueryParams("api/users","",Method.GET, params);

        List<UserReqRes> users = Arrays.asList(requests.deserializePath(res, "data", UserReqRes[].class));
        users.stream().map(user-> user.getLast_name()).forEach(name-> System.out.println(name));
        Assert.assertTrue(users.size()==12, "Invalid user count");
        res.then().body("data.size()", is(12))
                .body("data.id", hasItems(1,2,3))
                .body("", hasKey("per_page"))
                .body("", hasEntry("total", 12))
                .body("data[0]", hasKey("first_name"))
                .body("data", everyItem(hasKey("id")));

        System.out.printf("There are %d users%n", users.size());
    }

    @Test(description = "json-server users")
    void test_read_json_file() {
        String user_txt = new FileReader("data/user-payload.json").toString();
        System.out.println(user_txt);

        System.out.println("---");
        Response res = requests.send("http://" + jsonAPI + ":81/users", user_txt, Method.POST);
        User.UserBuilder userBuilder = requests.deserializeString(user_txt, User.UserBuilder.class);
        User user = userBuilder.build();
        System.out.println(user.toString());
        System.out.println("This user has been added");
        res.prettyPrint();
    }

    @Test
    void test_resolve_vars() {
        String txt = new FileReader("data/user-placeholders.json").toString();

        Faker faker = new Faker();
        String name = faker.getFemaleFirstName();

        //Key should match the placeholder in json file
        Map vars = new HashMap();
        vars.put("name", name);
        vars.put("job", "DevOps Engineer");

        String payload = requests.resolveVarsInString(txt,vars);
        System.out.printf("This body will be sent: %s%n",payload);

        System.out.println("---");
        Response res = requests.send("api/users",payload,Method.POST);
        res.then().body("",hasEntry("name", name),"",hasEntry("job", "DevOps Engineer"))
                .body("",hasKey("id"), "",hasKey("createdAt"))
                .statusCode(equalTo(201));

        res.prettyPrint();
    }

    @Test
    void test_get_single_user() {
        Response res = requests.send("api/users/{id}","",Method.GET,"1");
        res.prettyPrint();

        UserReqRes user = requests.deserializePath(res,"data", UserReqRes.class);
        System.out.printf("user name: %s%n", user.getFirst_name() + " " + user.getLast_name());
    }

    @Test
    void test_post_user() {
        Faker faker = new Faker();
        int id = faker.getRandom().person().hashCode();
        String email = faker.getEmail();
        String fname = faker.getMaleFirstName();
        String lname = faker.getFemaleFirstName();
        String avatar = faker.getWebsite();

        UserReqRes user = new UserReqRes(id, email, fname, lname, avatar);
        Response res = requests.send("api/users",user, Method.POST);
        System.out.println(res.body().asString());
    }

    @Test(enabled = true, description = "json-server users")
    void test_post_method_body() {
        String name = faker.getFullName();
        int age = faker.getRandom().person().getAge();
        String city = faker.getCity();
        Map payload = new HashMap();
        payload.put("name", name);
        payload.put("age", age);
        payload.put("city", city);

        Response res = requests.send("http://" + jsonAPI + ":81/users",payload, Method.POST);
        User.UserBuilder userBuilder = requests.deserializePath(res,"", User.UserBuilder.class);
        User user = userBuilder.build();
        System.out.println(res.body().asString());
        System.out.printf("User added with id: %s%n", user.toString());
    }

    @Test(enabled = true, description = "json-server users")
    void test_get_method_no_body() {
        Response res = requests.send("http://" + jsonAPI + ":81/users","",Method.GET);
        System.out.println(res.body().asString());

        User.UserBuilder[] userBuilder= requests.deserializeString(res.body().asString(), User.UserBuilder[].class);
        List<User> users = Arrays.stream(userBuilder).map(x->x.build()).collect(Collectors.toList());
        users.forEach(x-> System.out.println(x.toString()));
        users.stream().map(user->user.getName()).forEach(name-> System.out.println(name));
        res.then().body("", everyItem(hasKey("id")));
    }

    @Test(enabled = true, description = "json-server users")
    void test_put_user_object() {
        String name = faker.getMaleFirstName();
        int age = faker.getRandom().person().getAge();
        String city = faker.getCity();
        User user = new User.UserBuilder(name, age).setCity(city).build();
        Response res = requests.send("http://localhost:81/users/{id}",user,Method.PUT,3);
        res.then().body("",hasEntry("name", name),"",
                hasEntry("age", age), "",hasEntry("city",city));
        res.prettyPrint();
    }

    @Test
    void test_patch_user() {
        String payload = "{\"name\": \"Ahmed\", \"job\": \"Cloud Engineer\"}";
        Response res = requests.send("api/users/{id}",payload,Method.PATCH,"3");
        res.then().body("",hasKey("name"), "", hasKey("job"));
        res.prettyPrint();
    }

    @Test
    void test_delete_user() {
        Response res = requests.send("api/users/{id}","",Method.DELETE,"4");
        res.then().statusCode(equalTo(204));
        res.prettyPrint();
    }
}
