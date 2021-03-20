package com.cars.app.web.rest;

import com.cars.app.domain.Car;
import com.cars.app.repository.CarRepository;
import com.cars.app.repository.search.CarSearchRepository;
import com.cars.app.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.cars.app.domain.Car}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CarResource {

    private final Logger log = LoggerFactory.getLogger(CarResource.class);

    private final CarRepository carRepository;

    private final CarSearchRepository carSearchRepository;

    public CarResource(CarRepository carRepository, CarSearchRepository carSearchRepository) {
        this.carRepository = carRepository;
        this.carSearchRepository = carSearchRepository;
    }

    /**
     * {@code GET  /cars} : get all the cars.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cars in body.
     */
    @GetMapping("/cars")
    public ResponseEntity<List<Car>> getAllCars(Pageable pageable) {
        log.debug("REST request to get a page of Cars");
        Page<Car> page = carRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cars/:id} : get the "id" car.
     *
     * @param id the id of the car to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the car, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cars/{id}")
    public ResponseEntity<Car> getCar(@PathVariable Long id) {
        log.debug("REST request to get Car : {}", id);
        Optional<Car> car = carRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(car);
    }

    /**
     * {@code SEARCH  /_search/cars?query=:query} : search for the car corresponding
     * to the query.
     *
     * @param query the query of the car search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/cars")
    public ResponseEntity<List<Car>> searchCars(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Cars for query {}", query);
        Page<Car> page = carSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
