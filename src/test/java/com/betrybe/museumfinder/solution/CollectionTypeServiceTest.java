package com.betrybe.museumfinder.solution;

import com.betrybe.museumfinder.database.MuseumFakeDatabase;
import com.betrybe.museumfinder.service.CollectionTypeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Javadoc.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class CollectionTypeServiceTest {

  @MockBean
  private MuseumFakeDatabase museumFakeDatabase;

  @Autowired
  private CollectionTypeService collectionTypeService;

  @Test
  public void shouldReturnExpectedCountAndTypeWhenCountByCollectionTypesIsCalled() {
    long expectedCount = 492L;
    String testCollectionType = "hist";
    mockDatabaseToReturnCount(expectedCount);

    CollectionTypeService testService = instantiateTestService();
    var actualResult = testService.countByCollectionTypes(testCollectionType);

    assertEquals(expectedCount, actualResult.count());
    assertEquals(testCollectionType, actualResult.collectionTypes()[0]);
  }

  private void mockDatabaseToReturnCount(long count) {
    Mockito.when(museumFakeDatabase.countByCollectionType(Mockito.any())).thenReturn(count);
  }

  private CollectionTypeService instantiateTestService() {
    return new CollectionTypeService(museumFakeDatabase);
  }
}