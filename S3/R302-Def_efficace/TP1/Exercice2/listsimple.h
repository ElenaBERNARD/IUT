typedef struct cell {
  int value;
  struct cell* next;
} Cell;
 
typedef struct list {
  Cell* head;
  int size;
} List;
 
List* listCreate();
Cell* listFind(List* l, int value);
Cell* listGet(List* l, int index);
Cell* listAppend(List* l, int value);
Cell* listInsert(List* l, int value, int index);
Cell* listReplace(List* l, int value, int index);
Cell* listRemoveAt(List* l, int index);
Cell* listRemove(List* l, int value);
void freeList(List* l);
void listPrint(List* l);