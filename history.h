#ifndef HISTORY_H
#define HISTORY_H
#define MAX_HISTORY	10	
#define BEGIN_HISTORY	1
#define BEGIN_SIZE	0

typedef struct node{
	struct node *nod;
	char *data;
}Node;

typedef struct history{
	int max;
	int current;
	int currSize;
	Node *hist;
}History;

/**
*Defines the function that will print all of the members of the linked list
	created  by Node structures.
*
**INPUTS**
	Node *head-> pointer to the first Node to be printed and will
*/
void printNodes(Node *head);

/**
*Defines the function that will return the address of a newly created node and 
	will create that node on the heap in memory
*
**INPUTS**
	User *dat = pointer to the user that will become a member of the node
*
**OUTPUTS**
	Node *->pointer to the newly created node
*/
Node *createNode(char * dat);

/**
*Defines the function that will add a node to any already created linked list 
	and point to the given user in that list
*
**INPUTS**
	Node *head->the linked list that will gain a new member
	User *new->the User that will be added to this linked list
*/
void addNode(Node *head, char *new);

/**
*Defines the function that will return a pointer to the requested Node;
*
**INPUTS**
	Node *head-> the linked list that will be searched for a User
	User *tar->the targetted user to be searched for in this linked list
*
Node *findNode(Node *head, User *tar);

**
*Defines the function that will remove a member of a linked list from the list
	and return the Node that was removed from the linked list
*
**INPUTS**
	Node *start->the head node that will begin the search for the removal;
	Node *tar->the node to be removed from the list
*
**OUTPUTS**
	Node *->pointer to the Node removed from the linked list.
*/
Node *removal(Node *start, Node *tar);

/**
*Defines the function that frees the entirety of the linked list.
*
**INPUTS**
	Node *begin->the first node in the linked list to be freed along with
		every subsequent node.
*/
void freeNode(Node *begin);

History *createHistory();

int historySize(History *begin);

void addToHistory(History *target, char *command);

char *getCommand(History *target, int com);

void printStory(History *begin);

void freeHistory(History *tar);

void setMaxSize(History *begin, int newSize);




#endif
