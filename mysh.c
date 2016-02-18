/*
*
*
*
*
*
*/


#define _GNU_SOURCE
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <unistd.h>
#include "history.h"
#include "internal.h"
#include "tokenizer.h"
#include "run.h"

int main(int argv, char **argc){
	int verb = 0;
	int size = MAX_HISTORY;
	if(argv > 1){
		for(int i = 1; i<argv; i++){
			if(strcmp("-v", argc[i])==0){
				verb = 1;
				help();
			}
			if(strcmp("-h",argc[i])==0){
				size = sTInt(argc[i+1]);
				i++;
			}
		}	
	}
	SHELL = setShell(size, verb);
	while(1){
		//if(feof(stdin)){
		//	printf("\n");
		//	break;
		//}
		int mem;
		int MAX = 100;
		char *intake = malloc(MAX * sizeof(char));
		printf("mysh[%d]",SHELL->hist->current);
		mem = getline(&intake, &MAX, stdin);
		if(feof(stdin)){
			printf("\n");
			break;
		}
		//fgets(intake, MAX, stdin);
//		if(intake[0]==EOF){
//			free(intake);
//			quit(SHELL);
//			break;
//		}
//		else{
			run(intake, SHELL);
//		}
		//char **tokens = runMachine(intake);
		free(intake); 
	}
//	quit(SHELL);
	return 0;
}
