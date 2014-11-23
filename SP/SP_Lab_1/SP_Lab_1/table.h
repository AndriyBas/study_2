//
//  table.h
//  SP_Lab_1
//
//  Created by Andriy Bas on 11/22/14.
//  Copyright (c) 2014 Andriy Bas. All rights reserved.
//

#ifndef SP_Lab_1_table_h
#define SP_Lab_1_table_h

#include <cstring>
#include <iostream>

template <typename I, typename K, typename V> class Table {
public:
  Table(const I &capacity = 2)
      : values(new V[capacity]), count(0), capacity(capacity),
        keyValue(new KeyValue[capacity]) {}

  I add(const K &key, const V &value) {
    if (count >= capacity) { // If we run out of capacity
      V *newValues = new V[capacity * 2];
      std::memcpy(newValues, values, capacity * sizeof(V));
      delete[] values;
      values = newValues;

      KeyValue *newKeyValue = new KeyValue[capacity * 2];
      std::memcpy(newKeyValue, keyValue, capacity * sizeof(KeyValue));
      delete[] keyValue;
      keyValue = newKeyValue;

      capacity *= 2;
    }

    values[count] = value;

    I i = 0;
    while (i < count && keyValue[i].key < key) {
      ++i;
    }
    for (I j = count; j > i; --j) {
      std::memcpy(keyValue + j, keyValue + j - 1, sizeof(KeyValue));
      // keyValue[j] = keyValue[j-1];
    }
    keyValue[i].key = key;
    keyValue[i].index = count;

    return count++;
  }

  void delE(const V &value) {
    V zero;
    zero.val = 0;
    for (int i = 0; i < count; i++) {

      if (values[i].val == value.val)
        values[i] = zero;
    }
  }

  V &operator[](const I &index) const { return values[index]; }

  template <typename F> void linear(const K &key, F func) const {
    bool n = false;
    for (I i = 0; i < count; ++i) {
      if (keyValue[i].key == key && n == false) {
        n = true;
        func(values[keyValue[i].index]);
      }
    }
  }

  template <typename Functor> void binary(const K &key, Functor func) const {
    binaryRec(key, 0, count, func);
  }

  template <typename DistanceFunction>
  V &get_nearest(const DistanceFunction &func) {
    decltype(func(K())) min_distance =
        std::numeric_limits<decltype(min_distance)>::max();
    V *nearest = nullptr;
    for (I i = 0; i < count; ++i) {
      decltype(min_distance) cur_distance = func(keyValue[i].key);
      if (cur_distance < min_distance) {
        nearest = &values[keyValue[i].index];
        min_distance = cur_distance;
      }
    }
    return *nearest;
  }

  I size() const { return count; }

private:
  template <typename Functor>

  void binaryRec(const K &key, const I &from, const I &to, Functor func) const {
    bool n = false;
    if (from >= to) {
      return;
    }

    long long mid = (from + to) / 2;
    if (keyValue[mid].key == key && n == false) {
      while (mid >= 0 && keyValue[mid].key == key) {
        --mid;
      }
      ++mid;
      while (mid < count && keyValue[mid].key == key) {
        if (n == false)
          func(values[keyValue[mid].index]);
        n = true;
        ++mid;
      }
    } else if (keyValue[mid].key < key) {
      binaryRec(key, mid + 1, to, func);
    } else {
      binaryRec(key, from, mid, func);
    }
  }

  struct KeyValue {
    K key;
    I index;
  };

  V *values;
  I count, capacity;

  KeyValue *keyValue;
};

#endif
