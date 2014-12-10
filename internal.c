/*
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
#include <string.h>
#include "internal.h"
#include "history.h"

/**
*Returns a shell pointing to a null history
*
*INPUTS:
*	int size: how many items you wish to hold in the history
*	int verbose: 0-> off 1->on; indicates whether or not verbose mode is turned on
*
*OUTPUT:
*	Shell: a shell that will be used to operate many of the internal functions
*/
Shell *setShell(int size, int verbose){
	Shell *s = malloc(sizeof(Shell));
	s->hist = malloc(sizeof(History));
	s->hist = createHistory();
	if(size!=NULL){
		setMaxSize(s->hist, size);
		  
	}
	s->verbose = verbose;
	printf("%d\n", s->hist->current);
	return s;
}

/**
*VOID Frees all memory associated with the shell including the shell itself.
*
*INPUTS:
*	Shell *s: pointer to the shell to be free'd
*
*/
void freeShell(Shell *s){
	freeHistory(s->hist);
	free(s);
}

/**
*VOID quits the shell and frees all memory associated with it.
*
*INPUTS:
*	Shell *s: the shell to be free'd and exited
*/
void quit(Shell *s){
	freeShell(s);	
	printf("Good Bye!\n");
	exit(0);
}

/**
*VOID Gives us help.
*/
void help(){
	printf("----------------------------------------\n");
	printf("------------------help------------------\n");
	printf("----------------------------------------\n");
	printf("Call the following commands within the shell as well as any Unix\n \t commands you also wish to call\n\n");
	printf("!(N) -> Re-execute the Nth command in the list, as long as the \n \t number is within the current number of saved entries\n\n");
	printf("help -> Shows this window\n\n");
	printf("quit -> Exits this shell\n\n");
	printf("history -> Prints the contents of the current history, numbers \n \t for each item precede the item in history, these can be\n \t called with the ! command to re-execute the command\n\n");
	printf("verbose [on|off] -> turns on or off verbose mode. When verbose mode is on, every command called will provide the user with more information\n\n");
}

/***
*VOID toggles verbose mode switched by changing the answer from on to off visaversa
*
*INPUTS:
*	Shell *s -> The shell that shall be toggled
*	char *swit -> the new position
*/
void verbose(Shell *s, char *swit){
	if(strcmp("off", swit)==0){
		s->verbose = 0;
	}
	if(strcmp("on", swit)==0){
		s->verbose = 1;
	}else{
		fprintf(stderr, "The correct input for this command is: verbose [on|off]\n");
	}
}

/**
void printHistory(){
	printStory(SHELL->hist);
}
*/
/**
*Returns an int representing if the Command given represents a command that is 
	a member of this module. If it is tdetermined the Command is not a 
	member EXTERNAL_N is returned;
*INPUTS:
*	char *command -> a single string command that will determine location 
	of execution
*OUTPUTS:
*	int -> an nteger determining which function/operation should be called.
*/
int isInternal(char *command){
	if((strcmp(command, BANG_S)==0)){
		return BANG_N;
	}
	if(strcmp(command, QUIT_S)==0){
		return QUIT_N;
	}
	if(strcmp(command, HISTORY_S)==0){
		return HISTORY_N;
	}
	if(strcmp(command, VERBOSE_S)==0){
		return VERBOSE_N;
	}if(strcmp(command, HELP_S)==0){
		return HELP_N;
	}
	else{
		return EXTERNAL_N;
	}
}
