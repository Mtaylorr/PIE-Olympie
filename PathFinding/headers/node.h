#pragma once

#include <cmath>

class Node
{
	friend class AStar;

public:
	int x = 0, y = 0;
	bool obstacle = false;
	float malus = 0;
	const Node* parent = nullptr;

private:
	float global = 0, local = 0;
	bool initialized = false;
	bool explored = false;

public:
	Node();
	
	Node(int x, int y, bool obstacle = false, float malus = 0);

	/* dist est la distance avec le parent */
	void update(const Node* parent, const Node* goal, float dist);

	void reset();

private:
	float heuristic(const Node* goal) const;
};