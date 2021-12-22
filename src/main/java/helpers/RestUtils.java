package helpers;

import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class RestUtils {
    private Gson gson;
    public RestUtils() {
        gson = new Gson();
    }

    /**
     * Replace placeholders with assigned values
     * @param str containing the placeholders
     * @param vars assigned values
     * @return string with placeholders resolved
     */
    public String resolveVarsInString(String str, Map vars) {
        for (Object entry : vars.keySet()) {
            String key = (String) entry;
            String val = (String) vars.get(key);

            String template = "\\{\\{" + key + "\\}\\}";
            str = str.replaceAll(template, val);
        }
        return str;
    }

    /**
     * Get value of Json key<br>
     * The returned object could be: primitive, list or map
     *
     * @param source to read from
     * @param path to key
     * @param <T> data type of key
     * @return value of the given key
     */
    public <T> T getJsonPath(String source, String path) {
        return JsonPath.from(source).get(path);
    }

    /**
     * deserialize string into object
     * @param str to deserialize
     * @param type Class of T
     * @param <T> Type of desired object
     * @return Object of the given class
     */
    public <T> T deserializeString(String str, Class<T> type) {
        return gson.fromJson(str, type);
    }

    /**
     * deserialize path as a class
     * @param res response
     * @param path inside the response body, it mustn't be a primitive type
     * @param type Class of T
     * @param <T> Type of the desired object
     * @return Instance of object as per the given class
     */
    public <T> T deserializePath(Response res, String path, Class<T> type) {
        T obj = JsonPath.from(res.body().asString()).get(path);
        String json = gson.toJson(obj);
        return gson.fromJson(json.toString(), type);
    }

    /**
     * Get response as a Type
     * @param reqSpec Request Spec
     * @param method HTTP method, i.e, GET, POST, etc.
     * @param type Class of T
     * @param <T> Type of the desired object
     * @return Instance of object as per the given class
     */
    public <T> T getResponseAs(RequestSpecification reqSpec,Method method, Class<T> type) {
        return given().spec(reqSpec).when().request(method).then().extract().as(type);
    }

    /**
     * Get a response as a desired object
     * @param url request url
     * @param queryParams query parameters
     * @param method HTTP method, e.g. GET, POST
     * @param type Class of T
     * @param <T> Type of the desired object
     * @return Instance of object as per the given class
     */
    public <T> T getResponseAs(String url, Map queryParams, Method method, Class<T> type) {
        return given().queryParams(queryParams).when().request(method).then().extract().as(type);
    }

    /**
     * Get response as a Type
     * @param url request url
     * @param method HTTP method, i.e, GET, POST, etc.
     * @param type Class of T
     * @param <T> Type of the desired object
     * @param pathParams Values to replace path parameters
     * @return Instance of object as per the given class
     */
    public <T> T getResponseAs(String url, Method method, Class<T> type, Object... pathParams) {
        return given().when().request(method,url,pathParams).then().extract().as(type);
    }

    /**
     * Send a request with path parameters
     * @param url request url
     * @param body payload to send, leave out if GET request
     * @param method HTTP method
     * @param pathParams Values to replace path parameters
     * @return Response object
     */
    public Response send(String url, String body, Method method, Object... pathParams) {
        return given().body(body).contentType(ContentType.JSON).request(method,url,pathParams);
    }

    /**
     * Send a request with path parameters
     * @param url request url
     * @param body payload to send, leave out if GET request
     * @param method HTTP method
     * @param pathParams Values to replace path parameters
     * @return Response object
     */
    public <T> Response send(String url, T body, Method method, Object... pathParams) {
        return given().body(body).contentType(ContentType.JSON).request(method,url,pathParams);
    }

    /**
     * Sends request with query parameters
     * @param url url
     * @param body request payload
     * @param method HTTP method
     * @param queryParams query parameters
     * @return Response object
     */
    public Response sendWithQueryParams(String url, String body, Method method, Map queryParams) {
        return given().queryParams(queryParams).body(body).request(method,url);
    }

    /**
     * Sends request with query parameters
     * @param url url
     * @param body request payload
     * @param method HTTP method
     * @param queryParams query parameters
     * @return Response object
     */
    public <T> Response sendWithQueryParams(String url, T body, Method method, Map queryParams) {
        return given().queryParams(queryParams).body(body).request(method,url);
    }
}
