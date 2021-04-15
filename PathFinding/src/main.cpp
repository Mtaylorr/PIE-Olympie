#include <iostream>
#include <vector>
#include <fstream>

#include "grid.h"
#include "astar.h"

void savePath(std::string filePath, const std::vector<std::pair<int, int>>& path)
{
	std::ofstream ofs = std::ofstream(filePath);

	if (ofs.is_open())
	{
		int imax = path.size() - 1;
		int dx, dy;

		for (int i = 0; i < imax; i++)
		{
			dx = path[i + 1].first - path[i].first;
			dy = path[i + 1].second - path[i].second;

			if (dx == -1)
			{
				if (dy == -1)
					ofs << '7';
				else if (dy == 0)
					ofs << '6';
				else if (dy == 1)
					ofs << '5';
			}
			else if (dx == 0)
			{
				if (dy == -1)
					ofs << '0';
				else if (dy == 1)
					ofs << '4';
			}
			else if (dx == 1)
			{
				if (dy == -1)
					ofs << '1';
				else if (dy == 0)
					ofs << '2';
				else if (dy == 1)
					ofs << '3';
			}
		}

		ofs.close();
	}
	else
	{
		throw "can't open " + filePath;
	}
}

int main(int argc, char* argv[])
{
	if (argc != 7)
	{
		std::cerr << "format : ./PathFinding map.bin x_start y_start x_end y_end path.txt";
		exit(EXIT_FAILURE);
	}

	std::string s1 = std::string(argv[1]);
	std::string s2 = std::string(argv[2]);
	std::string s3 = std::string(argv[3]);
	std::string s4 = std::string(argv[4]);
	std::string s5 = std::string(argv[5]);
	std::string s6 = std::string(argv[6]);

	std::cout << "grid load..." << std::endl;
	Grid grid = Grid::load(s1);

	std::cout << "generate astar..." << std::endl;
	AStar astar(grid);

	std::cout << "convert argv to int..." << std::endl;
	int x_start = std::stoi(s2);
	int y_start = std::stoi(s3);
	int x_end = std::stoi(s4);
	int y_end = std::stoi(s5);

	std::cout << "calculate path..." << std::endl;
	std::vector<std::pair<int, int>> path = astar.findPath(x_start, y_start, x_end, y_end);

	std::cout << "save path..." << std::endl;
	savePath(s6, path);

	std::cout << "success" << std::endl;

	return 0;
}