/*
*
*
*
*
*/
typedef struct node{
	struct node *nod;
	char *data;
}Node;

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
