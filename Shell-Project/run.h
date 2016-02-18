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

void runInternally(char **tokens, int CMD);

void runExternally(char **CMD);


void run(char *CMD, History *his);
