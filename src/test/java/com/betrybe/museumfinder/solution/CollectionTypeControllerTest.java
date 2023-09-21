package com.betrybe.museumfinder.solution;

import com.betrybe.museumfinder.database.MuseumFakeDatabase;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Javadoc.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class CollectionTypeControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private MuseumFakeDatabase database;

  @Test
  public void shouldReturnCountForSingleType() throws Exception {
    mockDatabaseResponse(1L);
    performGetRequest("/collections/count/ab")
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.count").value(1L));
  }

  @Test
  public void shouldReturnCountForMultipleTypes() throws Exception {
    mockDatabaseResponse(2L);
    performGetRequest("/collections/count/abc,def")
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.count").value(4L));
  }

  @Test
  public void shouldReturnNotFoundForZeroCount() throws Exception {
    mockDatabaseResponse(0L);
    performGetRequest("/collections/count/abc")
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$").doesNotExist());
  }

  @Test
  public void shouldReturnNotFoundForMultipleNotFound() throws Exception {
    mockDatabaseResponse(0L);
    performGetRequest("/collections/count/abc,def")
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$").doesNotExist());
  }

  private void mockDatabaseResponse(Long response) {
    Mockito.when(database.countByCollectionType(any(String.class))).thenReturn(response);
  }

  private org.springframework.test.web.servlet.ResultActions performGetRequest(String url) throws Exception {
    return mockMvc.perform(MockMvcRequestBuilders.get(url));
  }
}