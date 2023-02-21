package com.simple.mybatis.sample.controller;

import com.simple.mybatis.sample.domain.City;
import com.simple.mybatis.sample.domain.CityService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author FanXing
 * @date 2023年02月16日 10:43
 */
@RestController
@RequestMapping("/city")
public class CityController extends BaseController<City, CityService> {
}
