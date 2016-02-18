/*
*
*
*
*
*
*
*
*
*
*
*/

typedef struct {
	int states;
	int start;
	int accept;
	int current;
}STATE;

#define WS	0
#define QUOTE 	1
#define ABC	2
#define BANG	3
#define NUMBERS	4
#define DASH	5
#define ERR	6
#define ACC	7

int toClass(char check);

char **runMachine(char *buffer);

void printTokens(char **tokens);
