#include <fstream>
#include <iostream>
#include <cstdint>

#include "grid.h"
#include "serializer.h"

Grid::Grid(int width, int height)
	: width(width), height(height)
{
	size = width * height;

	if (size % 8)
		size = size / 8 + 1;
	else
		size = size / 8;

	grid = new char[size];

	for (int i = 0; i < size; i++)
		grid[i] = 0;
}

Grid::Grid(const Grid& other)
{
	grid = nullptr;
	operator=(other);
}

Grid::Grid(Grid&& other) noexcept
{
	grid = nullptr;
	operator=(std::move(other));
}

void Grid::operator=(const Grid& other)
{
	this->~Grid();

	width = other.width;
	height = other.height;
	size = other.size;

	grid = new char[size];

	for (int i = 0; i < size; i++)
		grid[i] = other.grid[i];
}

void Grid::operator=(Grid&& other) noexcept
{
	this->~Grid();

	width = other.width;
	height = other.height;
	size = other.size;
	grid = other.grid;

	other.width = 0;
	other.height = 0;
	other.size = 0;
	other.grid = nullptr;
}

void Grid::serialize(BetterVector<char>& buffer) const
{
	namespace s = serializer;

	s::serialize<int>(buffer, width);
	s::serialize<int>(buffer, height);

	for (int i = 0; i < size; i++)
		s::serialize<char>(buffer, grid[i]);
}

Grid::Grid(const char* buffer, int& cursor)
{
	namespace s = serializer;

	width = s::unserialize<int>(buffer, cursor);
	height = s::unserialize<int>(buffer, cursor);

	size = width * height;

	if (size % 8)
		size = size / 8 + 1;
	else
		size = size / 8;

	grid = new char[size];

	for (int i = 0; i < size; i++)
		grid[i] = s::unserialize<char>(buffer, cursor);
}

Grid::~Grid()
{
	delete[] grid;
}

bool Grid::at(int x, int y) const
{
	int index = width * y + x;
	int indexQ = index / 8;
	int indexR = index % 8;

	return (grid[indexQ] >> (7 - indexR)) & 1;
}

void Grid::set(int x, int y, bool value)
{
	int index = width * y + x;
	int indexQ = index / 8;
	int indexR = index % 8;

	if (value)
		grid[indexQ] |= 1 << (7 - indexR);
	else
		grid[indexQ] &= ~(1 << (7 - indexR));
}

bool Grid::isInside(int x, int y) const
{
	return x >= 0 && x < width&& y >= 0 && y < height;
}

void Grid::clear(bool value)
{
	char octet = value ? 0xFF : 0x00;

	for (int i = 0; i < size; i++)
		grid[i] = octet;
}

Grid Grid::load(std::string file)
{
	namespace s = serializer;
	std::ifstream input(file, std::ios::binary);

	if (input.fail())
	{
		std::cout << "Impossible d'ouvrir " << file << std::endl;;
		exit(EXIT_FAILURE);
	}

	BetterVector<char> bytes = BetterVector<char>(std::istreambuf_iterator<char>(input), std::istreambuf_iterator<char>());
	input.close();

	if (bytes.size() < sizeof(uint32_t))
	{
		std::cout << file << " corrupted." << std::endl;;
		exit(EXIT_FAILURE);
	}

	int cursor = 0;
	uint32_t expectedSize = s::unserialize<uint32_t>(bytes.data(), cursor);

	if (bytes.size() != expectedSize)
	{
		std::cout << file << " corrupted." << std::endl;;
		exit(EXIT_FAILURE);
	}

	return Grid(bytes.data(), cursor);
}

void Grid::save(std::string file) const
{
	namespace s = serializer;

	BetterVector<char> buffer;
	s::serialize<uint32_t>(buffer, 0);
	serialize(buffer);

	BetterVector<char> header;
	s::serialize<uint32_t>(header, buffer.size());

	for (int i = 0; i < sizeof(uint32_t); i++)
		buffer[i] = header[i];

	std::ofstream output(file, std::ios::binary);
	output.write(buffer.data(), buffer.size());
	output.close();
}

int Grid::getWidth() const
{
	return width;
}

int Grid::getHeight() const
{
	return height;
}
