
f = open('tal.txt', 'r')
content = f.read().strip()
f.close()
f = open('primtal.txt', 'w')
numbers = content.split()
string = "private static int[] primes = {"
for i in range(int(len(numbers)*4/5)):
	string += numbers[i] + ","

string = string[:-1]
string += "};"
f.write(string)
