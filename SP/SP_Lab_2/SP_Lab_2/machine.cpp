#include "machine.h"

Machine::Machine(State **transitionTable, State initialState)
	: transitionTable(transitionTable)
	, state(initialState)
{
}

Machine::~Machine() {
}

State Machine::getState() {
	return state;
}

State Machine::step(Signal signal) {
	state = transitionTable[state][signal];
	return state;
}