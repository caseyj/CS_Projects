/**
*Author: John B. Casey
*
*
**
*
*
*
*
*
*
*
*
*/

#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <ctype.h>
#include <string.h>
#include <sys/wait.h>
#include <unistd.h>
#include "internal.h"
#include "history.h"
#include "tokenizer.h"

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
int stringToInt(char *string, int stringLength, int val){
	if(isdigit(string[strlen(string)-stringLength])==0){
		return val;
	}else{
		int newVal = (val*10)+((string[strlen(string)-stringLength])-'0');
		return stringToInt(string, stringLength-1, newVal);     
	}
}
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
int sTInt(char *string){
	if(isdigit(string[0])==0){
		return 0;
	}else{
		int newVal = (string[0] - '0');
		return stringToInt(string, strlen(string)-1, newVal);     
	}
}

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
void runInternally(char **tokens, int CMD, Shell *s){
	if(s->verbose == 1){
		printTokens(tokens);
	}
	switch(CMD){
		case HISTORY_N:
			printStory(s->hist);
			break;
		case VERBOSE_N:
			if(tokens[1]!=NULL){
				verbose(s, tokens[1]);
			}
			else{
				verbose(s, "no");
			}
			break;
		case QUIT_N:
			quit(s);
			break;
		case HELP_N:
			help();
			break;
	}
}

/**
*VOID function that calls fork and exec on an external action 
*
*INPUT:
*	char **CMD: the first token determines the file that will be exec'd the rest 
		determine the actions
*/
void runExternally(char **CMD){
	pid_t parent = getpid();
	pid_t pid = fork();
	
	if(pid<0){
		fprintf(stderr, "Fork Failure");
	}
	if(pid == 0){
		int e = execvp(CMD[0], CMD);
		if(e){
			fprintf(stderr, e);
		}
		//Ishould exec here
	}
	else{
		waitpid(pid, NULL, 0);
	}

}

/**
*VOID calls all functions that run commands
*
*INPUT:
*	char *CMD: the untouched user input with possible commands
*	Shell *s: the shell object containing the user history.
*/
void run(char *CMD, Shell *s){
	//char **tokenI = malloc(sizeof(char) * (strlen(CMD)*2));
	char **tokenI = runMachine(CMD);
	int go = isInternal(tokenI[0]);
	addToHistory(s->hist, CMD);
	if(s->verbose==1){
		printTokens(tokenI);
	}
	if(go!=EXTERNAL_N){
		if(go == BANG_N){
			run(getCommand(s->hist, sTInt(tokenI[1])),s);
		}else{
			runInternally(tokenI, go, s);
		}
	}else{
		runExternally(tokenI);
	}
	freeALL(tokenI);
}
