/*




*/
#include <stdio.h>
#include <ctype.h>
#include <string.h>
#include "tokenizer.h"

int toClass(char check){
	if(isdigit(check)){
		return NUMBERS;
	}
	if((65<=check)&&(check<=90)||(97<=check)&&(122>=check)){
		return ABC;
	}
	if(check == 33){
		return BANG;
	}
	if((check==34)||(check==39)){
		return QUOTE;
	}
	if(check == 45){
		return DASH;
	}
	if(check == 32){
		return WS;
	}
	else{
		return ABC;
	}
}

char **runMachine(char *buffer){
	STATE mech = {8, 0, 7, 0};	
	int bufSize = strlen(buffer);
	char **returnable = malloc((bufSize*2) * sizeof(char));
	int running = 0;
	int tokenNo = 0;
	char *smallStor = malloc(bufSize * sizeof(char));
	for(int i = 0;  i<bufSize; i++){
		int currOp = toClass(buffer[i]);
		printf("%d to class %c \n", currOp, buffer[i]);
		//handling current whiteSpace
		switch(mech.current){
			case(WS):
			//check currOp for membership in QUOTE
				if(currOp == QUOTE){
					printf("startQuote");
					mech.current = QUOTE;
					char thir9 = '\'';
					char thir4 = '\"';
					//smallStor[running] = "\buffer[i] ";
					if(buffer[i] == 34){
						smallStor[running] = thir4;
					}else{
						smallStor[running] = thir9;
					}
					running++;
					//break;
				}
				//check currOp for membership in ABC
				if(currOp == ABC){
					//printf("WS->ABC w/ %c", buffer[i]);
					mech.current = ABC;
					//printf("mech is now %d \n", mech.current);	
					smallStor[running] = buffer[i];
					running++;
					//break;
				}
				if(currOp == BANG){
					printf("hits Bang!\n");
					mech.current = ACC;
					smallStor[running] = buffer[i];
					running++;
				}
				if(currOp == NUMBERS){
					mech.current = NUMBERS;
					smallStor[running] = buffer[i];
					running++;
				}
				if(currOp == DASH){
					mech.current = DASH;
					smallStor[running] = buffer[i];
					running++;
				}
				break;
		 	case QUOTE:
				//check currOp for membership in WS
				switch(currOp){
					case QUOTE: 
						mech.current = ACC;
						printf("quote->quote exec \n");
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
						break;
					default: mech.current = ERR; break;
				}
				break;
			case BANG: 
				switch(currOp){
					case NUMBERS:
						mech.current = NUMBERS;
						smallStor[running] = buffer[i];
						running++;
						break;
					default: mech.current = ERR; break;
				}
				break;
			case NUMBERS:
				switch(currOp){
					case NUMBERS:
						smallStor[running] = buffer[i];
						running++;
						break;
					case WS:
						mech.current = ACC;
						break;
					default: mech.current = ERR; break;
				}
				break;
			case DASH:
				switch(currOp){
					case ABC: 
						mech.current = ABC;
						smallStor[running] = buffer[i];
						running++;
						break;
				}
				break;
		}
		if(mech.current == ACC){
			printf("here in accept ACCEPT %d\n", mech.current);
			smallStor[running] = '\0';
			printf("%s \n", smallStor);
			int accLen = strlen(smallStor);
			returnable[tokenNo] = malloc(running+1 * sizeof(char));
			strncpy(returnable[tokenNo], smallStor, running+1);
			tokenNo++;
			running = 0;
			mech.current = WS;
			//free(smallStor);
			//realloc(smallStor, (bufSize-i * sizeof(char)));
		}
/*		if(mech.current = ERR){
//			smallStor[running] = '\0';
//			int accLen = strlen(smallStor);
			//realloc(smallStor, (bufSize-i * sizeof(char)));
			//free(smallStor);
			running = 0; 
			return NULL;
		}
	*/}
	returnable[tokenNo] = NULL;
	return returnable;
}

void printTokens(char **tokens){
	int len = 0;
	while(tokens[len]!=NULL){
		printf("%s \n", tokens[len]);
		len++;
	}
}
/*

int main(){
	printf("%d %d %d %d %d %d %d %d \n", toClass('\"'),toClass('\''),toClass('!'),toClass('a'),toClass(' '), toClass('A'), toClass('1'),toClass('-'));
	char *test = "the quick sly brown fox jumped !21  \"Hellow \" -v ";
	char **tokens = runMachine(test); 
	printTokens(tokens);
	return 0;
}*/
