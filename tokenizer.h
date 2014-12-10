/*
*Author: John Casey
*Title: tokenizer.c
*Purpose: tools to generate tokens specifically for the mysh package
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

/**
*Returns an int that is used by run machine to determine the proper state to be 
	in based upon the determination of what a character represents
*INPUT:
*	char check: the individual character whos type is scritinized
*OUTPUT:
*	int: the class that the character represents with
	WHITE_SPACE -> 0
	!: -> 3
	" /  ' -> 1
	Acceptance->7
	Else -> 2
*/
int toClass(char check);

/**
*Returns an array of strings that act as the tokens
*
*INPUTS:
*	char *buffer -> the initial list of objects that will be turned into 
		separate strings
*
*OUTPUT:
*	An array of strings created by the parsing function. 
*
*/
char **runMachine(char *buffer);

/**
*VOID frees all members of a char **
*
*INPUTS:
*	char **JRR: the string of arrays to be free'd
*/
void freeALL(char **JRR);

/**
*VOID prints all members of a char **
*
*INPUTS:
*	char **tokens: the items to be printed
*
*/
void printTokens(char **tokens);
