//
//  main.cpp
//  SP_Lab_2
//
//  Created by Andriy Bas on 11/23/14.
//  Copyright (c) 2014 Andriy Bas. All rights reserved.
//

#include <iostream>
#include <string>

#include "operators.h"
#include "machine.h"

enum Signals {
    DELIMETER, NUMBER, LETTER, SIGN, nSignals
};

enum States {
    S0,S1, S2, S3, S4, S5, S6, S7, S8, S9, nStates
};

Signal charToSignal(char c);
//b=(2*a +c/d)*2*a;;
int main() {
    Operator *o1 = new Binary(new Variable("2"), new Variable("a"), "*");
    
    Operator *o2 = new Binary(new Variable("c"), new Variable("d"), "/");
    
    Operator *o3 = new Binary(o1, o2, "+");
    
    Operator *o4 = new Braces(o3);
    
    Operator *o5 = new Binary(o4, new Variable("2"), "*");
    
    Operator *o6 = new Binary(o5, new Variable("a"), "*");
    
    Operator *o7 = new Binary(new Variable("b"), o6, "=");

    Operator *o8 = new Cond(new Variable("c"), o7);
    
    std::cout << o8->show() + ";" << std::endl << std::endl;

    delete o8;
    
    State transitionTableConst[nStates][nSignals] = {
        //  { DELIMETER, NUMBER, LETTER, SIGN },
        { S2, S2, S7, S9 }, //0
        { S2, S3, S2, S2 }, //1
        { S8, S5, S3, S4 }, //2
        { S7, S7, S9, S4 }, //3
        { S5, S2, S9, S5 }, //4
        { S7, S8, S7, S7 }, //5
        { S6, S8, S6, S5 }, //6
        { S8, S9, S5, S2 }, //7
        { S6, S8, S6, S5 }, //8
        { S8, S9, S3, S2 }  //9
        
    };
    
    State **transitionTable = new State*[nStates];
    for (int i = 0; i < nStates; ++i) {
        transitionTable[i] = transitionTableConst[i];
    }
    
    Machine machine(transitionTable, S3);
    
    std::string str;
    std::cout << "Input string: ";
    std::getline(std::cin, str);
    
    for (std::string::iterator i = str.begin(); i != str.end(); ++i) {
        Signal signal = charToSignal(*i);
        State oldState = machine.getState();
        State newState = machine.step(signal);
        
        std::cout << "Char: \"" << *i << "\"\tOld State: " << oldState << "\tNew State: " << newState << "\n";
    }
    
    std::cin.get();
    return 0;
}

Signal charToSignal(char c) {
    if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
        return LETTER;
    }
    if (c == ' ' || c == '\t') {
        return DELIMETER;
    }
    if (c >= '0' && c <= '9') {
        return NUMBER;
    }
    return SIGN;
    
}
