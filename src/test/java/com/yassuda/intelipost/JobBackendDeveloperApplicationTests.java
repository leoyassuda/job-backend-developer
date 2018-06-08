package com.yassuda.intelipost;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"spring.jmx.enabled:true", "spring.datasource.jmx-enabled:true"})
@ActiveProfiles("test")
public class JobBackendDeveloperApplicationTests {

    private static final Logger logger = LogManager.getLogger(JobBackendDeveloperApplicationTests.class);

    private final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Rule
    public OutputCapture output = new OutputCapture();

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setUp() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    public void testLogger() {
        logger.info("Hello World - Teste de logger");
        this.output.expect(containsString("Hello World - Teste de logger"));
    }

    @Test
    public void verifyStatus() {
        String body = this.restTemplate.getForObject("/", String.class);
        Assert.assertEquals(body.toUpperCase(), "OK");
    }

    @Test
    public void verifyHealth() throws Exception {
        this.mvc.perform(get("/actuator/health"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"status\":\"UP\"")));
    }

    @Test
    public void verifyUserSignIn() throws Exception {

        this.mvc.perform(post("/api/auth/signin")
                .contentType(contentType)
                .content("{\"usernameOrEmail\":\"admin\",\"password\":\"pass#123\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"tokenType\":\"Bearer\"")));
        this.output.expect(containsString("User admin is authenticated"));
    }

    @Test
    public void verifyNonexistentUserSignIn() throws Exception {

        this.mvc.perform(post("/api/auth/signin")
                .contentType(contentType)
                .content("{\"usernameOrEmail\":\"nonexistent\",\"password\":\"pass#123\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("{\"success\":false,\"message\":\"Bad credentials\"}")));
    }

    @Test
    public void verifyUserSignUp() throws Exception {

        this.mvc.perform(post("/api/auth/signup")
                .contentType(contentType)
                .content("{\"name\":\"user-junit\"," +
                        "\"username\":\"userjunit\"," +
                        "\"email\":\"userjunit@fakemail.com\"," +
                        "\"password\":\"pass#123\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("{\"success\":true,\"message\":\"User registered successfully\"}")));
    }

    @Test
    public void verifyExistentUserSignUp() throws Exception {

        this.mvc.perform(post("/api/auth/signup")
                .contentType(contentType)
                .content("{\"name\":\"admin\"," +
                        "\"username\":\"admin\"," +
                        "\"email\":\"admin@fakemail.com\"," +
                        "\"password\":\"pass#123\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("{\"success\":false,\"message\":\"Username is already taken!\"}")));
    }

}
