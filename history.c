/*
*
*
*
*
*/
#include "history.h"

void printNodes(Node *head){
	Node *current = head;
	if(current!=NULL){
		while(current!=NULL){
			printf("%s ",current->data);
			current = current -> nod;
		}
	}
}

Node *createNode(char *dat){
	Node *newNode =  malloc(sizeof(Node)); 
	newNode->data = dat;
	newNode->nod = NULL;
	return newNode;
}

void addNode(Node *head, char *new){
	if(head!=NULL){
		Node *current = head;
		while(current->nod!=NULL){
			current = current->nod;
		}
		current->nod = createNode(new);
	}
	else{
		head = createNode(new);
	}
}
/*
Node *findNode(Node *head, User *tar){
	Node *current = head;
	while(current->data != tar){
		current = current->nod;
	}
	return current;
}
*/
Node *removal(Node *start, Node *tar){
	Node *current = start;
	Node *prev = NULL;
	while(current != tar){
		prev = current;
		current = current->nod;
	}
	Node *next = current->nod;
	if(prev!= NULL){
		prev->nod = next;
	}
	current->nod = NULL;
	return current;
}

void freeNode(Node *begin){
	if(begin!=NULL){
		Node *current = begin;
		Node *prev = NULL;
		while(current->nod!=NULL){
			prev = current;
			current = current->nod;
			free(prev);
		}
		free(current);
	}
}

History *createHistory(){
	History *newGuy = malloc(sizeof(History));
	newGuy.max = MAX_HISTORY;
	newGuy.current = BEGIN_HISTORY;
	newGuy.currSize = BEGIN_SIZE;
	newGuy->history = NULL;
	return newGuy;
}

int historySize(History *begin){
	int returner = 0;
	Node *current = begin->history;
	while(current!=NULL){
		returner++;
		current = current->nod;	
	}
	return returner;
}

void addToHistory(History *target, char *command){
	addNode(target->history, command);
	target.currSize++;
	target.current++;
	if(target.currSize > target.max){
		Node *toFree = removal(target->history, target->history);
		freeNode(target->history);
		target.currSize--;
	}
}

char *getCommand(History *target, int com){
	Node *curr = target->history;
	for(int i = (begin.current-begin.currentSize);i<=com;i++){
		curr = curr->nod;
	}
	return curr->data;
}

void printStory(History *begin){
	int size = historySize(begin);
	Node *curr = begin->history;
	for(int i = (begin.current-begin.currentSize);i<=begin.currSize;i++){
		printf("\t%d: %s", i, curr->data);
		curr = curr->nod;
	}
}

void freeHistory(History *tar){
	freeNode(tar.history);
	free(tar);
}

void setMaxSize(History *begin, int newSize){
	begin.max = newSize;
	
}

