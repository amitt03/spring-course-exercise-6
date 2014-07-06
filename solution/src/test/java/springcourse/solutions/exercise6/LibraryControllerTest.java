package springcourse.solutions.exercise6;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import springcourse.solutions.exercise6.application.AppConfig;
import springcourse.solutions.exercise6.controller.config.ControllerConfig;
import springcourse.solutions.exercise6.model.Book;

import static com.jayway.jsonassert.impl.matcher.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Amit Tal
 * @since 4/13/2014
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {AppConfig.class, ControllerConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class LibraryControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    private Gson gson;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        this.gson = new Gson();
    }

    @Test
    public void testCreateBook() throws Exception {
        Book book = new Book("Pro Spring 3", "Clarence Ho");
        String bookJson = gson.toJson(book);
        this.mockMvc.perform(post("/books").
                content(bookJson).
                contentType(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON)).
                andExpect(status().isCreated()).
                andDo(print());
    }

    // Notice that this test expects exception
    // This is because two things:
    // 1. Delete logic throws an exception in the dao (since catalogId is fake, cannot delete a non-existing book)
    // 2. Exception Handler does not exist in order to handle the exception and return a meaningful error
    @Test(expected = Exception.class)
    public void testDeleteBook() throws Exception {
        this.mockMvc.perform(delete("/books/{catalogId}", "dummy-catalog-id"));
    }

    @Test
    public void testReadAllBooks() throws Exception {
        this.mockMvc.perform(get("/books")).
                andExpect(status().isOk()).
                andExpect(jsonPath("$").isArray()).
                andExpect(jsonPath("$", hasSize(6))).
                andDo(print()).
                andReturn();
    }

    @Test
    public void testReadBooksByAuthor() throws Exception {
        this.mockMvc.perform(get("/books").param("author", "J. R. R. Tolkien")).
                andExpect(status().isOk()).
                andExpect(jsonPath("$").isArray()).
                andExpect(jsonPath("$", hasSize(4))).
                andDo(print()).
                andReturn();
    }
}
