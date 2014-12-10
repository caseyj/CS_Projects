/*
*
*
*
*
*/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
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
	History *newGuy = malloc(sizeof(History)+1);
	newGuy->max = MAX_HISTORY;
	newGuy->current = BEGIN_HISTORY;
	newGuy->currSize = BEGIN_SIZE;
	newGuy->hist = NULL;
	return newGuy;
}

int historySize(History *begin){
	int returner = 0;
	Node *current = begin->hist;
	while(current!=NULL){
		returner++;
		current = current->nod;	
	}
	return returner;
}

void addToHistory(History *target, char *command){
	if(target->hist==NULL){
		target->hist = createNode(command);
		target->currSize++;
		target->current++;
	}
	else{
		addNode(target->hist, command);
		target->currSize++;
		target->current++;
	}
	while(historySize(target) > target->max){
		Node *holdMyBeer = target->hist;
		target->hist = target->hist->nod;
		free(holdMyBeer);
		target->currSize--;
	}
}


char *getCommand(History *target, int com){
	Node *curr = target->hist;
	int current = (target->current - historySize(target));
	while(current!=com){
		curr = curr->nod;
		current++;
	}
	return curr->data;
}

void printStory(History *begin){
	int size = historySize(begin);
	Node *curr = begin->hist;
	int current = (begin->current - size);
	while(curr!=NULL){
		printf("%6d: %s\n", current, curr->data);
		curr = curr->nod;
		current++;
	}
}

void freeHistory(History *tar){
	freeNode(tar->hist);
	free(tar);
}

void setMaxSize(History *begin, int newSize){
	begin->max = newSize;
	
}

