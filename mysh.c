/*
*
*
*
*
*
*/


#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include <unistd.h>
#include "history.h"
#include "internal.h"
#include "tokenizer.h"


int main(int argv, char *argc){
	int verb = 0;
	int size = MAX_HISTORY;
	if(argv > 1){
		for(int i = 1; i<argv; i++){
			if(strcmp("-v", argc[i])==0){
				verb = 1;
			}
			if(strcmp("-h",argc[i])==0){
				size = argc[i+1];
				i++;
			}
		}	
	}
	setShell(size, verb);
	

	return 0;
}
