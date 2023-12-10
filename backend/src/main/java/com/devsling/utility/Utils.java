package com.devsling.utility;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class Utils {

  private final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

  public static String generateRandomStr(Random random, int length) {

    StringBuilder sb = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
    }
    return sb.toString();

  }

  public <T> Stream<T> checkStream(Collection<T> collection) {
    if (Objects.isNull(collection)) {
      return Stream.empty();
    }
    return collection.stream();
  }

  public LocalDate parseDateImport(String date) {
    try {
      if (date == null) {
        return null;
      }

      return LocalDate.parse(date);
    } catch (Exception e) {
      log.error(e.getMessage());
      return null;
    }

  }

  public <T> List<T> addToList(List<T> list, T element) {

    return addToList(list, Arrays.asList(element));

  }

  public <T> List<T> addToList(List<T> list, List<T> elements) {

    if (elements == null || elements.isEmpty()) {
      return list;
    }
    if (list == null || list.isEmpty()) {
      return elements;
    }
    return Stream.of(list, elements).flatMap(List::stream).filter(e -> e != null).distinct()
        .collect(Collectors.toList());

  }

  public <T> List<T> removeFromList(List<T> list, T element) {

    if (element == null) {
      return list;
    }
    if (list == null || list.isEmpty()) {
      return new ArrayList<>();
    }
    return list.stream().filter(e -> e != null).filter(e -> !e.equals(element)).distinct().collect(Collectors.toList());

  }

  public <T> UnaryOperator<T> peek(Consumer<T> c) {
    return x -> {
      c.accept(x);
      return x;
    };
  }

  public void sleepMiliSeconds(int miliSeconds) {
    try {
      Thread.sleep(miliSeconds * 1l);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      log.error(e.getMessage(), e);
    }

  }

  public void sleepSeconds(int seconds) {

    sleepMiliSeconds(seconds * 1000);

  }

  public void sleepMinutes(int minutes) {
    sleepSeconds(minutes * 60);
  }

  public void sleepHours(int hours) {
    sleepMinutes(hours * 60);
  }
}
