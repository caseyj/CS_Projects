/*
*
*
*
*
*
*
*
*/
/**
*Recursively takes string input and returns an integer
*
*Inputs:
*	char *string: the string contianing inputs	
*	int stringLength: informs the function where the next value may be
*	int val: the value already found within the string
*
*Output: 
*	if the next value is a digit->recurse the function
*	else->return the val parameter.
*/
int stringToInt(char *string, int stringLength, int val);

/**
*Recursively takes string input and returns an integer
*
*Inputs:
*	char *string: the string contianing inputs	
*	int stringLength: informs the function where the next value may be
*	int val: the value already found within the string
*
*Output: 
*	if the next value is a digit->recurse the function
*	else->return the val parameter.
*/
int sTInt(char *string);

/**
*VOID function that calls the appropriate internal action beased upon token input
*
*INPUT:
*	char **tokens: the first token determines the command and the rest of the set 
		are arguments
*	int CMD: the predetermined command that is taken	
*	Shell *s: the shell that is being worked with
*
**/
void runInternally(char **tokens, int CMD);

/**
*VOID function that calls fork and exec on an external action 
*
*INPUT:
*	char **CMD: the first token determines the file that will be exec'd the rest 
		determine the actions
*/
void runExternally(char **CMD);

/**
*VOID calls all functions that run commands
*
*INPUT:
*	char *CMD: the untouched user input with possible commands
*	Shell *s: the shell object containing the user history.
*/
void run(char *CMD, Shell *s);
