package com.devsling.utility;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CheckUtility {

  public String checkString(String s) {

    return Optional.ofNullable(s).map(String::trim).filter(e -> !e.isEmpty()).orElse(null);

  }

  public String concatString(List<String> strs) {

    return Utils.checkStream(strs).collect(Collectors.joining(", "));

  }

  public Long checkLong(String s) {

    return Optional.ofNullable(checkDouble(s)).map(e -> Long.valueOf(e.longValue())).orElse(null);

  }

  public Integer checkInteger(String s) {
    return Optional.ofNullable(checkDouble(s)).map(e -> Integer.valueOf(e.intValue())).orElse(null);
  }

  public Double checkDouble(String s) {
    try {
      return Double.parseDouble(checkString(s));
    } catch (Exception e) {
      return null;
    }
  }

  public LocalDate checkDate(String s) {
    try {
      return LocalDate.parse(checkString(s));
    } catch (Exception e) {
      return null;
    }
  }

}
