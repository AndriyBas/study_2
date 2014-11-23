//
//  main.cpp
//  SP_Lab_1
//
//  Created by Andriy Bas on 11/22/14.
//  Copyright (c) 2014 Andriy Bas. All rights reserved.
//

#include <iostream>
#include <cstring>

#include "table.h"

using namespace std;

struct Key {
    char *str;
    unsigned short i;
    
    bool operator==(const Key& k) {
        return strcmp(k.str, str) == 0 && k.i == i;
    }
    
    bool operator<(const Key& k) {
        int j = strcmp(k.str, str);
        if (j < 0) {
            return true;
        } else if (j == 0) {
            return i < k.i;
        }
        return false;
    }

};
struct MyStruct {
    float val;
    
    friend ostream& operator<<(ostream& stream, const MyStruct& mu);
    
    friend istream& operator>>(istream& stream, MyStruct& mu);
    
};


ostream& operator<<(ostream& stream, const MyStruct& mu) {
    return stream << mu.val;
}

istream& operator>>(istream& stream, MyStruct& mu) {
    return stream >> mu.val;
}

struct distance_to {
    
    const char *key;
    
    distance_to() : key(nullptr) {}
    
    distance_to(const char *key = nullptr) : key(key) {
    }
    
    int operator()(const Key& k) const {
        int distance = 0;
        
        const char *cur = key;
        const char *cur2 = k.str;
        
        if (*cur > *cur2) {
            distance = *cur - *cur2;
        } else {
            distance = *cur2 - *cur;
        }
        
        cout << key << " and " << k.str << " = " << distance << endl;
        return distance;
    }
};

int main(int argc, const char * argv[]) {
    Table<unsigned char, Key, MyStruct> table;
    
    Key key;
    MyStruct value;
    cout << "Input value (float) or 0 to end: ";
    cin >> value;
    while (value.val > 0) {
        cout << "Input key (word + long): ";
        key.str = new char[50];
        cin >> key.str >> key.i;
        cout << "\n";
        table.add(key, value);
        
        cout << "Input value (float) or 0 to end: ";
        cin >> value;
    }
    
    unsigned char c;
    
    int i;
    cout << "Input primary index to display or negative to exit: ";
    cin >> i;
    c = i;
    while ( i >= 0 ) {
        cout << "Value at " << i << " equals " << table[c] << "\n\n";
        cout << "Input primary index to display or negative to exit: ";
        cin >> i;
        c = i;
    }
    
    
    key.str = new char[50];
    
    cout << "Input value to delete (float) or 0 to end: ";
    cin >> value;
    while (value.val != 0) {
        table.delE( value);
        cout << "Input value tot delete (float) or 0 to end: ";
        cin >> value;
    }
    cout << "Input key (word + unsigned short) (or \"exit 0\" to exit): ";
    cin >> key.str >> key.i;
    while (strcmp(key.str, "exit") != 0) {
        cout << "Linear: ";
        table.linear(key, [](MyStruct& d) { if(d.val != 0) { cout << d << " "; } else { cout << ""; } } );
        cout << "\n";
        cout << "Binary: ";
        table.binary(key, [](MyStruct& d) { if(d.val != 0) { cout << d << " "; } else { cout << ""; } } );
        cout << "\n";
        cout << "\n";
        
        cout << "Input key (word + unsigned short) (or \"exit 0\" to exit): ";
        cin >> key.str >> key.i;
    }
    
    cout << "Input word (or \"exit 0\" to exit): ";
    cin >> key.str;
    while (strcmp(key.str, "exit") != 0) {
        cout << "Nearest: " << table.get_nearest(distance_to(key.str)) << endl;
        cout << "Input word (or \"exit 0\" to exit): ";
        cin >> key.str;
    }
    
    return 0;
}