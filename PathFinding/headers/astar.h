#pragma once

#include <vector>

#include "grid.h"
#include "node.h"

class AStar
{
private:
	static constexpr float SQRT_2 = 1.41421356237f;

public:
	Grid& grid;
	
private:
	int widthNodes, heightNodes;
	Node** nodes;
	std::vector<Node*> priority;

public:
	std::vector<Node*> explored;

public:
	AStar(Grid& grid);
	
	~AStar();

	std::vector<std::pair<int, int>> findPath(int xstart, int ystart, int xend, int yend);

private:
	void resetNodes();

	void deleteNodes();
};