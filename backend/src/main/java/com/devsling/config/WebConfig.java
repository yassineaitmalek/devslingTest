package com.devsling.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.core.convert.converter.Converter;
import com.devsling.constants.FuelType;
import com.devsling.constants.TransmissionType;

// @Configuration
// public class WebConfig {
// }

@Configuration
public class WebConfig implements WebMvcConfigurer {
  @Override
  public void addFormatters(FormatterRegistry registry) {
    registry.addConverter(new FuelTypeConverter());
    registry.addConverter(new TransmissionTypeConverter());
  }

  public class FuelTypeConverter implements Converter<String, FuelType> {

    @Override
    public FuelType convert(String value) {
      return FuelType.of(value);
    }

  }

  public class TransmissionTypeConverter implements Converter<String, TransmissionType> {

    @Override
    public TransmissionType convert(String value) {
      return TransmissionType.of(value);
    }

  }
}