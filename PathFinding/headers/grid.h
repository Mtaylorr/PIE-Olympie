#pragma once

#include <string>

#include "better_vector.h"

class Grid
{
private:
	int width, height;
	char* grid;
	int size;

public:
	Grid(int width, int height);

	Grid(const Grid& other);

	Grid(Grid&& other) noexcept;

	void operator=(const Grid& other);

	void operator=(Grid&& other) noexcept;

	void serialize(BetterVector<char>& buffer) const;

	Grid(const char* buffer, int& cursor);

	~Grid();

	bool at(int x, int y) const;

	void set(int x, int y, bool value);

	bool isInside(int x, int y) const;

	void clear(bool value);

	static Grid load(std::string file);

	void save(std::string file) const;

	int getWidth() const;

	int getHeight() const;
};