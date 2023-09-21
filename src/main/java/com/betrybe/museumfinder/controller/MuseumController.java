package com.betrybe.museumfinder.controller;

import com.betrybe.museumfinder.dto.MuseumCreationDto;
import com.betrybe.museumfinder.dto.MuseumDto;
import com.betrybe.museumfinder.model.Coordinate;
import com.betrybe.museumfinder.model.Museum;
import com.betrybe.museumfinder.service.MuseumServiceInterface;
import com.betrybe.museumfinder.util.ModelDtoConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Javadoc.
 */
@Controller
@RequestMapping("/museums")
public class MuseumController {
  private final MuseumServiceInterface museumServiceInterface;

  public MuseumController(MuseumServiceInterface museumServiceInterface) {
    this.museumServiceInterface = museumServiceInterface;
  }

  /**
   * Javadoc.
   */
  @PostMapping
  public ResponseEntity<Museum> createMuseum(@RequestBody MuseumCreationDto museumCreationDto) {
    Museum museum = ModelDtoConverter.dtoToModel(museumCreationDto);
    Museum result = museumServiceInterface.createMuseum(museum);
    return new ResponseEntity<>(result, HttpStatus.CREATED);
  }

  @GetMapping("/closest")
  public ResponseEntity<MuseumDto> getClosestMuseum(
    @RequestParam(required = true) double lat,
    @RequestParam(required = true) double lng,
    @RequestParam(required = true) Double max_dist_km) {
    Coordinate coordinate = new Coordinate(lat, lng);
    Museum museum = museumServiceInterface.getClosestMuseum(coordinate, max_dist_km);
    MuseumDto result = ModelDtoConverter.modelToDto(museum);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }
}
