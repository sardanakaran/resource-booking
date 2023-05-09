package com.osttra;

import com.osttra.resource.OsttraSeatBookingApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = OsttraSeatBookingApplication.class)
public class ControllerTest {

    @Autowired
    private WebApplicationContext wac;

    public MockMvc mockMvc;


    @Before
    public void setup() {
        DefaultMockMvcBuilder builder = MockMvcBuilders
                .webAppContextSetup(this.wac)
                .dispatchOptions(true);
        this.mockMvc = builder.build();
    }

    @Test
    public void testCors() throws Exception {
        this.mockMvc
                .perform(options("/api/ping")
                .header("Access-Control-Request-Method", "OPTIONS")
                .header("Origin","http://zz"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Access-Control-Allow-Methods", "OPTIONS,GET,POST,PUT,DELETE"));
    }
}
