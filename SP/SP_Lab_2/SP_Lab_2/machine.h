#ifndef APLB6_MACHINE_H
#define APLB6_MACHINE_H

typedef int State;
typedef int Signal;

class Machine {
public:
	Machine(State **transitionTable, State initialState = 0);
	virtual ~Machine();

	State getState();

	State step(Signal signal);

private:
	State **transitionTable;
	State state;
};

#endif // APLB6_MACHINE_H