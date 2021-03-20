package com.cars.app.repository.search;

import com.cars.app.domain.Car;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Car} entity.
 */
public interface CarSearchRepository extends ElasticsearchRepository<Car, Long> {
}
