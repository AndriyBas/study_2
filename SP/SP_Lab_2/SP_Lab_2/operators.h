#ifndef APLB7_OPERATORS_H
#define APLB7_OPERATORS_H

#include <string>

class Operator {
public:
    virtual std::string show() const = 0;
    virtual ~Operator() {}
};

class Braces : public Operator {
public:
    Braces(Operator *opOp);
    ~Braces();
    std::string show() const;
private:
    Operator *opOp;
};

class Binary : public Operator {
public:
    Binary(Operator *left, Operator *right, std::string operatorSign);
    ~Binary();
    std::string show() const;
private:
    Operator *left, *right;
    std::string operatorSign;
};

class Cond : public Operator {
public:
    Cond(Operator *middle, Operator *op);
    ~Cond();
    std::string show() const;
private:
    Operator *middle, *op;
};


class Dycrement : public Operator {
public:
    Dycrement(Operator *right);
    ~Dycrement();
    std::string show() const;
private:
    Operator *right;
};

class Process : public Operator {
public:
    Process(Operator *right);
    ~Process();
    std::string show() const;
private:
    Operator *right;
};

class Variable : public Operator {
public:
    Variable(std::string name);
    std::string show() const;
private:
    std::string name;
};

#endif

