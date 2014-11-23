#include "operators.h"


Braces::Braces(Operator *left, Operator *right)
    : left(left)
    , right(right)
{
}

Braces::~Braces() {
    delete left;
    delete right;
}

std::string Braces::show() const {
    return left->show() + "[" + right->show() + "]";
}

Binary::Binary(Operator *left, Operator *right, std::string operatorSign)
    : left(left)
    , right(right)
    , operatorSign(operatorSign)
{
}

Binary::~Binary() {
    delete left;
    delete right;
}

std::string Binary::show() const {
    return left->show() + operatorSign + right->show();
}

Loop::Loop(Operator *middle)
    : middle(middle)
{
}

Loop::~Loop() {
    delete middle;
}

std::string Loop::show() const {
    return "while(" + middle->show() + ")";
}

Dycrement::Dycrement(Operator *right)
    : right(right)
{
}

Dycrement::~Dycrement() {
    delete right;
}

std::string Dycrement::show() const {
    return "--" + right->show();
}

Process::Process(Operator *right)
    : right(right)
{
}

Process::~Process() {
    delete right;
}

std::string Process::show() const {
    return "do" + right->show();
}

Variable::Variable(std::string name)
    : name(name)
{
}

std::string Variable::show() const {
    return name;
}

