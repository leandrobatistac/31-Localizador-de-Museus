package com.betrybe.museumfinder.solution;

import com.betrybe.museumfinder.database.MuseumFakeDatabase;
import com.betrybe.museumfinder.exception.InvalidCoordinateException;
import com.betrybe.museumfinder.exception.MuseumNotFoundException;
import com.betrybe.museumfinder.model.Coordinate;
import com.betrybe.museumfinder.service.MuseumService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class MuseumServiceTest {

  private final MuseumFakeDatabase museumFakeDatabase = Mockito.mock(MuseumFakeDatabase.class);
  private final MuseumService museumService = new MuseumService(museumFakeDatabase);

  @Test
  public void shouldThrowInvalidCoordinateException() {
    Coordinate invalidCoordinate = new Coordinate(300000, 300000000);
    assertThrows(InvalidCoordinateException.class, () -> {
      museumService.getClosestMuseum(invalidCoordinate, 10.0);
    });
  }

  @Test
  public void shouldThrowMuseumNotFoundException() {
    Coordinate coordinate = new Coordinate(40.7128, -74.0060);
    when(museumFakeDatabase.getClosestMuseum(coordinate, 10.0)).thenReturn(Optional.empty());

    assertThrows(MuseumNotFoundException.class, () -> {
      museumService.getClosestMuseum(coordinate, 10.0);
    });
  }

}