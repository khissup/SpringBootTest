package com.example.ksrdemo;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.Comparator;

class Solution {
  public boolean solution(String[] phone_book) {
    int preContainedIdx = -1;
    int preLargeStrIdx = -1;
    String  preLargeStartStrHashCode;
    int length = phone_book.length;
    Arrays.sort(phone_book, Comparator.comparingInt(String::length));

    // caches[i][j] -> i번째 문자열 j길이만큼의 hashCode
    // int[][] hashCaches = new int[length][length];
    Map<String, String> hashCaches = new HashMap<>();


    for (int i = 0; i < phone_book.length; i++) {
      for (int j = i + 1; j < phone_book.length; j++) {
        preContainedIdx = i;
        preLargeStrIdx = j;


        String key = String.format("%d,%d", preLargeStrIdx, phone_book[preContainedIdx].length());
        if (hashCaches.containsKey(key)) {
          preLargeStartStrHashCode= hashCaches.get(String.format("%d,%d", preLargeStrIdx, phone_book[preContainedIdx].length()));
        }else{
          preLargeStartStrHashCode =  phone_book[preLargeStrIdx].substring(0,phone_book[preContainedIdx].length());
          hashCaches.put(key,preLargeStartStrHashCode);
        }

        if (phone_book[preContainedIdx].hashCode() ==preLargeStartStrHashCode.hashCode()) {
          return false;
        }


      }
    }
    return true;
  }

}