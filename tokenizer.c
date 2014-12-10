/*
Author: John Casey
Title: tokenizer.c
Purpose: tools to generate tokens specifically for the mysh package
*/
#include <stdio.h>
#include <ctype.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include "tokenizer.h"

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
int toClass(char check){
	if(((65<=check)&&(check<=90))||((97<=check)&&(122>=check))){
		return ABC;
	}
	if(check == 33){
		return BANG;
	}
	if((check==34)||(check==39)){
		return QUOTE;
	}
	if(isspace(check)){
		return WS;
	}
	else{
		return ABC;
	}
}

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
char **runMachine(char *buffer){
	STATE mech = {8, 0, 7, 0};	
	int bufSize = strlen(buffer);
	char **returnable = malloc((bufSize+1) * sizeof(char*));
	int running = 0;
	int tokenNo = 0;
	char *smallStor = malloc((bufSize+1) * sizeof(char));
	int i = 0;
	//for(int i = 0;  i<bufSize; i++){
	while(buffer[i]!='\0'){
		int currOp = toClass(buffer[i]);
		//handling current whiteSpace
		switch(mech.current){
			case(WS):
			//check currOp for membership in QUOTE
				if(currOp == QUOTE){
					mech.current = QUOTE;
					char thir9 = '\'';
					char thir4 = '\"';
					//smallStor[running] = "\buffer[i] ";
					if(buffer[i] == 34){
						smallStor[running] = thir4;
					}else{
						smallStor[running] = thir9;
					}
				}
				//check currOp for membership in ABC
				if(currOp == ABC){
					//printf("WS->ABC w/ %c", buffer[i]);
					mech.current = ABC;
					//printf("mech is now %d \n", mech.current);	
					smallStor[running] = buffer[i];
					running++;
				}
				if(currOp == BANG){
					mech.current = ACC;
					smallStor[running] = buffer[i];
					running++;
				}
				break;
		 	case QUOTE:
				//check currOp for membership in WS
				switch(currOp){
					case QUOTE: 
						mech.current = ACC;
						char thir9 = '\'';
						char thir4 = '\"';
					//smallStor[running] = "\buffer[i] ";
						if(buffer[i] == 34){
							smallStor[running] = thir4;
						}else{
							smallStor[running] = thir9;
						}//smallStor[running] = "\buffer[i]";
						running++;
						break;
					default: smallStor[running] = buffer[i];
						running++;
						break;
				}
				break;
			case ABC:
				switch(currOp){
					case WS: mech.current = ACC;
						//printf("here in ABC->WS %d \n", mech.current);
						break;
					case ABC:/* case NUMBERS: case BANG: case DASH:*/ 					
						//printf("here in ABC->ABC %c %d\n", buffer[i], mech.current);
						smallStor[running] = buffer[i];
						running++;
						if(buffer[i+1] == '\0'){
							mech.current = ACC;
						}
						break;
				}
				break;
			case BANG: 
				switch(currOp){
					case ABC: //NUMBERS:
						mech.current = NUMBERS;
						smallStor[running] = buffer[i];
						running++;
						if(buffer[i+1] == '\0'){
							mech.current = ACC;
						}
						break;
					default: mech.current = ERR; break;
				}
				break;
		}
		if(mech.current == ACC){
			smallStor[running] = '\0';
			running++;
			returnable[tokenNo] = malloc(running * sizeof(char));
			strncpy(returnable[tokenNo], smallStor, running);
			tokenNo++;
			running = 0;
			mech.current = WS;
		}
		i++;
	}
	free(smallStor);
	returnable[tokenNo] = NULL;
	return returnable;
}
/**
*VOID frees all members of a char **
*
*INPUTS:
*	char **JRR: the string of arrays to be free'd
*/
void freeALL(char **JRR){
	int i = 0;
	while(JRR[i]!=NULL){
		free(JRR[i]);
		i++;
	}
	free(JRR);
}

/**
*VOID prints all members of a char **
*
*INPUTS:
*	char **tokens: the items to be printed
*
*/
void printTokens(char **tokens){
	int len = 0;
	while(tokens[len]!=NULL){
		printf("%s \n", tokens[len]);
		len++;
	}
}
