package com.simple.mybatis.annotation.process;

import com.google.auto.service.AutoService;

import javax.annotation.processing.Processor;
import javax.annotation.processing.SupportedAnnotationTypes;

/**
 * @author FanXing
 * @date 2023年02月15日 23:14
 */
@SupportedAnnotationTypes({"com.simple.mybatis.annotation.Simpler"})
@AutoService(Processor.class)
public class ServiceImplProcessor {
}
