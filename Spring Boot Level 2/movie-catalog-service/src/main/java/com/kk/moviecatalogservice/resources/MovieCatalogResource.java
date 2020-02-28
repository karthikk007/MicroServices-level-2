package com.kk.moviecatalogservice.resources;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;

import com.kk.moviecatalogservice.models.Movie;
import com.kk.moviecatalogservice.models.UserRating;
import com.kk.moviecatalogservice.service.MovieInfo;
import com.kk.moviecatalogservice.service.UserRatingInfo;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kk.moviecatalogservice.models.CatalogItem;
import com.kk.moviecatalogservice.models.Rating;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

	@Autowired
	private RestTemplate restTemplate;

//	@Autowired
//	private DiscoveryClient discoveryClient;

	@Autowired
	private WebClient.Builder webClientBuilder;

	@Autowired
	MovieInfo movieInfo;

	@Autowired
	UserRatingInfo userRatingInfo;

	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

		WebClient.Builder builder = WebClient.builder();

		UserRating ratings = userRatingInfo.getUserRatings(userId);

		return ratings.getUserRatings().stream().map(rating -> {
			return movieInfo.getCatalogItem(rating);
		}).collect(Collectors.toList());

	}
}
