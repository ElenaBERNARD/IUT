CC=gcc
CFLAGS=-g -Og -Wall -Wextra -o
DEPS = HelloWorld.h
OBJ = main.o HelloWorld.o 

%.o: %.c $(DEPS)
	$(CC) -c -o $@ $<

main: $(OBJ)
	$(CC) $(CFLAGS) $@ $^

clean:
	rm *.o