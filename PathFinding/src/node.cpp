#include "node.h"

Node::Node() {}

Node::Node(int x, int y, bool obstacle, float malus)
	: x(x), y(y), obstacle(obstacle), malus(malus) {}

void Node::update(const Node* parent, const Node* goal, float dist)
{
	if (!initialized || parent->local + dist < local)
	{
		bool changeOfDirection = false;

		if (parent && parent->parent)
		{
			if (parent->x - parent->parent->x != x - parent->x)
				changeOfDirection = true;
			else if (parent->y - parent->parent->y != x - parent->y)
				changeOfDirection = true;
		}

		float malus = changeOfDirection ? 0.001 : 0;

		local = malus + (parent ? parent->local + dist : 0);
		global = local + heuristic(goal);
		initialized = true;
		this->parent = parent;
	}
}

void Node::reset()
{
	parent = nullptr;
	global = 0;
	local = 0;
	initialized = false;
	explored = false;
}

float Node::heuristic(const Node* goal) const
{
	int dx = goal->x - x, dy = goal->y - y;
	return sqrt(dx * dx + dy * dy);
}