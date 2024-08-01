package com.qa.API.RestAssured;

import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class Get_Legacy_BookStore {
	static String baseURL = "https://demoqa.com/BookStore/v1/Books";

	@Test
	public void AllBooksDetails() {
		Response response = given().contentType(ContentType.JSON).when().get(baseURL).then().extract().response();
		System.out.println(response.asString());
	}

	@Test
	public void TotalBooksInBookStore() {
		Response response = given().contentType(ContentType.JSON).when().get(baseURL).then().extract().response();
		JsonPath jp = new JsonPath(response.asString());
		int size = jp.get("books.isbn.size()");
		System.out.println("Total Books In Book Store: " + size);
	}

	@Test
	public void AllBooksISBNs() {
		Response response = given().contentType(ContentType.JSON).when().get(baseURL).then().extract().response();
		System.out.println("All Books Data :"+response.jsonPath().getList("books.isbn").toString().trim());
	}

	@Test
	public void BookNameForParticularISBN() {
		Response response = given().contentType(ContentType.JSON).when().get(baseURL).then().extract().response();
		JsonPath j = new JsonPath(response.asString());
		int s = j.getInt("books.isbn.size()");
		System.out.println("Total Books In Book Store: " + s);
		for (int i = 0; i < s; i++) {
			String isbn = j.getString("books[" + i + "].isbn");
			if (isbn.equalsIgnoreCase("9781449325862")) {
				String title = j.getString("books[" + i + "].title");
				System.out.println("Book Title for searched ISBN is: " + title);
				break;
			}
		}
	}

	@Test
	public void GetTitleOfFirstBook() {
		Response response = given().contentType(ContentType.JSON).when().get(baseURL).then().extract().response();
		JsonPath j = new JsonPath(response.asString());
		String title1 = j.getString("books[0].title");
		System.out.println("Title for 1st Book is: " + title1);
		String title2 = j.get("books[1].title");
		System.out.println("Title for 2nd Book is: " + title2);
	}
	
	@Test
	public void GetTitleOfLastBook() {
		Response response = given().contentType(ContentType.JSON).when().get(baseURL).then().extract().response();
		JsonPath j = new JsonPath(response.asString());
		int lastRecord = j.getInt("books.isbn.size()")-1;
		String title = j.getString("books["+lastRecord+"].title");
		System.out.println("Title for last Book is: " + title);
	}
	
	@Test
	public void GetAllDetailsOfFirstBook() {
		Response response = given().contentType(ContentType.JSON).when().get(baseURL).then().extract().response();
		JsonPath j = new JsonPath(response.asString());
		String title = j.getString("books[0]");
		System.out.println("All details for first book are: " + title);
		System.out.println("API Response Time in ms: "+response.getTime());
	}
}