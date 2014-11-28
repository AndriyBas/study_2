#include "operators.h"


Braces::Braces(Operator *opOp)
    : opOp(opOp)
{
}

Braces::~Braces() {
    delete opOp;
}

std::string Braces::show() const {
    return "(" + opOp->show() + ")";
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

Cond::Cond(Operator *middle, Operator *op)
    : middle(middle),
    op(op)
{
}

Cond::~Cond() {
    delete middle;
    delete op;
}

std::string Cond::show() const {
    return "if (" + middle->show() + ") " + op->show();
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
    return right->show();
}

Variable::Variable(std::string name)
    : name(name)
{
}

std::string Variable::show() const {
    return name;
}

