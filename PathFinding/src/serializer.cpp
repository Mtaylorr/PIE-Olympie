#include "serializer.h"

void serializer::serializeString(BetterVector<char>& buffer, const std::string& value)
{
	serialize<size_t>(buffer, value.size());

	for (char c : value)
		serialize<char>(buffer, c);
}

std::string serializer::unserializeString(const char* buffer, int& cursor)
{
	size_t size = unserialize<size_t>(buffer, cursor);

	std::string result;
	result.resize(size);

	for (size_t i = 0; i < size; i++)
		result[i] = unserialize<char>(buffer, cursor);

	return result;
}