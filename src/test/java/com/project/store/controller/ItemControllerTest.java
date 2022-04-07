package com.project.store.controller;

import com.project.store.model.Item;
import com.project.store.model.UserRole;
import com.project.store.service.ItemService;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.project.store.model.UserRole.ADMIN;
import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @Test
    void shouldShowAllItems() throws Exception {
        when(itemService.getAllItems())
                .thenReturn(List.of(new Item("name", "description", valueOf(1))));

        mockMvc
                .perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("name"))
                .andExpect(jsonPath("$[0].description").value("description"))
                .andExpect(jsonPath("$[0].price").value(valueOf(1)));
    }

    @Test
    void shouldShowItem() throws Exception {
        when(itemService.getItemById(1L))
                .thenReturn(new Item(1L,"name", "description", valueOf(1)));

        mockMvc
                .perform(get("/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['id']").value(1L))
                .andExpect(jsonPath("$['name']").value("name"))
                .andExpect(jsonPath("$['description']").value("description"))
                .andExpect(jsonPath("$['price']").value(valueOf(1)));
    }

    @Test
    void shouldUpdateItem() throws Exception {
        Item item = new Item(1L,"name", "description", valueOf(1));
        doAnswer((Answer<Void>) invocationOnMock -> {
            item.setName("nam");
            item.setDescription("des");
            return null;
        }).when(itemService).updateItem(1L, "nam", "des");

        mockMvc
                .perform(put("/1")
                        .param("name","nam")
                        .param("description","des")
                        .with(user("admin").roles(ADMIN.name())))
                .andExpect(status().isOk());

        assertEquals(item.getName(),"nam");
        assertEquals(item.getDescription(),"des");
    }

    @Test
    void shouldCreateItem() throws Exception {
        mockMvc
                .perform(post("/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"name\": \"apple\", \"description\": \"description\", \"price\": 10 }")
                    .with(user("admin").roles(ADMIN.name())))
                .andExpect(status().isOk());

        verify(itemService).createItem(any(Item.class));
    }

    @Test
    void shouldDeleteItem() throws Exception {
        mockMvc
                .perform(delete("/1")
                    .with(user("admin").roles(ADMIN.name())))
                .andExpect(status().isOk());

        verify(itemService).deleteItemById(1L);
    }
}