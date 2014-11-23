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
    S2, S3, S4, S5, S6, S7, S8, S9, nStates
};

Signal charToSignal(char c);

int main() {
    Operator *op1;
    op1 = new Dycrement(new Variable("n"));
    op1 = new Process(op1);
    Operator *op2;
    op2 = new Braces(new Variable("a"), new Variable("n"));
    op2 = new Binary(new Variable("b"), op2, "==");
    op2 = new Binary(new Variable("n"), op2, "&&");
    op2 = new Loop(op2);
    std::cout << op1->show() + ";" + op2->show() + ";" << std::endl << std::endl;
    delete op1;
    delete op2;
    
    State transitionTableConst[nStates][nSignals] = {
        //  { DELIMETER, NUMBER, LETTER, SIGN },
        { S2, S2, S7, S9 },
        { S2, S3, S2, S2 },
        { S8, S3, S3, S4 },
        { S4, S4, S9, S4 },
        { S5, S2, S9, S5 },
        { S7, S5, S7, S7 },
        { S6, S8, S6, S5 },
        { S8, S9, S3, S2 }
    };
    
    State **transitionTable = new State*[nStates];
    for (int i = 0; i < nStates; ++i) {
        transitionTable[i] = transitionTableConst[i];
    }
    
    Machine machine(transitionTable, S2);
    
    std::string str;
    std::cout << "Input string: ";
    std::getline(std::cin, str);
    
    for (std::string::iterator i = str.begin(); i != str.end(); ++i) {
        Signal signal = charToSignal(*i);
        State oldState = machine.getState();
        State newState = machine.step(signal);
        
        std::cout << "Char: \"" << *i << "\"\tOld State: " << oldState+2 << "\tNew State: " << newState+2 << "\n";
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
