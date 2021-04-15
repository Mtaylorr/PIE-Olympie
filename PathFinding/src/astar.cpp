#include <algorithm>

#include "astar.h"

AStar::AStar(Grid& grid)
	: grid(grid)
{
	nodes = nullptr;
	widthNodes = 0;
	heightNodes = 0;

	resetNodes();
}

AStar::~AStar()
{
	deleteNodes();
}

std::vector<std::pair<int, int>> AStar::findPath(int xstart, int ystart, int xend, int yend)
{
	if (!(grid.isInside(xstart, ystart) && grid.isInside(xend, yend)))
		return std::vector<std::pair<int, int>>();

	resetNodes();

	Node* start = &nodes[ystart][xstart];
	Node* end = &nodes[yend][xend];

	if (start->obstacle || end->obstacle)
		return std::vector<std::pair<int, int>>();

	priority.clear();
	priority.reserve(grid.getWidth()* grid.getHeight());
	explored.clear();
	explored.reserve(grid.getWidth()* grid.getHeight());

	start->update(nullptr, end, 0);
	priority.push_back(start);

	auto comparer = [](Node* a, Node* b) -> bool
	{
		return a->global < b->global;
	};

	bool pathFound = false;

	while (!priority.empty())
	{
		std::sort(priority.begin(), priority.end(), comparer);
		Node* node = priority[0];
		priority.erase(priority.begin());

		if (node->x == xend && node->y == yend)
		{
			pathFound = true;
			break;
		}

		int x, dx, y, dy;
		bool diagonale;

		for (dy = -1; dy <= 1; dy++)
			for (dx = -1; dx <= 1; dx++)
			{
				if (dx == 0 && dy == 0)
					continue;

				x = node->x + dx;
				y = node->y + dy;

				if (grid.isInside(x, y))
				{
					diagonale = (dx * dy != 0);
					Node* next = &nodes[y][x];

					if (!next->obstacle && !(diagonale && nodes[node->y][x].obstacle && nodes[y][node->x].obstacle))
					{
						if (!next->initialized)
							priority.push_back(next);

						next->update(node, end, diagonale ? SQRT_2 : 1);
					}
				}
			}

		node->explored = true;
		explored.push_back(node);
	}

	std::vector<std::pair<int, int>> path;

	if (pathFound)
	{
		const Node* current = end;

		while (current != nullptr)
		{
			path.emplace_back(current->x, current->y);
			current = current->parent;
		}

		std::reverse(path.begin(), path.end());
	}

	return path;
}

void AStar::resetNodes()
{
	size_t width = grid.getWidth(), height = grid.getHeight();

	if (grid.getWidth() != widthNodes || grid.getHeight() != heightNodes)
	{
		deleteNodes();
		nodes = new Node*[height];

		for (size_t y = 0; y < height; y++)
			nodes[y] = new Node[width];

		widthNodes = width;
		heightNodes = height;
	}

	float malus = 1.f / (width * height);
		
	for (int y = 0; y < height; y++)
		for (int x = 0; x < width; x++)
		{
			Node& node = nodes[y][x];
			node.x = x;
			node.y = y;
			node.obstacle = grid.at(x, y);
			node.malus = malus;
			node.reset();
		}
}

void AStar::deleteNodes()
{
	for (size_t i = 0; i < heightNodes; i++)
		delete[] nodes[i];

	delete[] nodes;
	nodes = nullptr;
}