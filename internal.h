/**
*
*
*
*
*
*
*/
#ifndef	INTERNAL_H
#define INTERNAL_H
#include "history.h"
#define BANG_N	1
#define QUIT_N	2
#define HISTORY_N	3
#define VERBOSE_N	4
#define EXTERNAL_N	0
#define HELP_N		5

#define BANG_S	"!"
#define QUIT_S	"quit"
#define HISTORY_S	"history"
#define VERBOSE_S	"verbose"
#define HELP_S		"help"

typedef struct shell{
	int verbose;
	History *hist;	
}Shell;


Shell *setShell(int size, int verbose);

void freeShell(Shell *s);

void quit(Shell *s);

void help();

char *bangNum(int n);

void verbose(Shell *s, char *swit);

void historySet(int n);

void printHistory();

int isInternal(char *command);

void internalCall(char *command);


#endif
