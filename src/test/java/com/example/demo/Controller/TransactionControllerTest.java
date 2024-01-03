package com.example.demo.Controller;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void testCalculateAllRewards() throws Exception {
        String jsonContent = new String(Files.readAllBytes(
                Paths.get(new ClassPathResource("request.json").getURI())));


        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/Transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent);


        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @Order(2)
    public void testGetTotalRewardsById() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/Total/1");
        MvcResult resultCustomer1 = mockMvc.perform(requestBuilder).andReturn();
        String requestResultCustomer1 = resultCustomer1.getResponse().getContentAsString();;
        Assertions.assertEquals("{\"1\":180}", requestResultCustomer1);

        requestBuilder = MockMvcRequestBuilders
                .get("/Total/2");
        MvcResult resultCustomer2 = mockMvc.perform(requestBuilder).andReturn();
        String requestResultCustomer2 = resultCustomer2.getResponse().getContentAsString();;
        Assertions.assertEquals("{\"2\":90}", requestResultCustomer2);

        requestBuilder = MockMvcRequestBuilders
                .get("/Total/3123142");
        MvcResult resultCustomer3 = mockMvc.perform(requestBuilder).andReturn();
        String requestResultCustomer3 = resultCustomer3.getResponse().getContentAsString();;
        Assertions.assertEquals("{\"3123142\":0}", requestResultCustomer3);

        requestBuilder = MockMvcRequestBuilders
                .get("/Total/1231");
        MvcResult resultCustomer4 = mockMvc.perform(requestBuilder).andReturn();
        String requestResultCustomer4 = resultCustomer4.getResponse().getContentAsString();;
        Assertions.assertEquals("{\"1231\":0}", requestResultCustomer4);

        requestBuilder = MockMvcRequestBuilders
                .get("/Total/6");
        MvcResult resultCustomerNotFound = mockMvc.perform(requestBuilder).andReturn();
        String stringCustomerNotFound = resultCustomerNotFound.getResponse().getContentAsString();;
        Assertions.assertEquals("No rewards record found for this customer id: 6", stringCustomerNotFound);
    }

    @Test
    @Order(3)
    public void testGetMonthRewardsById() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/Month/1");
        MvcResult resultCustomer1 = mockMvc.perform(requestBuilder).andReturn();
        String requestResultCustomer1 = resultCustomer1.getResponse().getContentAsString();;
        Assertions.assertEquals("{\"2023-12\":90,\"2023-5\":0,\"2023-2\":90}", requestResultCustomer1);

        requestBuilder = MockMvcRequestBuilders
                .get("/Month/2");
        MvcResult resultCustomer2 = mockMvc.perform(requestBuilder).andReturn();
        String requestResultCustomer2 = resultCustomer2.getResponse().getContentAsString();;
        Assertions.assertEquals("{\"2023-2\":90}", requestResultCustomer2);

        requestBuilder = MockMvcRequestBuilders
                .get("/Month/3123142");
        MvcResult resultCustomer3 = mockMvc.perform(requestBuilder).andReturn();
        String requestResultCustomer3 = resultCustomer3.getResponse().getContentAsString();;
        Assertions.assertEquals("{\"2023-2\":0}", requestResultCustomer3);

        requestBuilder = MockMvcRequestBuilders
                .get("/Month/1231");
        MvcResult resultCustomer4 = mockMvc.perform(requestBuilder).andReturn();
        String requestResultCustomer4 = resultCustomer4.getResponse().getContentAsString();;
        Assertions.assertEquals("{\"2023-5\":0}", requestResultCustomer4);

        requestBuilder = MockMvcRequestBuilders
                .get("/Month/6");
        MvcResult resultCustomerNotFound = mockMvc.perform(requestBuilder).andReturn();
        String stringCustomerNotFound = resultCustomerNotFound.getResponse().getContentAsString();;
        Assertions.assertEquals("No rewards record found for this customer id: 6", stringCustomerNotFound);
    }

    @Test
    @Order(4)
    public void testRewardsById() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/Rewards/1");
        MvcResult resultCustomer1 = mockMvc.perform(requestBuilder).andReturn();
        String requestResultCustomer1 = resultCustomer1.getResponse().getContentAsString();;
        Assertions.assertEquals("{\"1\":{\"2023-12\":90,\"Total\":180,\"2023-5\":0,\"2023-2\":90}}", requestResultCustomer1);

        requestBuilder = MockMvcRequestBuilders
                .get("/Rewards/2");
        MvcResult resultCustomer2 = mockMvc.perform(requestBuilder).andReturn();
        String requestResultCustomer2 = resultCustomer2.getResponse().getContentAsString();;
        Assertions.assertEquals("{\"2\":{\"Total\":90,\"2023-2\":90}}", requestResultCustomer2);

        requestBuilder = MockMvcRequestBuilders
                .get("/Rewards/3123142");
        MvcResult resultCustomer3 = mockMvc.perform(requestBuilder).andReturn();
        String requestResultCustomer3 = resultCustomer3.getResponse().getContentAsString();;
        Assertions.assertEquals("{\"3123142\":{\"Total\":0,\"2023-2\":0}}", requestResultCustomer3);

        requestBuilder = MockMvcRequestBuilders
                .get("/Rewards/1231");
        MvcResult resultCustomer4 = mockMvc.perform(requestBuilder).andReturn();
        String requestResultCustomer4 = resultCustomer4.getResponse().getContentAsString();;
        Assertions.assertEquals("{\"1231\":{\"Total\":0,\"2023-5\":0}}", requestResultCustomer4);

        requestBuilder = MockMvcRequestBuilders
                .get("/Month/6");
        MvcResult resultCustomerNotFound = mockMvc.perform(requestBuilder).andReturn();
        String stringCustomerNotFound = resultCustomerNotFound.getResponse().getContentAsString();;
        Assertions.assertEquals("No rewards record found for this customer id: 6", stringCustomerNotFound);
    }
}
