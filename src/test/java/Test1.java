import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import models.User;
import models.UserReqRes;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import helpers.FileReader;
import helpers.RestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test1 {
    private RestUtils requests;

    @BeforeClass
    void setup() {
        requests = new RestUtils();
        RestAssured.baseURI = "http://localhost:3000"; //from property file
    }

    @Test
    public void test_get_users() {
        Map params = new HashMap();
        params.put("per_page", 20);

        Response res = requests.sendWithQueryParams("api/users","",Method.GET,params);

        System.out.println(res.body().asString());
        res.prettyPrint();
    }

    @Test(description = "reqres.in")
    void test_get_user_array() {
        Map params = new HashMap();
        params.put("per_page", 20);

        Response res = requests.sendWithQueryParams("api/users","",Method.GET, params);
        List<UserReqRes> data_users = requests.getJsonPath(res.body().asString(),"data");
        System.out.printf("There are %d users%n", data_users.size());
        System.out.println("end");
    }

    @Test
    void test_read_json_file() {
        String user_txt = new FileReader("data/user-payload.json").toString();
        System.out.println(user_txt);

        System.out.println("---");
        User user = requests.deserializeString(user_txt, User.class);
        System.out.printf("user id: %s%nname: %s%n",user.getId(),user.getName());
    }

    @Test
    void test_resolve_vars() {
        String txt = new FileReader("data/user-placeholders.json").toString();
        Map vars = new HashMap();
        vars.put("id", "");
        vars.put("name", "Medhat");
        vars.put("age", "36");
        vars.put("city","Aswan");

        String payload = requests.resolveVarsInString(txt,vars);
        System.out.printf("This body will be sent: %s%n",payload);

        System.out.println("---");
        Response res = requests.send("users",payload,Method.POST);
        res.prettyPrint();
    }

    /**
     * http://reqres.in/api/users
     */
    @Test
    void test_get_single_user() {
        Response res = requests.send("api/users/{id}","",Method.GET,"1");
        res.prettyPrint();

        UserReqRes user = requests.deserializePath(res,"data", UserReqRes.class);
        System.out.printf("user name: %s%n", user.getFirst_name() + " " + user.getLast_name());
    }

    @Test
    void test_post_user() {
        User user = new User("","Arabi",22, "Cairo");
        Response res = requests.send("users",user, Method.POST);
        System.out.println(res.body().asString());
    }

    @Test
    void test_post_method_body() {
        String payload = "{\"name\":\"Hagar\",\"age\":23,\"city\": \"Paris\"}";
        Response res = requests.send("users",payload, Method.POST);
        User user = requests.deserializePath(res,"",User.class);
        System.out.println(res.body().asString());
        System.out.printf("User added with id: %s%n", user.getId());
    }

    @Test
    void test_get_method_no_body() {
        Response res = requests.send("api/users","",Method.GET);
        System.out.println(res.body().asString());
    }

    @Test
    void test_put_json_user() {
        //base URL: http://localhost:3000
        String body = "{\"name\": \"Hassan\",\"age\": 20,\"city\": \"Giza\"}";

        Response res = requests.send("users/{id}",body,Method.PUT,"2");
        res.prettyPrint();
    }

    @Test
    void test_put_user_object() {
        User user = new User("4", "Adam", 17, "London");
        Response res = requests.send("users/{id}",user,Method.PUT,3);
        res.prettyPrint();
    }

    @Test
    void test_patch_user() {
        String payload = "{\"name\": \"Khaled\", \"age\": 20}";
        Response res = requests.send("users/{id}",payload,Method.PATCH,"BOwl-Rb");
        res.prettyPrint();
    }

    @Test
    void test_delete_user() {
        Response res = requests.send("users/{id}","",Method.DELETE,"2");
        res.prettyPrint();
    }
}
