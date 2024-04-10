package com.alianz.practice.alianz_practice.Entity;

import java.util.stream.Stream;

public enum ProductCatergory {
  ELECTRONICS(1),
  CLOTHING(2),
  BOOKS(3),
  HOME_APPLIANCES(4),
  SPORTS_EQUIPMENT(5),
  BEAUTY_PRODUCTS(6),
  FOOD_AND_DRINKS(7),
  FURNITURE(8),
  TOYS(9),
  JEWELRY(10),
  HEALTH_AND_WELLNESS(11),
  AUTOMOTIVE(12),
  PET_SUPPLIES(13),
  OFFICE_SUPPLIES(14),
  MUSIC_AND_MEDIA(15),
  ART_AND_CRAFTS(16),
  OTHER(17);

  private final int value;

  ProductCatergory(int value) {
    this.value = value;
  }

  public static ProductCatergory parse(int value) {
    return Stream.of(ProductCatergory.values()).filter(i -> value == i.value).findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Invalid value " + value));
  }

  public static ProductCatergory parse(String value) {
    return Stream.of(ProductCatergory.values()).filter(i -> i.name().equalsIgnoreCase(value)).findFirst()
    .orElseThrow(() -> new IllegalArgumentException("Invalid value " + value));
  }

  

  public int getValue() {
    return value;
  }

}
